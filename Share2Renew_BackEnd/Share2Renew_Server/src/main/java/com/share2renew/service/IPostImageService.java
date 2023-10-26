package com.share2renew.service;

import com.share2renew.pojo.GeneralBean;
import com.share2renew.pojo.PostImage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Junxian Cai
 * @since 2023-10-07
 */
public interface IPostImageService extends IService<PostImage> {

    /**
     * get all post images by userId
     *
     * @param postId
     * @return
     */
    GeneralBean getPostImageByUserId(Integer postId);

    GeneralBean uploadPostImage(List<String> imageUrls, Integer postId);

    /**
     * getPostImageByPostIdReturnUrl
     * @param postId
     * @return
     */
    GeneralBean getPostImageByPostIdReturnUrl(Integer postId);

    /**
     * uploadPostImageOnce
     * @param url
     * @param postId
     * @return
     */
    GeneralBean uploadPostImageOnce(String url, Integer postId);
}
