package com.share2renew.service.Impl;

import com.share2renew.pojo.GeneralBean;
import com.share2renew.pojo.PostImage;
import com.share2renew.mapper.PostImageMapper;
import com.share2renew.service.IPostImageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Junxian Cai
 * @since 2023-10-07
 */
@Service
public class PostImageServiceImpl extends ServiceImpl<PostImageMapper, PostImage> implements IPostImageService {

    @Autowired
    private PostImageMapper postImageMapper;

    /**
     * get all post images by userId
     *
     * @param postId
     * @return
     */
    @Override
    public GeneralBean getPostImageByUserId(Integer postId) {

        List<PostImage> postImageByUserId = postImageMapper.getPostImageByPostId(postId);
        if (postImageByUserId != null) {
            return GeneralBean.success(postImageByUserId);
        }
        return GeneralBean.error("Get images failed");
    }

    @Override
    public GeneralBean uploadPostImage(List<String> imageUrls, Integer postId) {

        List<String> imageResult = new ArrayList<String>();

        for (int i = 0; i < imageUrls.size(); i++) {
            PostImage postImage = new PostImage();
            //默认第一张照片是封面type: 1
            if (i == 0) {
                postImage.setImageType(1);
            } else {
                postImage.setImageType(2);
            }
            postImage.setImageUrl(imageUrls.get(i));
            postImage.setValidity(1);
            postImage.setPostId(postId);
            int result = postImageMapper.insert(postImage);
            if (result != 1) {
                return GeneralBean.error("Save images failed!");
            }
            imageResult.add(imageUrls.get(i));
        }
        return GeneralBean.success("Upload images successfully!", imageResult);
    }

    /**
     * getPostImageByPostIdReturnUrl
     * @param postId
     * @return
     */
    @Override
    public GeneralBean getPostImageByPostIdReturnUrl(Integer postId) {
        List<String> postImageByUserId = postImageMapper.getPostImageByPostIdReturnUrl(postId);
        if (postImageByUserId != null) {
            return GeneralBean.success(postImageByUserId);
        }
        return GeneralBean.error("Get images failed");
    }
}
