package com.share2renew.controller;


import com.share2renew.exception.ParamsException;
import com.share2renew.pojo.Comment;
import com.share2renew.pojo.GeneralBean;
import com.share2renew.service.ICommentService;
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
 * @since 2023-09-07
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private ICommentService commentService;


    @ApiOperation(value = "add a comment")
    @PostMapping("/addComment")
    public GeneralBean addComment(@RequestParam String commentContent, @RequestParam Integer postId) throws ParamsException {
        return commentService.addComment(commentContent, postId);
    }

    @ApiOperation(value = "get all comments by postId")
    @GetMapping("/getAllCommentsByPostId")
    public GeneralBean getAllCommentsByPostId(@RequestParam Integer postId) {
        return commentService.getAllCommentsByPostId(postId);
    }

    @ApiOperation(value = "get all comments by username")
    @GetMapping("/getAllCommentsByUserName")
    public GeneralBean getAllCommentsByUserId(Principal principal) {

        if (principal == null) {
            return null;
        }else {
            String username = principal.getName();
            return commentService.getAllCommentsByUsername(username);
        }
    }

    @ApiOperation(value = "get user' avatar by comment")
    @PostMapping("/getUserAvatarByComment")
    public String getUserAvatarByComment(@RequestParam Integer commentId) {
        return commentService.getUserAvatarByComment(commentId);
    }

    @ApiOperation(value = "delete a comment")
    @PostMapping("/deleteComment")
    public GeneralBean deleteComment(@RequestParam Integer commentId) {
        return commentService.deleteComment(commentId);
    }




}
