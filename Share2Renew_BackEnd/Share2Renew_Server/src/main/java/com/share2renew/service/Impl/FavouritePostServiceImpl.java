package com.share2renew.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.share2renew.pojo.FavouritePost;
import com.share2renew.mapper.FavouritePostMapper;
import com.share2renew.pojo.GeneralBean;
import com.share2renew.pojo.Post;
import com.share2renew.service.IFavouritePostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Junxian Cai
 * @since 2023-09-07
 */
@Service
public class FavouritePostServiceImpl extends ServiceImpl<FavouritePostMapper, FavouritePost> implements IFavouritePostService {

    @Autowired
    private FavouritePostMapper favouritePostMapper;

    @Override
    public GeneralBean AddTofavourite(Integer userId, Integer postId) {
        FavouritePost favouritePost = new FavouritePost();
        favouritePost.setPostId(postId);
        favouritePost.setUserId(userId);
        favouritePost.setValidity(1);
        favouritePostMapper.insert(favouritePost);
        return GeneralBean.success("Add to favourite successfully");
    }

    @Override
    public GeneralBean GetFavouriteList(Integer userId, String title) {
        return GeneralBean.success(favouritePostMapper.GetFavouriteList(userId,title));
    }

    @Override
    public GeneralBean DeleteFavourite(Integer userId, Integer postId) {
        LambdaQueryWrapper<FavouritePost> wapper = new LambdaQueryWrapper<>();
        wapper.and(i->i.eq(FavouritePost::getPostId,postId).eq(FavouritePost::getUserId,userId));
        favouritePostMapper.delete(wapper);
        return GeneralBean.success("delete successfully");
    }
}
