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

    @GetMapping("/forExchangeBuyerGetPost")
    @ApiOperation(value = "forExchangeBuyerGetPost")
    public GeneralBean forExchangeBuyerGetPost(@RequestParam Integer userId) {
        return iOrderService.forExchangeBuyerGetPost(userId);
    }

    /**
     * for Exchange function Seller Get two Posts
     * @param userId
     * @return
     */
    @GetMapping("forExchangeSellerGetPost")
    @ApiOperation(value = "forExchangeSellerGetPost")
    public GeneralBean forExchangeSellerGetPost(@RequestParam Integer userId) {
        return iOrderService.forExchangeSellerGetPost(userId);
    }

    @GetMapping("/sellerGetOrder")
    @ApiOperation("Seller get order by userId")
    public GeneralBean SellerGetOrder(@RequestParam(value = "pageNo") int pageNo,
                                                  @RequestParam(value = "pageSize") int pageSize,
                                                  @RequestParam(value = "userId") Integer userId,
                                                  @RequestParam(value = "title", required = false) String title){
        return iOrderService.SellerGetOrder(pageNo, pageSize, userId, title);
    }

    @GetMapping("/sellerGetOrderByPurpose")
    @ApiOperation("seller get order by seller id and post_purpose")
    public GeneralBean SellerGetOrderByUserIdAndPurpose(@RequestParam(value = "pageNo") int pageNo,
                                                  @RequestParam(value = "pageSize") int pageSize,
                                                  @RequestParam(value = "userId") Integer userId,
                                                  @RequestParam(value = "post_purpose", required = false) int purpose) {
        return iOrderService.SellerGetOrdersByUserIdAndPostPurpose(pageNo, pageSize, userId, purpose);
    }

    @GetMapping("/sellerGetOrderByStatus")
    @ApiOperation("seller get order by seller id and order_status")
    public GeneralBean SellerGetOrderByUserIdAndStatus(@RequestParam(value = "pageNo") int pageNo,
                                                  @RequestParam(value = "pageSize") int pageSize,
                                                  @RequestParam(value = "userId") Integer userId,
                                                  @RequestParam(value = "order_state", required = false) int status) {
        return iOrderService.SellerGetOrdersByUserIdAndStatus(pageNo, pageSize, userId, status);
    }

    @GetMapping("/buyerGetOrderByPurpose")
    @ApiOperation("buyer get order by buyer id and post_purpose")
    public GeneralBean BuyerGetOrderByUserIdAndPurpose(@RequestParam(value = "pageNo") int pageNo,
                                                  @RequestParam(value = "pageSize") int pageSize,
                                                  @RequestParam(value = "userId") Integer userId,
                                                  @RequestParam(value = "post_purpose", required = false) int purpose) {
        return iOrderService.BuyerGetOrdersByUserIdAndPostPurpose(pageNo, pageSize, userId, purpose);
    }

    @GetMapping("/buyerGetOrderByStatus")
    @ApiOperation("buyer get order by buyer id and order_status")
    public GeneralBean BuyerGetOrderByUserIdAndStatus(@RequestParam(value = "pageNo") int pageNo,
                                                 @RequestParam(value = "pageSize") int pageSize,
                                                 @RequestParam(value = "userId") Integer userId,
                                                 @RequestParam(value = "order_state", required = false) int status) {
        return iOrderService.BuyerGetOrdersByUserIdAndStatus(pageNo, pageSize, userId, status);
    }


    @PostMapping("/cancelOrder")
    @ApiOperation("cancel order")
    public GeneralBean CancelOrder(@RequestParam(value = "order_id") Integer order_id) {
        return iOrderService.CancelOrder(order_id);
    }



}
