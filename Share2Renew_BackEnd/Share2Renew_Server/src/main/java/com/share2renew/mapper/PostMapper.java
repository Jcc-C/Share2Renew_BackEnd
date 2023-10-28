package com.share2renew.mapper;

import com.share2renew.pojo.Post;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Junxian Cai
 * @since 2023-09-07
 */
@Repository
public interface PostMapper extends BaseMapper<Post> {
    int GetPostId ();

    List<Post> getAllPost();
}
