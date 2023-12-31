package com.share2renew.controller;


import com.share2renew.pojo.GeneralBean;
import com.share2renew.pojo.Post;
import com.share2renew.service.IPostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return postService.getAllPost();
    }

    @PostMapping("/createNewPost")
    @ApiOperation(value = "Create new post")
    public int CreateNewPost(@RequestBody Post newpost) {
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
        return postService.updatePost(post);
    }

    @PutMapping("/updatePostByPostId")
    @ApiOperation(value = "update a post by postId")
    public GeneralBean UpdatePostByPostId(@RequestBody Post post){
        boolean result = postService.updateById(post);
        if (result = true) {
            return  GeneralBean.success("update post successfully.");
        }
        return  GeneralBean.error("update post failed!");
    }

    @PutMapping("/getPostByPostPurpose")
    @ApiOperation(value = "get posts by post purpose")
    public GeneralBean GetPostByPostPurpose(@RequestParam(value = "pageNo") int pageNo,
                                            @RequestParam(value = "pageSize") int pageSize,
                                            @RequestParam(value = "postPurpose", required = false) int postPurpose) {
        return postService.getPostByPostPurpose(pageNo, pageSize, postPurpose);

    }

    @GetMapping("/getPostByPostPurposeAndUserId")
    @ApiOperation(value = "get posts by post purpose and user id")
    public GeneralBean getPostByPostPurposeAndUserId(@RequestParam Integer postPurpose, @RequestParam Integer userId) {
        return postService.getPostByPostPurposeAndUserId(postPurpose, userId);
    }


    @GetMapping("/getPostDetail")
    @ApiOperation("Get detail of the post")
    public GeneralBean GetPostDetail(@RequestParam(value = "postId") Integer postId,
                                     @RequestParam(value = "userId") Integer userId){
        return postService.GetPostDetail(postId,userId);
    }

    @PostMapping("/deletePost")
    @ApiOperation(value = "For user delete post")
    public GeneralBean deletePstByUser(@RequestParam Integer postId) {
        return postService.deletePostByUser(postId);
    }


}