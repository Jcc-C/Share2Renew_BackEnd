package com.share2renew.service;

import com.share2renew.pojo.GeneralBean;
import com.share2renew.pojo.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Junxian Cai
 * @since 2023-09-07
 */
public interface IOrderService extends IService<Order> {

    GeneralBean CreateOrder(Integer userId, Integer postId, Integer addressId);


    GeneralBean BuyerGetOrder(int pageNo, int pageSize, Integer userId, String title);

    GeneralBean PayOrder(String orderId);

    GeneralBean GetOrderDetail(String orderId);

    GeneralBean SellerGetOrder(int pageNo, int pageSize, Integer buyerId, String title);
}
