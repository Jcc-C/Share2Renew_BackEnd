package com.share2renew.controller;

import com.share2renew.pojo.GeneralBean;
import com.share2renew.pojo.User;
import com.share2renew.pojo.UserLoginInfo;
import com.share2renew.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * @program: Share2Renew_BackEnd
 * @description: Use for login related-functions
 * @author: Junxian Cai
 **/

@Api(tags = "LoginController")
@RestController
@CrossOrigin
public class LoginController {

    @Autowired
    private IUserService userService;

    @ApiOperation(value = "Login then return token")
    @PostMapping("/login")
    public GeneralBean login(@RequestBody UserLoginInfo userLoginInfo, HttpServletRequest request) {
        return userService.login(userLoginInfo.getUsername(), userLoginInfo.getPassword(), request);
    }

    /**
     * Logout operations are handled by the front-end
     * Delete the token head and token in the front-end
     * @return
     */
    @ApiOperation(value = "Logout")
    @PostMapping("/logout")
    public GeneralBean logout() {
        return GeneralBean.success("Logout successfully");
    }

    @ApiOperation(value = "Get the current user info")
    @GetMapping("/user/info")
    public User getCurrentUserInfo(Principal principal) {
        //如果是空证明spring security里面没有这个用户
        if (null == principal) {
            return null;
        }
        String username = principal.getName();
        User user = userService.getUserByUserName(username);
        //Do not return the password
        user.setPassword(null);
        return user;
    }

    /**
     * For the user register
     * @param user
     * @return
     */
    @ApiOperation("Register")
    @PostMapping("/register")
    public GeneralBean register(@RequestBody User user) {
        return userService.register(user);
    }



}
