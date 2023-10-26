package com.share2renew.service;

import com.share2renew.exception.ParamsException;
import com.share2renew.pojo.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.share2renew.pojo.GeneralBean;
import io.swagger.models.auth.In;

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
    GeneralBean addComment(Comment comment, Integer postId) throws ParamsException;

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
}
