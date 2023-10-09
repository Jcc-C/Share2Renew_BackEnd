package com.share2renew.service;

import com.share2renew.pojo.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.share2renew.pojo.Post;
import com.share2renew.pojo.User;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Junxian Cai
 * @since 2023-10-07
 */
public interface IAdminService extends IService<Admin> {

    Admin adminLogin(String username, String password);

    /**
     * get all users basic information from db
     * @return
     */
    List<User> getAllUsers();

    /**
     * get all posts from db
     * @return
     */
    List<Post> getAllPosts();

    //上面两个 存疑

    /**
     *  modify post validity by post id
     * @param postId
     */
    void modifyPostValidity(String postId);

    /**
     * modify user validity by user id
     * @param userId
     */
    void modifyUserValidity(String userId);

    /**
     * modify user comment right by user id
     * @param userId
     */
    void modifyUserCommentRight(String userId);




}
