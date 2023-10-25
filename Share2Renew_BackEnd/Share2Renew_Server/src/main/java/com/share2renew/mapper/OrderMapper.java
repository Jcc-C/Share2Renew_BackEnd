package com.share2renew.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.share2renew.pojo.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.share2renew.pojo.PostOrderInfo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Junxian Cai
 * @since 2023-09-07
 */
public interface OrderMapper extends BaseMapper<Order> {
    IPage<PostOrderInfo> BuyerGetOrder(IPage<PostOrderInfo> page, int userId, String title);

    IPage<PostOrderInfo> SellerGetOrder(IPage<PostOrderInfo> page, int userId, String title);
    void UpdatePayStatus(String orderId);
    List<PostOrderInfo> GetOrderDetail(String orderId);

    IPage<PostOrderInfo> SellerGetOrderByUserIdAndPostPurpose(IPage<PostOrderInfo> page, int userId, int post_purpose);

    IPage<PostOrderInfo> SellerGetOrderByUserIdAndStatus(IPage<PostOrderInfo> page, int userId, int order_state);

    IPage<PostOrderInfo> BuyerGetOrderByUserIdAndPostPurpose(IPage<PostOrderInfo> page, int userId, int post_purpose);

    IPage<PostOrderInfo> BuyerGetOrderByUserIdAndStatus(IPage<PostOrderInfo> page, int userId, int order_state);

    void CancelOrder(int orderId);
}
