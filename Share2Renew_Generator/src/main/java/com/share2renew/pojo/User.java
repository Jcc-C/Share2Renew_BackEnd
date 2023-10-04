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
 * @since 2023-10-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user")
@ApiModel(value="User对象", description="")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId("user_id")
    private Integer userId;

    @ApiModelProperty(value = "users’ real name")
    @TableField("real_name")
    private String realName;

    @ApiModelProperty(value = "username")
    private String username;

    @ApiModelProperty(value = "password")
    private String password;

    @ApiModelProperty(value = "Can this user make a comment or not")
    @TableField("right_to_comment")
    private Integer rightToComment;

    @ApiModelProperty(value = "mobile phone number")
    private String mobile;

    @ApiModelProperty(value = "gender")
    private String gender;

    @ApiModelProperty(value = "email address
")
    private String email;

    private String location;

    @ApiModelProperty(value = "is it valid or not")
    private Integer validity;


}
