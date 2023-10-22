package com.share2renew.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.share2renew.pojo.GeneralBean;
import com.share2renew.pojo.Order;
import com.share2renew.mapper.OrderMapper;
import com.share2renew.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.java2d.loops.FillRect;

import java.util.List;

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
    private OrderMapper orderMapper;


    /**
     * Get all the order by userId
     * @param userId
     * @return
     */
    @Override
    public GeneralBean getAllOrderByUserId(Integer userId) {

        List<Order> orderByUserId = orderMapper.selectList(new QueryWrapper<>(new Order()).eq("user_id", userId));
        if (orderByUserId != null) {
            return GeneralBean.success("Get all the order by userId successfully", orderByUserId);
        }
        return GeneralBean.error("Get all the order by userId failed");
    }
}
