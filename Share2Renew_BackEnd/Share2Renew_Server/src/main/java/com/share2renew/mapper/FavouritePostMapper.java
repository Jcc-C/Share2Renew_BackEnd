package com.share2renew.mapper;

import com.share2renew.pojo.FavouritePost;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.share2renew.pojo.Post;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Junxian Cai
 * @since 2023-09-07
 */
public interface FavouritePostMapper extends BaseMapper<FavouritePost> {
    List<Post> GetFavouriteList(Integer userId, String title);
}
