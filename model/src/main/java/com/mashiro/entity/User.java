package com.mashiro.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mashiro.enums.BaseStatus;
import lombok.Data;

/**
 * 系统用户表
 *
 * @TableName sys_user
 */
@TableName(value = "sys_user")
@Data
public class User extends BaseEntity {
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

    /**
     * 用户状态：1正常，0禁用
     */
    private BaseStatus status;

    /**
     * 积分
     */
    private Integer points;
}