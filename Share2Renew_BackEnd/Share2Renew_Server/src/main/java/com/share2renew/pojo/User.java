package com.share2renew.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Collection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
@TableName("t_user")
@ApiModel(value="User对象", description="")
public class User implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "user_id", type = IdType.AUTO)
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

    @ApiModelProperty(value = "email address")
    private String email;

    @ApiModelProperty(value = "users' location")
    private String location;

    @ApiModelProperty(value = "user avatar address")
    @TableField("avatar")
    private String avatar;

    @ApiModelProperty(value = "Is valid or not")
    @Getter(AccessLevel.NONE)
    //@TableLogic //Logic delete -> 0: Active / 1: Deleted
    private Boolean validity;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return validity;
    }
}
