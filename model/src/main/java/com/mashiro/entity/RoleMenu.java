package com.mashiro.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 角色菜单关联表
 *
 * @TableName sys_role_menu
 */
@TableName(value = "sys_role_menu")
@Data
public class RoleMenu extends BaseEntity {

    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 菜单ID
     */
    private Integer menuId;

}