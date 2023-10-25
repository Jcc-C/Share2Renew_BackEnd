package com.share2renew.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.share2renew.mapper.PostMapper;
import com.share2renew.mapper.ShippingAddressMapper;
import com.share2renew.pojo.GeneralBean;
import com.share2renew.pojo.Order;
import com.share2renew.mapper.OrderMapper;
import com.share2renew.pojo.Post;
import com.share2renew.pojo.PostOrderInfo;
import com.share2renew.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.SimpleDateFormat;
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
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Autowired
    private ShippingAddressMapper shippingAddressMapper;

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private PostMapper postMapper;

    @Override
    public GeneralBean CreateOrder(Integer buyerId, Integer postId, Integer addressId) {
        Date date = new Date();
        if(buyerId == 0)
        {
            return GeneralBean.error("Login first");
        }
        Order order = new Order();
        Post post = new Post();
        SimpleDateFormat df = new SimpleDateFormat("yyddHHssSmm");
        SimpleDateFormat df2 = new SimpleDateFormat("SmmssDS");
        //String orderId = String.valueOf(Calendar.getInstance().getTimeInMillis());
        order.setOrderId(df2.format(new Date()));
        order.setTrackingId(df.format(new Date()));
        order.setPostId(postId);
        order.setBuyerId(buyerId);
        order.setSellerId(postMapper.selectById(postId).getUserId());
        order.setOrderAddress(String.valueOf(shippingAddressMapper.selectById(addressId).getPostcode())+" "+shippingAddressMapper.selectById(addressId).getAddressDetail());
        order.setOrderMobile(shippingAddressMapper.selectById(addressId).getAddressMobile());
        order.setOrderState(0);//0 下单生成订单  1已经付款    2收货
        order.setValidity(1);
        order.setOrderDate(date);
        post.setPostId(postId);
        post.setValidity(2); //validity = "2" 商品已经出售 设置为不可购买
        postMapper.updateById(post);
        orderMapper.insert(order);
        return GeneralBean.success(order);
    }

    @Override
    public GeneralBean BuyerGetOrder(int pageNo, int pageSize, Integer userId, String title) {
        Page<PostOrderInfo> page = new Page<>(pageNo, pageSize);
        IPage<PostOrderInfo> iPage = orderMapper.BuyerGetOrder(page, userId, title);
        Map<String, Object> data = new HashMap<>();
        data.put("total", iPage.getTotal());
        data.put("data",iPage.getRecords());
        return GeneralBean.success(data);
    }


    @Override
    public GeneralBean PayOrder(String orderId) {
        orderMapper.UpdatePayStatus(orderId);
        return GeneralBean.success(orderMapper.selectById(orderId));
    }

    @Override
    public GeneralBean GetOrderDetail(String orderId) {
        return GeneralBean.success(orderMapper.GetOrderDetail(orderId));
    }

    @Override
    public GeneralBean SellerGetOrder(int pageNo, int pageSize, Integer userId, String title) {
        Page<PostOrderInfo> page = new Page<>(pageNo, pageSize);
        IPage<PostOrderInfo> iPage = orderMapper.SellerGetOrder(page, userId, title);
        Map<String, Object> data = new HashMap<>();
        data.put("total", iPage.getTotal());
        data.put("data", iPage.getRecords());
        return GeneralBean.success(data);
    }

    @Override
    public GeneralBean GetOrdersByUserIdAndStatus(int pageNo, int pageSize, int userId, int status) {
        Page<PostOrderInfo> page = new Page<>(pageNo, pageSize);
        IPage<PostOrderInfo> iPage = orderMapper.GetOrdersByUserIdAndStatus(page, userId, status);
        Map<String, Object> data = new HashMap<>();
        data.put("total", iPage.getTotal());
        data.put("data", iPage.getRecords());
        return GeneralBean.success(data);
    }

    @Override
    public GeneralBean GetOrdersByUserIdAndPostPurpose(int pageNo, int pageSize, int userId, int post_purpose) {

        Page<PostOrderInfo> page = new Page<>(pageNo, pageSize);
        IPage<PostOrderInfo> iPage = orderMapper.GetOrderByUserIdAndPostPurpose(page, userId, post_purpose);
        Map<String, Object> data = new HashMap<>();
        data.put("total", iPage.getTotal());
        data.put("data",iPage.getRecords());
        return GeneralBean.success(data);

    }


    @Override
    public GeneralBean CancelOrder(int orderId) {
        return null;
    }

    @Override
    public GeneralBean GetUserOrderCount(int userId) {
        return null;
    }


}
