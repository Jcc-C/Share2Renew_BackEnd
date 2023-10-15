package com.share2renew.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDate;
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
@TableName("t_post")
@ApiModel(value="Post对象", description="")
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "post_id", type = IdType.AUTO)
    private Integer postId;

    @TableField("post_detail")
    private String postDetail;

    @TableField("post_title")
    private String postTitle;

    @TableField("post_date")
    private LocalDate postDate;

    @TableField("post_purpose")
    private Integer postPurpose;

    private Integer price;

    @TableField("category_id")
    private Integer categoryId;

    @TableField("user_id")
    private Integer userId;

    @TableLogic(value = "1",delval = "0") //逻辑处理注解（逻辑删除）value = "1" 默认的原值，delval = "0" 删除后的值
    private Integer validity;


}
