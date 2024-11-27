package com.mashiro.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.mashiro.entity.Role;
import com.mashiro.enums.BaseStatus;
import com.mashiro.result.Result;
import com.mashiro.service.RoleMenuService;
import com.mashiro.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "角色管理")
@RestController
@RequestMapping("/system/role/")
public class RoleController {

    @Resource
    private RoleService roleService;
    @Resource
    private RoleMenuService roleMenuService;

    /**
     * 获取角色列表
     */
    @SaCheckPermission("sysRole:view")
    @Operation(summary = "获取角色列表")
    @GetMapping("list")
    public Result listRole() {
        List<Role> list = roleService.list();
        return Result.ok(list);
    }

    /**
     * 保存或更新角色信息并关联菜单
     */
    @SaCheckPermission("sysRole:add")
    @Operation(summary = "保存或更新角色信息并关联菜单")
    @PostMapping("saveOrUpdate")
    @Transactional
    public Result saveRole(@RequestBody Role role) {
        // 设置默认启用状态
        role.setStatus(BaseStatus.ENABLE);
        // 保存或更新角色
        boolean isSuccess = roleService.saveOrUpdate(role);
        if (!isSuccess) {
            return Result.error("角色保存失败");
        }
        // 先删除现有的角色菜单关联
        roleMenuService.deleteByRoleId(role.getId());

        // 插入新的角色菜单关联
        if (role.getMenuIds() != null) {
            roleMenuService.insertRoleMenu(role);
        }
        return Result.ok();
    }

    /**
     * 更新角色状态
     */
    @SaCheckPermission("sysRole:edit")
    @Operation(summary = "更新角色状态")
    @PostMapping("updateStatus")
    public Result updateStatus(@RequestParam long id, @RequestParam BaseStatus status) {
        LambdaUpdateWrapper<Role> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Role::getId, id);
        updateWrapper.set(Role::getStatus, status);
        roleService.update(updateWrapper);
        return Result.ok();
    }

    /**
     * 删除角色
     */
    @SaCheckPermission("sysRole:delete")
    @Operation(summary = "删除角色")
    @DeleteMapping("removeById")
    public Result removeRole(@RequestParam Integer id) {
        roleService.removeById(id);
        return Result.ok();
    }

    /**
     * 查询当前角色所拥有的菜单
     */
    @SaCheckPermission("sysRole:view")
    @Operation(summary = "查询当前角色所拥有的菜单")
    @GetMapping("getMenuIdsByRoleId")
    public Result<Map<String, Object>> getMenuIdsByRoleId(@RequestParam Long roleId) {
        Map<String, Object> roleMenuByRoleId = roleMenuService.getMenuIdsByRoleId(roleId);
        return Result.ok(roleMenuByRoleId);
    }
}
