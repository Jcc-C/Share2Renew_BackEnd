package com.share2renew.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

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
@TableName("t_comment")
@ApiModel(value="Comment对象", description="")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "comment_id", type = IdType.AUTO)
    @ApiModelProperty(value = "comment id", hidden = true)
    private Integer commentId;

    @TableField("comment_detail")
    private String commentDetail;

    @TableField("comment_date")
    @ApiModelProperty(value = "comment date", hidden = true)
    private Date commentDate;

    @TableField("user_id")
    @ApiModelProperty(value = "user id", hidden = true)
    private Integer userId;

    @TableField("post_id")
    @ApiModelProperty(value = "post id", hidden = true)
    private Integer postId;

    @ApiModelProperty(value = "likes", hidden = true)
    private Integer likes;

    @ApiModelProperty(value = "validity", hidden = true)
    private Integer validity;


}
