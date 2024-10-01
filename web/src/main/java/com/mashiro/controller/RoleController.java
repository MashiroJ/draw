package com.mashiro.controller;

import com.mashiro.entity.Role;
import com.mashiro.result.Result;
import com.mashiro.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "角色管理")
@RestController
@RequestMapping("/system/role/")
public class RoleController {

    @Resource
    private RoleService roleService;

    @Operation(summary = "获取角色列表")
    @GetMapping("list")
    public Result listRole() {
        List<Role> list = roleService.list();
        return Result.ok(list);
    }

    @Operation(summary = "保存或更新角色")
    @PostMapping("saveOrUpdate")
    public Result saveRole(@RequestBody Role role) {
        roleService.saveOrUpdate(role);
        return Result.ok();
    }

    @Operation(summary = "删除角色")
    @DeleteMapping("removeById")
    public Result removeRole(@RequestParam Long id) {
        roleService.removeById(id);
        return Result.ok();
    }
}
