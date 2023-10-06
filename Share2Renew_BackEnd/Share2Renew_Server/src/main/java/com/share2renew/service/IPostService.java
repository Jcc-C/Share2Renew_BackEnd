package com.share2renew.service;

import com.share2renew.pojo.GeneralBean;
import com.share2renew.pojo.Post;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Junxian Cai
 * @since 2023-09-07
 */
public interface IPostService extends IService<Post> {

    GeneralBean showAllPosts(Long pageNo, Long pageSize, String title);
}
