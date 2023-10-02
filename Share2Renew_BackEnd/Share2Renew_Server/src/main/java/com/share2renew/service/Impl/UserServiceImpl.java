package com.share2renew.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.share2renew.config.security.JwtTokenUtil;
import com.share2renew.pojo.GeneralBean;
import com.share2renew.pojo.User;
import com.share2renew.mapper.UserMapper;
import com.share2renew.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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

    /**
     * return token after login
     * @param username
     * @param password
     * @param request
     * @return
     */
    @Override
    public GeneralBean login(String username, String password, HttpServletRequest request) {
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
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //todo: 1-7

        //Generate token
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);

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
    public GeneralBean register(User user) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        QueryWrapper<User> username = queryWrapper.eq("username", user.getUsername());
        User result = userMapper.selectOne(username);

        //首先判断username是否唯一
        if ( result == null && user.getUsername() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setValidity(true);
            user.setRightToComment(1);

            userMapper.insert(user);
            return GeneralBean.success("Register successfully");
        } else if (user.getUsername() == null) {
            return GeneralBean.error("Please input username first.");
        } else {
            return GeneralBean.error("Username has been used, please try another one.");
        }
    }
}
