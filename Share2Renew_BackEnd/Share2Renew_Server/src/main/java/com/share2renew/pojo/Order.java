package com.share2renew.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Junxian Cai
 * @since 2023-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_order")
@ApiModel(value="Order对象", description="")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("order_id")
    private Integer orderId;

    @TableField("user_id")
    private Integer userId;

    @TableField("post_id")
    private Integer postId;

    @TableField("order_mobile")
    private String orderMobile;

    @TableField("order_address")
    private String orderAddress;

    @TableField("tracking_id")
    private String trackingId;

    @TableField("order_state")
    private Integer orderState;

    private Integer validity;


}
