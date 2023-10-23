package com.share2renew.pojo;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostOrderInfo {

    private Integer postId;
    private String postTitle;
    private Integer postPurpose;
    private Integer price;
    private Integer categoryId;
    private Integer buyerId;
    private Integer sellerId;
    private String orderId;
    private String orderMobile;
    private String orderAddress;
    private String trackingId;
    private Integer orderState;

}
