package com.share2renew.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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


import java.text.SimpleDateFormat;
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
        order.setOrderState(0);//0 下单生成订单  1已经付款    2收货   3取消订单
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
    public GeneralBean SellerGetOrdersByUserIdAndStatus(int pageNo, int pageSize, int userId, int status) {
        Page<PostOrderInfo> page = new Page<>(pageNo, pageSize);
        IPage<PostOrderInfo> iPage = orderMapper.SellerGetOrderByUserIdAndStatus(page, userId, status);
        Map<String, Object> data = new HashMap<>();
        data.put("total", iPage.getTotal());
        data.put("data", iPage.getRecords());
        return GeneralBean.success(data);
    }

    @Override
    public GeneralBean SellerGetOrdersByUserIdAndPostPurpose(int pageNo, int pageSize, int userId, int post_purpose) {

        Page<PostOrderInfo> page = new Page<>(pageNo, pageSize);
        IPage<PostOrderInfo> iPage = orderMapper.SellerGetOrderByUserIdAndPostPurpose(page, userId, post_purpose);
        Map<String, Object> data = new HashMap<>();
        data.put("total", iPage.getTotal());
        data.put("data",iPage.getRecords());
        return GeneralBean.success(data);

    }

    @Override
    public GeneralBean BuyerGetOrdersByUserIdAndStatus(int pageNo, int pageSize, int userId, int status) {
        Page<PostOrderInfo> page = new Page<>(pageNo, pageSize);
        IPage<PostOrderInfo> iPage = orderMapper.BuyerGetOrderByUserIdAndStatus(page, userId, status);
        Map<String, Object> data = new HashMap<>();
        data.put("total", iPage.getTotal());
        data.put("data", iPage.getRecords());
        return GeneralBean.success(data);
    }

    @Override
    public GeneralBean BuyerGetOrdersByUserIdAndPostPurpose(int pageNo, int pageSize, int userId, int post_purpose) {

        Page<PostOrderInfo> page = new Page<>(pageNo, pageSize);
        IPage<PostOrderInfo> iPage = orderMapper.BuyerGetOrderByUserIdAndPostPurpose(page, userId, post_purpose);
        Map<String, Object> data = new HashMap<>();
        data.put("total", iPage.getTotal());
        data.put("data",iPage.getRecords());
        return GeneralBean.success(data);

    }


    @Override
    public GeneralBean CancelOrder(int orderId) {
        orderMapper.CancelOrder(orderId);
        return GeneralBean.success("Cancel order successful");
    }

    /**
     * for Exchange function Buyer Get two Posts
     * @param userId
     * @return
     */
    @Override
    public GeneralBean forExchangeBuyerGetPost(Integer userId) {
        Order order = orderMapper.selectOne(new QueryWrapper<Order>().eq("buyer_id", userId).eq("order_state", 1));
        Integer exchangePostId = order.getExchangePostId();
        Integer originPostId = order.getPostId();

        Post exchangePost = postMapper.selectById(exchangePostId);
        Post originPost = postMapper.selectById(originPostId);

        List<Post> exchangePostList = new ArrayList<>();
        exchangePostList.add(exchangePost);
        exchangePostList.add(originPost);

        return GeneralBean.success(exchangePostList);
    }

    /**
     * for Exchange function Seller Get two Posts
     * @param userId
     * @return
     */
    @Override
    public GeneralBean forExchangeSellerGetPost(Integer userId) {
        Order order = orderMapper.selectOne(new QueryWrapper<Order>().eq("seller_id", userId).eq("order_state", 1));
        Integer exchangePostId = order.getExchangePostId();
        Integer originPostId = order.getPostId();

        Post exchangePost = postMapper.selectById(exchangePostId);
        Post originPost = postMapper.selectById(originPostId);

        List<Post> exchangePostList = new ArrayList<>();
        exchangePostList.add(exchangePost);
        exchangePostList.add(originPost);

        return GeneralBean.success(exchangePostList);
    }

    /**
     * Create a new order for exchange
     * exchangePostId - 买家的postId / postId - 卖家的postId
     * @param buyerId
     * @param postId
     * @param exchangePostId
     * @param addressId
     * @return
     */
    @Override
    public GeneralBean createOrderForExchange(Integer buyerId, Integer postId, Integer exchangePostId, Integer addressId) {


        Order order = new Order();
        Post post = new Post();
        SimpleDateFormat df = new SimpleDateFormat("yyddHHssSmm");
        SimpleDateFormat df2 = new SimpleDateFormat("SmmssDS");

        order.setOrderId(df2.format(new Date()));
        order.setTrackingId(df.format(new Date()));

        order.setPostId(postId);
        order.setExchangePostId(exchangePostId);
        order.setBuyerId(buyerId);
        order.setSellerId(postMapper.selectById(postId).getUserId());

        order.setOrderAddress(String.valueOf(shippingAddressMapper.selectById(addressId).getPostcode())+" "+shippingAddressMapper.selectById(addressId).getAddressDetail());
        order.setOrderMobile(shippingAddressMapper.selectById(addressId).getAddressMobile());
        order.setOrderState(0);//0 下单生成订单  1已经付款    2收货   3取消订单
        order.setValidity(1);
        order.setOrderDate(new Date());
        post.setPostId(postId);
        post.setValidity(2); //validity = "2" 商品已经出售 设置为不可购买
        postMapper.updateById(post);
        orderMapper.insert(order);
        return GeneralBean.success(order);
    }

    /**
     * for User Get Sell Order
     * @param userId
     * @return
     */
    @Override
    public GeneralBean forUserGetSellOrder(Integer userId) {
        List<Order> orderList = orderMapper.selectList(new QueryWrapper<Order>().eq("seller_id", userId).eq("validity", 1));
        List<PostOrderInfo> postList = new ArrayList<>();

        for (Order order : orderList) {
            PostOrderInfo postOrderInfo = new PostOrderInfo();
            Post post = postMapper.selectById(order.getPostId());

            postOrderInfo.setPostId(post.getPostId());
            postOrderInfo.setPostTitle(post.getPostTitle());
            postOrderInfo.setPostPurpose(post.getPostPurpose());
            postOrderInfo.setPrice(post.getPrice());
            postOrderInfo.setCategoryId(post.getCategoryId());
            postOrderInfo.setBuyerId(order.getBuyerId());
            postOrderInfo.setSellerId(order.getSellerId());
            postOrderInfo.setOrderId(order.getOrderId());
            postOrderInfo.setOrderMobile(order.getOrderMobile());
            postOrderInfo.setOrderAddress(order.getOrderAddress());
            postOrderInfo.setTrackingId(order.getTrackingId());
            postOrderInfo.setOrderState(order.getOrderState());

            postList.add(postOrderInfo);
        }
        return GeneralBean.success(postList);
    }


}
