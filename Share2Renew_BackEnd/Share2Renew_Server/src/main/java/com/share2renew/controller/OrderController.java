package com.share2renew.controller;


import com.share2renew.pojo.GeneralBean;
import com.share2renew.service.ICommentService;
import com.share2renew.service.IOrderService;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    private IOrderService iOrderService;

    @PostMapping("/createOrder")
    @ApiOperation("Create a new order")
    public GeneralBean CreateOrder(@RequestParam(value = "userId") Integer userId,
                                   @RequestParam(value = "postId") Integer postId,
                                   @RequestParam(value = "addressId") Integer addressId){
        return iOrderService.CreateOrder(userId, postId, addressId);
    }

    @PutMapping("/pay")
    @ApiOperation("Pay money")
    public GeneralBean PayOrder(@RequestParam(value = "orderId") String orderId){
        return iOrderService.PayOrder(orderId);
    }

    @GetMapping("/getOrderDetail")
    @ApiOperation("Get the detail of order")
    public GeneralBean GetOrderDetail(@RequestParam(value = "orderId") String orderId){
        return iOrderService.GetOrderDetail(orderId);
    }

    @GetMapping("/buyerGetOrder")
    @ApiOperation("Buyer Get order by userId")
    public GeneralBean BuyerGetOrder(@RequestParam(value = "pageNo") int pageNo,
                                                  @RequestParam(value = "pageSize") int pageSize,
                                                  @RequestParam(value = "userId") Integer userId,
                                                  @RequestParam(value = "title", required = false) String title){
        return iOrderService.BuyerGetOrder(pageNo, pageSize, userId, title);
    }

    @GetMapping("/sellerGetOrder")
    @ApiOperation("Seller get order by userId")
    public GeneralBean SellerGetOrder(@RequestParam(value = "pageNo") int pageNo,
                                                  @RequestParam(value = "pageSize") int pageSize,
                                                  @RequestParam(value = "userId") Integer userId,
                                                  @RequestParam(value = "title", required = false) String title){
        return iOrderService.SellerGetOrder(pageNo, pageSize, userId, title);
    }


}
