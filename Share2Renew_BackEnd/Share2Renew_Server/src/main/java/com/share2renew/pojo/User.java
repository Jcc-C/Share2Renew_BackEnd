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

    @ApiModelProperty(value = "id", hidden = true)
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    @ApiModelProperty(value = "users’ real name")
    @TableField("real_name")
    private String realName;

    @ApiModelProperty(value = "username")
    private String username;

    @ApiModelProperty(value = "password")
    private String password;

    @ApiModelProperty(value = "Can this user make a comment or not", hidden = true)
    @TableField("right_to_comment")
    private Integer rightToComment;

    @ApiModelProperty(value = "mobile phone number")
    private String mobile;

    @ApiModelProperty(value = "gender")
    private String gender;

    @ApiModelProperty(value = "email address")
    private String email;

    @ApiModelProperty(value = "users' location", hidden = true)
    private String location;

    @ApiModelProperty(value = "user avatar address", hidden = true)
    @TableField("avatar")
    private String avatar;

    @ApiModelProperty(value = "Is valid or not", hidden = true)
    @Getter(AccessLevel.NONE)
    //@TableLogic //Logic delete -> 0: Active / 1: Deleted
    private Boolean validity;

    @Override
    @ApiModelProperty(hidden = true)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    @ApiModelProperty(hidden = true)
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @ApiModelProperty(hidden = true)
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @ApiModelProperty(hidden = true)
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @ApiModelProperty(hidden = true)
    public boolean isEnabled() {
        return validity;
    }
}
