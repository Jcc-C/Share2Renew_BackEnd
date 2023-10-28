package com.share2renew.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.share2renew.mapper.AdminMapper;
import com.share2renew.mapper.UserMapper;
import com.share2renew.pojo.GeneralBean;
import com.share2renew.pojo.Post;
import com.share2renew.mapper.PostMapper;
import com.share2renew.service.IPostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.*;

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

    @Autowired
    private PostMapper postMapper;

    /**
     *
     * @param pageNo 第几页 必须输入
     * @param pageSize 一页显示几个 必须输入
     * @param title 需要搜索的帖子title 可为空 为空则显示所有
     * @param categoryId 需要显示的帖子种类 可为空 为空则显示所有
     * @return
     */
    @Override
    public GeneralBean getAllOrSpecificPost(int pageNo, int pageSize, String title, Integer categoryId) {
        LambdaQueryWrapper<Post> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasLength(title), Post::getPostTitle,title); //条件查询
        wrapper.eq(categoryId !=null, Post::getCategoryId, categoryId);
        Page<Post> page = new Page<>(pageNo, pageSize);
        postService.page(page,wrapper);

        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());//查询的总数
        data.put("rows",page.getRecords());//查询的数据
        return GeneralBean.success(data);
    }

    @Override
    public int createNewPost(Post post) {
        post.setValidity(1);
        postMapper.insert(post);
        return postMapper.GetPostId();
    }

    @Override
    public GeneralBean updatePost(Post post) {
        post.setValidity(1);
        postMapper.updateById(post);
        return GeneralBean.success("Update post successfully");
    }


    @Override
    public GeneralBean getPostByCategory(int pageNo, int pageSize, int categoryId) {
        LambdaQueryWrapper<Post> wapper = new LambdaQueryWrapper<Post>();
        wapper.eq(Post::getCategoryId,categoryId);

        Page<Post> page = new Page<>(pageNo, pageSize);
        postMapper.selectPage(page,wapper);

         Map<String, Object> data = new HashMap<String, Object>();
         data.put("total", page.getTotal());
         data.put("data", page.getRecords());
         return GeneralBean.success(data);
    }

    @Override
    public GeneralBean getPostByUserId(int pageNo, int pageSize, int userId) {
        LambdaQueryWrapper<Post> wapper = new LambdaQueryWrapper<Post>();
        wapper.eq(Post::getUserId,userId).eq(Post::getValidity,1);

        Page<Post> page = new Page<>(pageNo, pageSize);
        postMapper.selectPage(page,wapper);

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("total", page.getTotal());
        data.put("data", page.getRecords());
        return GeneralBean.success(data);
    }

    @Override
    public GeneralBean getPostByPostPurpose(int pageNo, int pageSize, int postPurpose) {
        LambdaQueryWrapper<Post> wapper = new LambdaQueryWrapper<Post>();
        wapper.eq(Post::getPostPurpose,postPurpose);

        Page<Post> page = new Page<>(pageNo, pageSize);
        postMapper.selectPage(page,wapper);

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("total", page.getTotal());
        data.put("data", page.getRecords());
        return GeneralBean.success(data);
    }

    @Override
    public GeneralBean GetPostDetail(Integer postId, Integer userId) {
        int isOrder = 1;//能否下单 1 能  0 不能
        if(postMapper.selectById(postId).getUserId().equals(userId)){
            isOrder = 0;
        }
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("isOrder", isOrder);
        data.put("postDetail", postMapper.selectById(postId));
        return GeneralBean.success(data);
    }

    @Override
    public GeneralBean deletePostByUser(Integer postId) {

        Post post = postMapper.selectById(postId);
        post.setValidity(0);
        int result = postMapper.updateById(post);
        if (result == 1) {
            return GeneralBean.success("Delete post successfully.");
        }
        return GeneralBean.error("Delete post failed! Please try again.");
    }
}
