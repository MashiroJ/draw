package com.mashiro.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 用户角色关联表
 *
 * @TableName sys_user_role
 */
@TableName(value = "sys_user_role")
@Data
public class UserRole extends BaseEntity {

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 角色ID
     */
    private Integer roleId;

}