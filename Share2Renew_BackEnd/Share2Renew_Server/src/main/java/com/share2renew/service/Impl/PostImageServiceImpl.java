package com.share2renew.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.share2renew.pojo.GeneralBean;
import com.share2renew.pojo.PostImage;
import com.share2renew.mapper.PostImageMapper;
import com.share2renew.service.IPostImageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.share2renew.util.FastDFSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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
    @Autowired
    private IPostImageService postImageService;

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
        if (postImageByUserId != null && postImageByUserId.size() > 0) {
            return GeneralBean.success(postImageByUserId);
        } else if (postImageByUserId.size() == 0) {
            return GeneralBean.error("This post has no images");
        }
        return GeneralBean.error("Get images failed");
    }

    /**
     * uploadPostImageOnce
     * @param url
     * @param postId
     * @return
     */
    @Override
    public GeneralBean uploadPostImageOnce(String url, Integer postId) {
//        PostImage postImage = postImageMapper.selectById(postId);
        PostImage imageObject = new PostImage();
        long countPhoto = postImageService.count(new QueryWrapper<PostImage>().eq("post_id", postId));
        if (countPhoto == 0){

            imageObject.setPostId(postId);
            imageObject.setImageUrl(url);
            imageObject.setImageType(1);
            imageObject.setValidity(1);

            int result = postImageMapper.insert(imageObject);
            if (result == 1) {
                //修改了返回postImageId
                return GeneralBean.success("Upload image successfully!", imageObject.getPostImageId());
            } else {
                return GeneralBean.error("Upload image failed!");
            }
        } else if (countPhoto >= 1 && countPhoto < 5) {
            imageObject.setImageUrl(url);
            imageObject.setValidity(1);
            imageObject.setImageType(2);
            imageObject.setPostId(postId);

            int result = postImageMapper.insert(imageObject);
            if (result == 1) {
                //修改了返回postImageId
                return GeneralBean.success("Upload image successfully!", imageObject.getPostImageId());
            } else {
                return GeneralBean.error("Upload image failed!");
            }
        } else {
            return GeneralBean.error("Maximum Photo");
        }
    }

    /**
     * update image
     * @param url
     * @param postImageId
     * @return
     */
    @Override
    public GeneralBean updateImage(String url, Integer postImageId) {

        PostImage postImage = postImageMapper.selectById(postImageId);
        if (postImage != null) {

            String originalImageUrl = postImage.getImageUrl();

            String[] fastDFS = filterFastDFS(originalImageUrl);

            FastDFSUtils.deleteFile(fastDFS[0], fastDFS[1]);

            postImage.setImageUrl(url);
            int result = postImageMapper.updateById(postImage);
            if (result == 1) {
                return GeneralBean.success("Update image successfully!", url);
            }
        }
        return GeneralBean.error("Update image failed!");
    }

    /**
     * delete image
     * @param postImageId
     * @return
     */
    @Override
    public GeneralBean deleteImage(Integer postImageId) {

        PostImage postImage = postImageMapper.selectById(postImageId);
        String imageUrl = postImage.getImageUrl();
        String[] strings = filterFastDFS(imageUrl);
        FastDFSUtils.deleteFile(strings[0], strings[1]);
        postImage.setImageUrl(null);
        int result = postImageMapper.updateById(postImage);
        if (result == 1) {
            return GeneralBean.success("Delete image successfully!");
        }
        return GeneralBean.error("Delete image failed!");
    }

    public String[] filterFastDFS(String url){

        String[] result = new String[2];

        String[] parts = url.split("/", 5);

        String groupName = parts[3];
        String remoteFileName = parts[4];

        System.out.println("groupName: " + groupName);
        System.out.println("remoteFileName: " + remoteFileName);

        result[0] = groupName;
        result[1] = remoteFileName;

        return result;

    }
}
