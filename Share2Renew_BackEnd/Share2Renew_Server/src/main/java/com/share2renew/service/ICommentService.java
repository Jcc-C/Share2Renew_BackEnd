package com.share2renew.service;

import com.share2renew.exception.ParamsException;
import com.share2renew.pojo.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.share2renew.pojo.GeneralBean;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Junxian Cai
 * @since 2023-09-07
 */
public interface ICommentService extends IService<Comment> {

    /**
     * Add a comment
     * @param comment
     * @return
     */
    GeneralBean addComment(String commentContent, Integer postId) throws ParamsException;

    /**
     * Get all comments by postId
     * @param postId
     * @return
     */
    GeneralBean getAllCommentsByPostId(Integer postId);

    /**
     * Get user's avatar by comment
     * @param commentId
     * @return
     */
    String getUserAvatarByComment(Integer commentId);

    /**
     * Get all comments by username
     * @param username
     * @return
     */
    GeneralBean getAllCommentsByUsername(String username);

    /**
     * Delete a comment
     * @param commentId
     * @return
     */
    GeneralBean deleteComment(Integer commentId);
}
