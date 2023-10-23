package com.share2renew.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.share2renew.mapper.OrderMapper;
import com.share2renew.mapper.ShippingAddressMapper;
import com.share2renew.pojo.Comment;
import com.share2renew.mapper.CommentMapper;
import com.share2renew.pojo.GeneralBean;
import com.share2renew.pojo.Order;
import com.share2renew.pojo.ShippingAddress;
import com.share2renew.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Junxian Cai
 * @since 2023-09-07
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {
}
