package com.mashiro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mashiro.entity.RoleMenu;

import java.util.Map;

/**
* @author mashiro
* @description 针对表【sys_role_menu(角色菜单关联表)】的数据库操作Service
* @createDate 2024-09-24 22:13:09
*/
public interface RoleMenuService extends IService<RoleMenu> {
    Map<String, Object> findSysRoleMenuByRoleId(Long roleId);
}
