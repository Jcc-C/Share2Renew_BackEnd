package com.share2renew.mapper;

import com.share2renew.pojo.PostImage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Junxian Cai
 * @since 2023-10-07
 */
public interface PostImageMapper extends BaseMapper<PostImage> {

    /**
     * get post image by post id
     * @param postId
     * @return
     */
    List<PostImage> getPostImageByPostId(Integer postId);

    /**
     * get post image url by post id
     * @param postId
     * @return
     */
    List<String> getPostImageByPostIdReturnUrl(Integer postId);
}
