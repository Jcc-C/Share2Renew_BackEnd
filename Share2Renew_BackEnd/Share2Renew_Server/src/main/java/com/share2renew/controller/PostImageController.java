package com.share2renew.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.share2renew.pojo.GeneralBean;
import com.share2renew.pojo.PostImage;
import com.share2renew.service.IPostImageService;
import com.share2renew.util.FastDFSUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Junxian Cai
 * @since 2023-10-07
 */
@RestController
@RequestMapping("/post-image")
public class PostImageController {

    @Autowired
    private IPostImageService postImageService;

//    @GetMapping("/getPostImageByPostId")
//    @ApiOperation(value = "get all post image by userId")
//    public GeneralBean getPostImageByUserId(@RequestParam Integer postId) {
//        return postImageService.getPostImageByUserId(postId);
//    }

    /**
     * 是按顺序取出来的，所以第一张永远是封面的照片
     * @param postId
     * @return
     */
    @GetMapping("/getPostImageByPostIdReturnUrl")
    @ApiOperation(value = "get all post imageFiles by postId return url")
    public GeneralBean getPostImageByPostIdReturnUrl(@RequestParam Integer postId) {
        return postImageService.getPostImageByPostIdReturnUrl(postId);
    }

    //返回image对象
    @GetMapping("/getAllImageByPostIdReturnObject")
    @ApiOperation(value = "get all post images by postId return object")
    public List<PostImage> getAllImageByPostIdReturnObject(@RequestParam Integer postId) {
        List<PostImage> postImageList = postImageService.list(new QueryWrapper<PostImage>().eq("post_id", postId));
        if (postImageList.size() != 0) {
            return postImageList;
        }else {
            return null;
        }
    }

    @PostMapping("/updateImage")
    @ApiOperation(value = "update image")
    public GeneralBean updateImage(@RequestParam MultipartFile multipartFile, @RequestParam Integer postImageId) {
        //Upload file by FastDFS
        String[] uploadPath = FastDFSUtils.upload(multipartFile);
        //get the url
        String url = FastDFSUtils.getTrackerUrl() + uploadPath[0] + "/" + uploadPath[1];

        return postImageService.updateImage(url, postImageId);

    }


    @PostMapping(value = "/uploadPostImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "upload post imageFiles")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "imageFiles", value = "上传的图片文件", dataType = "__file", paramType = "form", allowMultiple = true )
    })
    public GeneralBean uploadPostImage(@RequestParam List<MultipartFile> imageFiles, @RequestParam Integer postId) {

        //For storage the image urls
        List<String> imageUrls = new ArrayList<String>();
        // Get all the files
        for (MultipartFile file : imageFiles) {
            if (file != null) {
                String[] uploadPath = FastDFSUtils.upload(file);
                //get the url
                String url = FastDFSUtils.getTrackerUrl() + uploadPath[0] + "/" + uploadPath[1];
                imageUrls.add(url);
            }
        }
        return postImageService.uploadPostImage(imageUrls, postId);
    }

    @PostMapping("/deleteImage")
    @ApiOperation(value = "delete image")
    public GeneralBean deleteImage(@RequestParam Integer postImageId) {
        return postImageService.deleteImage(postImageId);
    }

    @ApiOperation(value = "upload photo once a time")
    @PostMapping(value = "/uploadPostImageOnce")
    public GeneralBean uploadPostImageOnce(@RequestParam MultipartFile file, @RequestParam Integer postId) {
        //Upload file by FastDFS
        String[] uploadPath = FastDFSUtils.upload(file);
        //get the url
        String url = FastDFSUtils.getTrackerUrl() + uploadPath[0] + "/" + uploadPath[1];

        return postImageService.uploadPostImageOnce(url, postId);
    }

    @ApiOperation(value = "拿总数")
    @PostMapping("/getImagesCount")
    public GeneralBean getImagesCount(@RequestParam Integer postId) {
        QueryWrapper<PostImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("post_id", postId);
        Long count = postImageService.count(queryWrapper);
        return GeneralBean.success(count);
    }



}
