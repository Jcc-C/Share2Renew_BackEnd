package com.share2renew.service.Impl;

import com.share2renew.pojo.Admin;
import com.share2renew.mapper.AdminMapper;
import com.share2renew.pojo.Post;
import com.share2renew.pojo.User;
import com.share2renew.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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


    @Override
    public Admin adminLogin(String username, String password) {
        return null;
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
