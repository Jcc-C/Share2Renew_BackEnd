package com.share2renew.controller;


import com.share2renew.pojo.GeneralBean;
import com.share2renew.pojo.Order;
import com.share2renew.service.IOrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Junxian Cai
 * @since 2023-09-07
 */
@RestController
@RequestMapping("/order")
public class OrderController {


    @Autowired
    private IOrderService orderService;

    /**
     * Get all the order
     * @return
     */
    @ApiOperation(value = "get all the order")
    @GetMapping("/getAllOrder")
    public List<Order> getAllOrder() {
        return orderService.list();
    }


    /**
     * Get all the order by userId
     * @param userId
     * @return
     */
    @ApiOperation(value = "get all order by userId")
    @GetMapping("/getAllOrderByUserId")
    public GeneralBean getAllOrderByUserId(@RequestParam Integer userId) {
        return orderService.getAllOrderByUserId(userId);
    }


}
