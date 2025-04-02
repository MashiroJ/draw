package com.mashiro.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.mashiro.entity.Menu;
import com.mashiro.result.Result;
import com.mashiro.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "菜单管理")
@RequestMapping("/system/menu")
@RestController
public class MenuController {

    @Resource
    private MenuService menuService;

    /**
     * 获取菜单列表
     */
    @SaCheckLogin
    @GetMapping("list")
    @Operation(summary = "获取菜单列表")
    public Result listMenu() {
        List<Menu> menus = menuService.queryListMenu();
//        List<Menu> list = menuService.list();
        return Result.ok(menus);
    }

    /**
     * 添加或更新菜单
     */
    @SaCheckPermission("sysMenu:add")
    @PostMapping("saveOrUpdate")
    @Operation(summary = "保存或更新菜单")
    public Result saveOrUpdate(@RequestBody Menu menu) {
        menuService.saveOrUpdate(menu);
        return Result.ok();
    }

    /**
     * 删除菜单
     */
    @SaCheckPermission("sysMenu:delete")
    @DeleteMapping("removeById")
    @Operation(summary = "删除菜单")
    public Result removeMenu(@RequestParam Long id) {
        menuService.removeById(id);
        return Result.ok();
    }
}
