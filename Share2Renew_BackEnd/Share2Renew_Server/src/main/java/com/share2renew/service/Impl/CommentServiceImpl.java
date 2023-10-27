package com.share2renew.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.share2renew.exception.ParamsException;
import com.share2renew.mapper.UserMapper;
import com.share2renew.pojo.Comment;
import com.share2renew.mapper.CommentMapper;
import com.share2renew.pojo.GeneralBean;
import com.share2renew.pojo.User;
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
    @Autowired
    private UserMapper userMapper;

    /**
     * Add a comment
     * @param comment
     * @param postId
     * @return
     */
    @Override
    public GeneralBean addComment(String commentContent, Integer postId) throws ParamsException {

        // Get the current userId
        int currentUserId = userService.getCurrentUserId();
        Comment comment = new Comment();
        comment.setUserId(currentUserId);
        comment.setCommentDetail(commentContent);
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

    /**
     * Get user's avatar by comment
     * @param commentId
     * @return
     */
    @Override
    public String getUserAvatarByComment(Integer commentId) {

        Comment comment = commentMapper.selectById(commentId);
        Integer userId = comment.getUserId();
        User user = userMapper.selectById(userId);
        String avatar = user.getAvatar();
        return avatar;
    }

    /**
     * Get all comments by username
     * @param username
     * @return
     */
    @Override
    public GeneralBean getAllCommentsByUsername(String username) {

        User user = userService.getUserByUserName(username);
        Integer userId = user.getUserId();

        List<Comment> commentList = commentMapper.selectList(new QueryWrapper<Comment>().eq("user_id", userId));
        if (commentList.size() >= 1) {
            return GeneralBean.success(commentList);
        } else {
            return GeneralBean.error("No comment found.");
        }
    }

    /**
     * Delete a comment
     * @param commentId
     * @return
     */
    @Override
    public GeneralBean deleteComment(Integer commentId) {

        Comment comment = commentMapper.selectById(commentId);
        comment.setValidity(0);
        int result = commentMapper.insert(comment);
        if (result == 1) {
            return GeneralBean.success("Delete comment successfully.");
        }
        return GeneralBean.error("Delete comment failed! Please try again.");
    }
}
