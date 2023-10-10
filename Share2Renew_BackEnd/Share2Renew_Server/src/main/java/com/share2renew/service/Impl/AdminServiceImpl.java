package com.share2renew.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.share2renew.mapper.UserMapper;
import com.share2renew.pojo.Admin;
import com.share2renew.mapper.AdminMapper;
import com.share2renew.pojo.Post;
import com.share2renew.pojo.User;
import com.share2renew.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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


    @Override
    public Admin adminLogin(String username, String password) {
        return adminMapper.selectOne(new QueryWrapper<Admin>().eq("username",username).eq("password", password).eq("valodity", 1));
    }

    @Override
    public List<User> getAllUsers() {


        return null;
    }

    @Override
    public List<Post> getAllPosts() {
        return null;
    }

    @Override
    public void modifyPostValidity(String postId) {

    }

    @Override
    public void modifyUserValidity(String userId) {

    }

    @Override
    public void modifyUserCommentRight(String userId) {

    }

}
