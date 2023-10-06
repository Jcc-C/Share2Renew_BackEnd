package com.share2renew.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.share2renew.pojo.GeneralBean;
import com.share2renew.pojo.Post;
import com.share2renew.mapper.PostMapper;
import com.share2renew.service.IPostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Junxian Cai
 * @since 2023-09-07
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements IPostService {

    @Autowired
    private IPostService postService;

    @Override
    public GeneralBean showAllPosts(Long pageNo, Long pageSize, String title) {
        LambdaQueryWrapper<Post> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasLength(title), Post::getPostTitle,title); //条件查询

        Page<Post> page = new Page<>(pageNo, pageSize);
        postService.page(page,wrapper);

        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());//查询的总数
        data.put("rows",page.getRecords());//查询的数据
        return GeneralBean.success(data);
    }
}
