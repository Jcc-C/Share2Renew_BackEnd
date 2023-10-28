package com.share2renew.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.share2renew.config.security.JwtTokenUtil;
import com.share2renew.exception.ParamsException;
import com.share2renew.pojo.GeneralBean;
import com.share2renew.pojo.User;
import com.share2renew.mapper.UserMapper;
import com.share2renew.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.ipinfo.api.IPinfo;
import io.ipinfo.api.errors.RateLimitedException;
import io.ipinfo.api.model.IPResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Junxian Cai
 * @since 2023-09-07
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private JavaMailSenderImpl javaMailSender;
    @Value("${spring.mail.username}")
    private String senderEmail;
    @Autowired
    private Configuration configuration;
    private static final String GET_IP_INFO_URL = "http://ipinfo.io/ip";
    @Autowired
    private RestTemplate restTemplate;

    /**
     * return token after login
     * @param username
     * @param password
     * @param request
     * @return
     */
    @Override
    public GeneralBean login(String username, String password, HttpServletRequest request) throws RateLimitedException {
        //Login
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        //Basic judge
        if (null == userDetails || !passwordEncoder.matches(password, userDetails.getPassword())){
            return GeneralBean.error("Username or Password Incorrect!");
        }
        if (!userDetails.isEnabled()) {
            return GeneralBean.error("Current account is disabled!");
        }
        //Update user info

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails
                ,null,userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //Generate token
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);

        String ipInfo = getIpInfo(request);
        User currentUser = getUserByUserName(username);
        currentUser.setLocation(ipInfo);
        userMapper.updateById(currentUser);

        return GeneralBean.success("Login successfully", tokenMap);
    }

    /**
     * Get the user info by username
     * @param username
     * @return
     */
    @Override
    public User getUserByUserName(String username) {
        //查出来了还可以继续做其他判断
        return userMapper.selectOne(new QueryWrapper<User>().eq("username", username).eq("validity", true));
    }

    /**
     * For the user register
     * @param user
     * @return
     */
    @Override
    public GeneralBean register(User user, HttpServletRequest request) throws MessagingException, TemplateException, IOException, RateLimitedException {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        QueryWrapper<User> username = queryWrapper.eq("username", user.getUsername());
        User result = userMapper.selectOne(username);

        //首先判断username是否唯一
        if ( result == null && user.getUsername() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setValidity(true);
            user.setRightToComment(1);

            String ipInfo = getIpInfo(request);
            user.setLocation(ipInfo);

            //Get user current location
            int resultInsert = userMapper.insert(user);
            if (resultInsert == 1) {
                sendEmailForRegister(user.getRealName(), user.getEmail());
                return GeneralBean.success("Register successfully");
            }
        } else if (user.getUsername() == null) {
            return GeneralBean.error("Please input username first.");
        } else {
            return GeneralBean.error("Username has been used, please try another one.");
        }
        return GeneralBean.error("Register failed, please try again.");
    }

    public String getIpInfo(HttpServletRequest request) throws RateLimitedException {
        IPinfo iPinfo = new IPinfo.Builder().setToken("94fa75aedcef6d").build();

        String ipAddress = restTemplate.getForObject(GET_IP_INFO_URL, String.class).trim();

        IPResponse ipResponse = iPinfo.lookupIP(ipAddress);
        String countryCode = ipResponse.getCountryCode();
        String region = ipResponse.getRegion();
        String ip = ipResponse.getIp();
        String city = ipResponse.getCity();
        return city + "/" + region + "/" + countryCode;
    }

    /**
     * For user update password
     * @param userInfo
     * @return
     */
    @Override
    public GeneralBean updatePassword(Map<String, Object> userInfo) throws MessagingException, TemplateException, IOException {
        // Access user information including password and userId
        String previousPass = (String) userInfo.get("previousPass");
        String newPass = (String) userInfo.get("newPass");
        Integer userId = (Integer) userInfo.get("userId");

        // Get user information by user id
        User user = userMapper.selectById(userId);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        if (bCryptPasswordEncoder.matches(previousPass, user.getPassword())) {
            // Set user new password
            user.setPassword(bCryptPasswordEncoder.encode(newPass));
            // Update the user information to the database
            int result = userMapper.updateById(user);
            if (result == 1) {
                sendEmailForPasswordChange(user);
                return GeneralBean.success("Password update successfully.");
            }
        }
        return GeneralBean.error("Password update failed, please try again.");
    }

    /**
     * Get the current user
     * @return
     */
    @Override
    public int getCurrentUserId() throws ParamsException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User user = getUserByUserName(username);
                if (user != null) {
                    return user.getUserId();
                }
            }
        }
        throw new ParamsException();
    }

    /**
     * update User information
     * @param user
     * @return
     */
    @Override
    public GeneralBean updateUser(User user) {
        int result = userMapper.updateById(user);
        if (result == 1) {
            return GeneralBean.success("Update user information successfully");
        }
        return GeneralBean.error("Update user information failed");
    }

    /**
     * Update user avatar
     * @param url
     * @param userId
     * @param authentication
     * @return
     */
    @Override
    public GeneralBean updateUserAvatar(String url, Integer userId, Authentication authentication) {
        //get current user
        User user = userMapper.selectById(userId);
        //set the user's url
        user.setAvatar(url);
        int result = userMapper.updateById(user);
        if (result == 1) {
            // Update Spring Security Information
            User principal = (User) authentication.getPrincipal();
            principal.setAvatar(url);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null, authentication.getAuthorities()));
            return GeneralBean.success("Update successfully!", url);
        }
        return GeneralBean.error("Update failed!");
    }

    @Override
    public void sendEmail() throws IOException, MessagingException, TemplateException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        Template template = configuration.getTemplate("welcomeMail.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, null);

        helper.setTo("kevin.f.shen@foxmail.com");
        helper.setText(html, true);
        helper.setSubject("Welcome Mail");
        helper.setFrom(senderEmail);

        javaMailSender.send(mimeMessage);

    }

    public void sendEmailForRegister(String realName, String emailAddress) throws IOException, MessagingException, TemplateException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        Template template = configuration.getTemplate("welcome.html");

        Map<String, Object> model = new HashMap<>();
        model.put("realName", realName);

        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

        helper.setTo(emailAddress);
        helper.setText(html, true);
        helper.setSubject("Welcome Mail");

        helper.setFrom(senderEmail);

        javaMailSender.send(mimeMessage);

    }

    public void sendEmailForPasswordChange(User user) throws IOException, MessagingException, TemplateException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        Template template = configuration.getTemplate("password.html");

        String realName = user.getRealName();
        String emailAddress = user.getEmail();

        Map<String, Object> model = new HashMap<>();
        model.put("realName", realName);

        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

        helper.setTo(emailAddress);
        helper.setText(html, true);
        helper.setSubject("Password has been changed");

        helper.setFrom(senderEmail);

        javaMailSender.send(mimeMessage);

    }
}
