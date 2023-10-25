package com.share2renew.service;

import com.share2renew.pojo.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.share2renew.pojo.GeneralBean;
import com.share2renew.pojo.Post;
import com.share2renew.pojo.User;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
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

    GeneralBean adminLogin(String username, String password, HttpServletRequest request);

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
    void modifyPostValidity(int postId, int validity);

    /**
     * modify user validity by user id
     * @param userId
     */
    void modifyUserValidity(int userId, boolean validity);

    /**
     * modify user comment right by user id
     * @param userId
     */
    void modifyUserCommentRight(int userId, int commentRight);

    /**
     * Return admin by username
     * @param userName
     * @return
     */
    Admin getAdminByUserName(String userName);

    /**
     * For admin register
     * @param admin
     * @return
     */
    GeneralBean registerAdmin(Admin admin);

    /**
     * For getting current admin
     * @param principal
     * @return
     */
    Admin getCurrentAdmin(Principal principal);
}
