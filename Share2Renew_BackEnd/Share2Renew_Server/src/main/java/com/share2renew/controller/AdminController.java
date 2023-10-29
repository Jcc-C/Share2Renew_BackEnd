package com.share2renew.controller;


import com.share2renew.pojo.Admin;
import com.share2renew.pojo.GeneralBean;
import com.share2renew.pojo.Post;
import com.share2renew.pojo.User;
import com.share2renew.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Junxian Cai
 * @since 2023-10-07
 */
@RestController
@RequestMapping("/admin")
@Api(tags = "AdminController")
public class AdminController {

    //TODO: JWT好像也算API

    @Autowired
    private IAdminService adminService;

    @ApiOperation(value = "registerAdmin")
    @PostMapping("/registerAdmin")
    public GeneralBean registerAdmin(@RequestBody Admin admin) {
        return adminService.registerAdmin(admin);
    }

    @ApiOperation(value = "get current admin")
    @GetMapping("/admin/info")
    public Admin getCurrentAdmin(Principal principal) {
        return adminService.getCurrentAdmin(principal);
    }

    @ApiOperation(value = "modify user validity")
    @PostMapping("/modifyUserValidity")
    public void modifyUserValidity(@RequestParam("userId") int userId, @RequestParam("validity") boolean validity) {

        adminService.modifyUserValidity(userId, validity);
    }

    @ApiOperation(value = "modify post validity")
    @PostMapping("/modifyPostValidity")
    public void modifyPostValidity(@RequestParam("postId") int postId, @RequestParam("validity") int validity) {

        adminService.modifyPostValidity(postId, validity);

    }

    @ApiOperation(value = "modify user's right to comment")
    @PostMapping("/modifyUserCommentRight")
    public void modifyUserCommentRight(@RequestParam("userId") int userId, @RequestParam("commentRight") int commentRight) {

        adminService.modifyUserCommentRight(userId, commentRight);
    }




}
