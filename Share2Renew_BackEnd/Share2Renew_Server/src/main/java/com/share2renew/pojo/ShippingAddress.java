package com.share2renew.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
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
@TableName("t_shipping_address")
@ApiModel(value="ShippingAddress对象", description="")
public class ShippingAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "shipping_address_id", type = IdType.AUTO)
    private Integer shippingAddressId;

    @TableField("user_id")
    private Integer userId;

    @TableField("postcode")
    private Integer postcode;

    @TableField("address_detail")
    private String addressDetail;

    @TableField("address_mobile")
    private String addressMobile;

    private Integer validity;


}
