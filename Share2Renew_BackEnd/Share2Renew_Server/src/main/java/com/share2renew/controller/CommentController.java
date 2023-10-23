package com.share2renew.controller;


import com.share2renew.exception.ParamsException;
import com.share2renew.pojo.Comment;
import com.share2renew.pojo.GeneralBean;
import com.share2renew.service.ICommentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public GeneralBean addComment(@RequestBody Comment comment, @RequestParam Integer postId) throws ParamsException {
        return commentService.addComment(comment, postId);
    }

    @ApiOperation(value = "get all comments by postId")
    @GetMapping("/getAllCommentsByPostId")
    public GeneralBean getAllCommentsByPostId(@RequestParam Integer postId) {
        return commentService.getAllCommentsByPostId(postId);
    }



}
