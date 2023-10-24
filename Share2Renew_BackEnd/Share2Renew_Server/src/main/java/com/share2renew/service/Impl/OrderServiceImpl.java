package com.share2renew.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 服务实现类
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

    @Override
    public GeneralBean CreateOrder(Integer userId, Integer postId, Integer addressId) {
        if (userId == 0) {
            return GeneralBean.error("Login first");
        }
        Order order = new Order();
        SimpleDateFormat df = new SimpleDateFormat("yyddHHssSmm");
        SimpleDateFormat df2 = new SimpleDateFormat("SmmssDS");
        //String orderId = String.valueOf(Calendar.getInstance().getTimeInMillis());
        order.setOrderId(df2.format(new Date()));
        order.setTrackingId(df.format(new Date()));
        order.setPostId(postId);
        order.setUserId(userId);
        order.setOrderAddress(String.valueOf(shippingAddressMapper.selectById(addressId).getPostcode()) + " " + shippingAddressMapper.selectById(addressId).getAddressDetail());
        order.setOrderMobile(shippingAddressMapper.selectById(addressId).getAddressMobile());
        order.setOrderState(0);//0 下单生成订单  1已经付款    2收货
        order.setValidity(1);
        orderMapper.insert(order);
        return GeneralBean.success(order);
    }

    @Override
    public GeneralBean GetOrderOrSpecificByUserId(int pageNo, int pageSize, Integer userId, String title) {
        Page<PostOrderInfo> page = new Page<>(pageNo, pageSize);
        IPage<PostOrderInfo> iPage = orderMapper.GetAllOrSpecificByuserId(page, userId, title);
        Map<String, Object> data = new HashMap<>();
        data.put("total", iPage.getTotal());
        data.put("data", iPage.getRecords());
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
    public GeneralBean GetSoldOrSpecificByUserId(int pageNo, int pageSize, Integer userId, String title) {
        Page<PostOrderInfo> page = new Page<>(pageNo, pageSize);
        IPage<PostOrderInfo> iPage = orderMapper.GetSoldOrSpecificByuserId(page, userId, title);
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
        data.put("data", iPage.getRecords());
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
