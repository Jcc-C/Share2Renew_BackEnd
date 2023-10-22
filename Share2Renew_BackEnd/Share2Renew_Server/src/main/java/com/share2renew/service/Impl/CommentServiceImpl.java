package com.share2renew.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.share2renew.exception.ParamsException;
import com.share2renew.pojo.Comment;
import com.share2renew.mapper.CommentMapper;
import com.share2renew.pojo.GeneralBean;
import com.share2renew.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.share2renew.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Junxian Cai
 * @since 2023-09-07
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {


    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private IUserService userService;

    /**
     * Add a comment
     * @param comment
     * @param postId
     * @return
     */
    @Override
    public GeneralBean addComment(Comment comment, Integer postId) throws ParamsException {

        // Get the current userId
        int currentUserId = userService.getCurrentUserId();
        comment.setUserId(currentUserId);
        comment.setPostId(postId);
        comment.setCommentDate(new Date());
        comment.setLikes(0);
        comment.setValidity(1);

        int result = commentMapper.insert(comment);
        if (result == 1) {
            return GeneralBean.success("Add comment successfully");
        }
        return GeneralBean.error("Add comment failed");
    }

    /**
     * Get all comments by postId
     * @param postId
     * @return
     */
    @Override
    public GeneralBean getAllCommentsByPostId(Integer postId) {
        List<Comment> commentByPost = commentMapper.selectList(new QueryWrapper<Comment>().eq("post_id", postId));
        if (commentByPost.size() > 0) {
            return GeneralBean.success(commentByPost);
        }
        return GeneralBean.error("Current post have no comment.");
    }
}
