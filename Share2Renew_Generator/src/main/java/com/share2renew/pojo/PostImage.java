package com.share2renew.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2023-10-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_post_image")
@ApiModel(value="PostImage对象", description="")
public class PostImage implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "post_image_id", type = IdType.AUTO)
    private Integer postImageId;

    @TableField("post_id")
    private String postId;

    @TableField("image_url")
    private String imageUrl;

    @TableField("image_type")
    private Integer imageType;

    private Integer validity;


}
