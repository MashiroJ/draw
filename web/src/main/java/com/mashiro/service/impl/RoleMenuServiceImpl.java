package com.mashiro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashiro.entity.Role;
import com.mashiro.entity.RoleMenu;
import com.mashiro.mapper.RoleMenuMapper;
import com.mashiro.service.MenuService;
import com.mashiro.service.RoleMenuService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mashiro
 * @description 针对表【sys_role_menu(角色菜单关联表)】的数据库操作Service实现
 * @createDate 2024-09-24 22:13:09
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu>
        implements RoleMenuService {
    @Resource
    private MenuService menuService;
    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Override
    public Map<String, Object> getMenuIdsByRoleId(Long roleId) {// 通过用户ID获取用户相关联的角色ID
        // 存储所有菜单ID
        List<Long> allRoleMenuIds = new ArrayList<>();
        List<Long> roleMenuIds = roleMenuMapper.findSysRoleMenuByRoleId(roleId);
        allRoleMenuIds.addAll(roleMenuIds);
        // 初始化结果Map，将所有菜单ID存储到Map中进行返回
        Map<String, Object> result = new HashMap<>();
        result.put("roleMenuIds", allRoleMenuIds);
        return result;
    }

    @Override
    public int insertRoleMenu(Role role) {
        int rows = 1;
        // 新增用户与角色管理
        List<RoleMenu> list = new ArrayList<>();
        for (int menuId : role.getMenuIds()) {
            RoleMenu rm = new RoleMenu();
            rm.setRoleId(Math.toIntExact(role.getId()));
            rm.setMenuId(menuId);
            list.add(rm);
        }
        if (list.size() > 0) {
            rows = roleMenuMapper.batchRoleMenu(list);
        }
        return rows;
    }

    @Override
    public void deleteByRoleId(Long id) {
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId, id);
        this.remove(queryWrapper);
    }
}




