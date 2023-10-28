package com.share2renew.service;

import com.share2renew.pojo.GeneralBean;
import com.share2renew.pojo.Post;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Junxian Cai
 * @since 2023-09-07
 */
public interface IPostService extends IService<Post> {

    /**
     * Get all post or specific post
     * @param pageNo
     * @param pageSize
     * @param title
     * @param categoryId
     * @return
     */
    GeneralBean getAllOrSpecificPost(int pageNo, int pageSize, String title, Integer categoryId);

    /**
     * Create new post
     * @param post
     * @return
     */
    int createNewPost(Post post);

    /**
     * Update post
     * @param post
     * @return
     */
    GeneralBean updatePost(Post post);

    /**
     * Get post by post id
     * @param pageNo
     * @param pageSize
     * @param categoryId
     * @return
     */
    GeneralBean getPostByCategory(int pageNo, int pageSize, int categoryId);

    /**
     * Get post by user id
     * @param pageNo
     * @param pageSize
     * @param userId
     * @return
     */
    GeneralBean getPostByUserId(int pageNo, int pageSize, int userId);

    /**
     * Get post by post purpose
     * @param pageNo
     * @param pageSize
     * @param postPurpose
     * @return
     */
    GeneralBean getPostByPostPurpose(int pageNo, int pageSize, int postPurpose);

    /**
     * Get post detail
     * @param postId
     * @param userId
     * @return
     */
    GeneralBean GetPostDetail(Integer postId, Integer userId);

    /**
     * Delete post by user
     * @param postId
     * @return
     */
    GeneralBean deletePostByUser(Integer postId);

    /**
     * Get all post
     * @return
     */
    List<Post> getAllPost();
}
