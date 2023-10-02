package com.share2renew.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
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

    private Integer validity;


}
