package com.share2renew.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.share2renew.pojo.GeneralBean;
import com.share2renew.pojo.Post;
import com.share2renew.service.IPostService;
import com.share2renew.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
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
@RequestMapping("/post")
@Api(tags = "PostController")
public class PostController {
    /**
     * Kevin
     * */

    @Autowired
    private IPostService postService;

    /**
     *
     * @param pageNo 第几页
     * @param pageSize 一页显示几个
     * @param title 需要搜索的帖子title 为空则显示所有
     * @param categoryId  需要搜索的帖子种类 为空则显示所有
     * @return
     */
    @GetMapping("/getAllOrSpecificPost")
    @ApiOperation(value = "get all post with requirements")
    public GeneralBean getAllOrSpecificPost(@RequestParam(value = "pageNo") int pageNo,
                                     @RequestParam(value = "pageSize") int pageSize,
                                     @RequestParam(value = "title", required = false) String title,
                                     @RequestParam(value = "categoryId", required = false) Integer categoryId) {

        return postService.getAllOrSpecificPost(pageNo, pageSize, title, categoryId);
    }

    @GetMapping("/getAllPost")
    @ApiOperation(value = "get all post")
    public List<Post> getAllPost() {
        return postService.list();
    }

    @PostMapping("/createNewPost")
    @ApiOperation(value = "Create new post")
    public GeneralBean CreateNewPost(@RequestBody Post newpost) {
            return postService.createNewPost(newpost);
    }

    @GetMapping("/getPostByCategory")
    @ApiOperation(value = "Get a post by category")
    public GeneralBean GetPostByCategory(@RequestParam(value = "pageNo") int pageNo,
                                         @RequestParam(value = "pageSize") int pageSize,
                                         @RequestParam(value = "categoryId", required = false) int categoryId){
        return postService.getPostByCategory(pageNo,pageSize,categoryId);
    }

    @GetMapping("/getPostByUserId")
    @ApiOperation(value = "Get a post by user id")
    public GeneralBean GetPostByUserId(@RequestParam(value = "pageNo") int pageNo,
                                       @RequestParam(value = "pageSize") int pageSize,
                                       @RequestParam(value = "userId", required = false) int userId){
        return postService.getPostByUserId(pageNo,pageSize,userId);
    }


    @PutMapping("/updatePost")
    @ApiOperation(value = "Update a post")
    public GeneralBean UpdatePost(@RequestBody Post post){
        return postService.updatepost(post);
    }

    @PutMapping("updatePostByPostId")
    @ApiOperation(value = "update a post by postId")
    public GeneralBean UpdatePostByPostId(@RequestBody Post post){
        postService.updateById(post);
        return  GeneralBean.success("update post successfully");
    }


}