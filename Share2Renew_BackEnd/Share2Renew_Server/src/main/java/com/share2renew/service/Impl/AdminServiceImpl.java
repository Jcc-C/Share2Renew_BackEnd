package com.share2renew.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.share2renew.config.security.JwtTokenUtil;
import com.share2renew.mapper.PostMapper;
import com.share2renew.mapper.UserMapper;
import com.share2renew.pojo.Admin;
import com.share2renew.mapper.AdminMapper;
import com.share2renew.pojo.GeneralBean;
import com.share2renew.pojo.Post;
import com.share2renew.pojo.User;
import com.share2renew.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Junxian Cai
 * @since 2023-10-07
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHead}")
    private String tokenHead;


    @Override
    public GeneralBean adminLogin(String username, String password, HttpServletRequest request) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (null == userDetails || !passwordEncoder.matches(password, userDetails.getPassword())){
            return GeneralBean.error("Username or Password Incorrect!");
        }
        if (!userDetails.isEnabled()) {
            return GeneralBean.error("Current account is disabled!");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails
                ,null,userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //Generate token
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);

        return GeneralBean.success("Login successfully", tokenMap);


    }

    @Override
    public List<User> getAllUsers() {
        return userMapper.selectList(new QueryWrapper<User>());
    }

    @Override
    public List<Post> getAllPosts() {
        return postMapper.selectList(new QueryWrapper<Post>());
    }

    @Override
    public void modifyUserValidity(int userId, boolean validity) {

        User user = new User();
        user.setUserId(userId);
        user.setValidity(validity);
        userMapper.updateById(user);

    }

    @Override
    public void modifyUserCommentRight(int userId, int commentRight) {

        User user = new User();
        user.setUserId(userId);
        user.setRightToComment(commentRight);
        userMapper.updateById(user);

    }

    @Override
    public Admin getAdminByUserName(String userName) {
        return adminMapper.selectOne(new QueryWrapper<Admin>().eq("username", userName).eq("validity", true));
    }

    /**
     * For admin register
     * @param admin
     * @return
     */
    @Override
    public GeneralBean registerAdmin(Admin admin) {
        Admin result = adminMapper.selectOne(new QueryWrapper<Admin>().eq("username", admin.getUsername()));
        if (result != null) {
            return GeneralBean.error("Username has been userd, please try another one");
        } else if (result == null && admin.getUsername() != null) {
            admin.setPassword(passwordEncoder.encode(admin.getPassword()));
            admin.setValidity(true);

            int insert = adminMapper.insert(admin);
            if (insert == 1) {
                return GeneralBean.success("Register successfully");
            }
        }
        return GeneralBean.error("Register failed, please try again.");
    }

    @Override
    public Admin getCurrentAdmin(Principal principal) {
        if (principal == null) {
            return null;
        }else {
            String username = principal.getName();
            Admin admin = adminMapper.selectOne(new QueryWrapper<Admin>().eq("username", username).eq("validity", true));
            if (admin != null) {
                return admin;
            }
        }
        return null;
    }

    @Override
    public void modifyPostValidity(int postId, int validity) {

        Post post = new Post();
        post.setPostId(postId);
        post.setValidity(validity);
        postMapper.updateById(post);

    }



}
