package com.share2renew.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("t_favourite_post")
@ApiModel(value="FavouritePost对象", description="")
public class FavouritePost implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "favourite_post_id", type = IdType.AUTO)
    private Integer favouritePostId;

    @TableField("post_id")
    private Integer postId;

    @TableField("user_id")
    private Integer userId;

    private Integer validity;


}
