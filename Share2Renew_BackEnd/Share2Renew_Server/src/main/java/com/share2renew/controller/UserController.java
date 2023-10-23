package com.share2renew.controller;


import com.share2renew.pojo.GeneralBean;
import com.share2renew.pojo.User;
import com.share2renew.service.IUserService;
import com.share2renew.util.FastDFSUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.jws.soap.SOAPBinding;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Junxian Cai
 * @since 2023-09-07
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;
    //test

    /**
     * For user update password
     * The Front-end need to pass a Map(Key 分别是previousPass / newPass / userId -> 前端将这些数据获取后放到Map里传递给后端)
     * @param userInfo
     * @return
     */
    @ApiOperation(value = "Update Password")
    @PostMapping("/updatePassword")
    public GeneralBean updatePassword(@RequestBody Map<String, Object> userInfo) {
        return userService.updatePassword(userInfo);
    }

    @PutMapping("/updateUser")
    @ApiOperation(value = "updateUser")
    public GeneralBean updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @ApiOperation(value = "getALlUser")
    @GetMapping("/getAllUser")
    public List<User> getALlUser() {
        return userService.list();
    }

    /**
     * Update user avatar
     * @param file
     * @param userId
     * @param authentication
     * @return
     */
    @ApiOperation(value = "Update user avatar")
    @PostMapping("/updateAvatar")
    public GeneralBean updateUserAvatar(@RequestParam MultipartFile file, Integer userId, Authentication authentication) {
        //Upload file by FastDFS
        String[] uploadPath = FastDFSUtils.upload(file);
        //get the url
        String url = FastDFSUtils.getTrackerUrl() + uploadPath[0] + "/" + uploadPath[1];
        return userService.updateUserAvatar(url, userId, authentication);
    }

}
