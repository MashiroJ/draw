package com.mashiro.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mashiro.enums.BaseStatus;
import lombok.Data;

import java.util.List;

/**
 * 系统角色表
 *
 * @TableName sys_role
 */
@TableName(value = "sys_role")
@Data
public class Role extends BaseEntity {

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 角色状态：1正常，0禁用
     */
    private BaseStatus status;

    /** 菜单组 */
    @TableField(exist = false)
    private List<Integer> menuIds;
}
