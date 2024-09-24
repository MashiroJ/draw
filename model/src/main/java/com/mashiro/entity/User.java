package com.mashiro.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
     * 手机号，用作登录名
     */
    private String phone;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 头像URL
     */
    private String avatarUrl;

    /**
     * 用户状态：1正常，0禁用
     */
    private Integer status;

}