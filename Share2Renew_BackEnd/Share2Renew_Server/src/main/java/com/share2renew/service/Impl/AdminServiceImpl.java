package com.share2renew.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.share2renew.mapper.PostMapper;
import com.share2renew.mapper.UserMapper;
import com.share2renew.pojo.Admin;
import com.share2renew.mapper.AdminMapper;
import com.share2renew.pojo.Post;
import com.share2renew.pojo.User;
import com.share2renew.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

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


    @Override
    public Admin adminLogin(String username, String password) {
        return adminMapper.selectOne(new QueryWrapper<Admin>().eq("username",username).eq("password", password).eq("validity", 1));
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
    public void modifyPostValidity(int postId, int validity) {

        Post post = new Post();
        post.setPostId(postId);
        post.setValidity(validity);
        postMapper.updateById(post);

    }



}
