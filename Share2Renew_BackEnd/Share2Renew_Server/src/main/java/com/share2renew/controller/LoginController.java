package com.share2renew.controller;

import com.share2renew.pojo.GeneralBean;
import com.share2renew.pojo.User;
import com.share2renew.pojo.UserLoginInfo;
import com.share2renew.service.IAdminService;
import com.share2renew.service.IUserService;
import freemarker.template.TemplateException;
import io.ipinfo.api.IPinfo;
import io.ipinfo.api.errors.RateLimitedException;
import io.ipinfo.api.model.IPResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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


    //Test upload
    @Autowired
    private IUserService userService;
    @Autowired
    private IAdminService adminService;
    private static final String GET_IP_INFO_URL = "http://ipinfo.io/ip";
    @Autowired
    private RestTemplate restTemplate;

    @ApiOperation(value = "Login then return token")
    @PostMapping("/login")
    public GeneralBean login(@RequestBody UserLoginInfo userLoginInfo, HttpServletRequest request) {
        return userService.login(userLoginInfo.getUsername(), userLoginInfo.getPassword(), request);
    }

    @ApiOperation(value = "Admin Login")
    @PostMapping("/adminLogin")
    public GeneralBean adminLogin(@RequestBody UserLoginInfo userLoginInfo, HttpServletRequest request) {
        return adminService.adminLogin(userLoginInfo.getUsername(), userLoginInfo.getPassword(), request);
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
    public GeneralBean register(@RequestBody User user) throws MessagingException, TemplateException, IOException {
        return userService.register(user);
    }

    //TODO: 用这个api得到登陆信息, 创建多一个表用于给管理员查看登陆信息
    @ApiOperation("Get ip info")
    @GetMapping("/getIpInfo")
    public String getIpInfo(HttpServletRequest request) throws RateLimitedException {
        IPinfo iPinfo = new IPinfo.Builder().setToken("94fa75aedcef6d").build();

        String ipAddress = restTemplate.getForObject(GET_IP_INFO_URL, String.class).trim();

        IPResponse ipResponse = iPinfo.lookupIP(ipAddress);
        String countryCode = ipResponse.getCountryCode();
        String region = ipResponse.getRegion();
        String ip = ipResponse.getIp();
        String city = ipResponse.getCity();
        return "You are in " + city + "/" + region + "/" + countryCode + " now.";
    }

}
