package com.share2renew.service;

import com.share2renew.pojo.GeneralBean;
import com.share2renew.pojo.Post;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.models.auth.In;

import java.time.LocalDate;
import java.util.Date;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Junxian Cai
 * @since 2023-09-07
 */
public interface IPostService extends IService<Post> {

    GeneralBean getAllOrSpecificPost(int pageNo, int pageSize, String title, Integer categoryId);

    int createNewPost(Post post);

    GeneralBean updatepost(Post post);


    GeneralBean getPostByCategory(int pageNo, int pageSize, int categoryId);

    GeneralBean getPostByUserId(int pageNo, int pageSize, int userId);
}
