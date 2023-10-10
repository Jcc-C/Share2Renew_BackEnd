package com.share2renew.service;

import com.share2renew.exception.ParamsException;
import com.share2renew.pojo.GeneralBean;
import com.share2renew.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Junxian Cai
 * @since 2023-09-07
 */
public interface IUserService extends IService<User> {

    /**
     * return token after login
     * @param username
     * @param password
     * @param request
     * @return
     */
    public GeneralBean login(String username, String password, HttpServletRequest request);

    /**
     * Get the user info by username
     * @param username
     * @return
     */
    public User getUserByUserName(String username);

    /**
     * For the user register
     * @param user
     * @return
     */
    public GeneralBean register(User user);

    /**
     * For user update password
     * @param userInfo
     * @return
     */
    public GeneralBean updatePassword(Map<String, Object> userInfo);

    /**
     * Get current userId
     * @return
     */
    public int getCurrentUserId() throws ParamsException;


}
