package com.mashiro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mashiro.entity.RoleMenu;

import java.util.List;

/**
 * @author mashiro
 * @description 针对表【sys_role_menu(角色菜单关联表)】的数据库操作Mapper
 * @createDate 2024-09-24 22:13:09
 * @Entity com.mashiro.entity.RoleMenu
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {


    List<Long> findSysRoleMenuByRoleId(Long roleId);

    int batchRoleMenu(List<RoleMenu> list);
}




