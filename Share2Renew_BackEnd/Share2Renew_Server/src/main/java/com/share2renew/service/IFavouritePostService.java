package com.share2renew.service;

import com.share2renew.pojo.FavouritePost;
import com.baomidou.mybatisplus.extension.service.IService;
import com.share2renew.pojo.GeneralBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Junxian Cai
 * @since 2023-09-07
 */
public interface IFavouritePostService extends IService<FavouritePost> {

    GeneralBean AddTofavourite(Integer userId, Integer postId);

    GeneralBean GetFavouriteList(Integer userId, String title);

    GeneralBean DeleteFavourite(Integer userId, Integer postId);
}
