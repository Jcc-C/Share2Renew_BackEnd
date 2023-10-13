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
     * @return
     */
    @PostMapping("/getAllPostByPage")
    @ApiOperation(value = "getAllPostByPage")
    public GeneralBean GetAllThePost(@RequestParam(value = "pageNo") Long pageNo,
                                     @RequestParam(value = "pageSize") Long pageSize,
                                     @RequestParam(value = "title", required = false) String title){

        return postService.showAllPosts(pageNo, pageSize, title);
    }

    @GetMapping("/getAllPost")
    @ApiOperation(value = "getAllPost")
    public List<Post> getAllPost() {
        return postService.list();
    }

}
