package com.mashiro.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.mashiro.entity.BaseEntity;
import com.mashiro.enums.BaseStatus;
import lombok.Data;

@Data
public class UpdateUserDto extends BaseEntity {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    @TableField(select = false)
    private String password;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 头像URL
     */
    private String avatarUrl;
}
