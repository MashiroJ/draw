package com.mashiro.controller;

import com.mashiro.entity.Menu;
import com.mashiro.mapper.MenuMapper;
import com.mashiro.result.Result;
import com.mashiro.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "菜单管理")
@RequestMapping("/system/menu")
@RestController
public class MenuController {

    @Resource
    private MenuService menuService;
    //获取菜单列表
    @GetMapping("list")
    @Operation(summary = "获取菜单列表")
    public Result listMenu(){
        List<Menu> list = menuService.list();
        return Result.ok(list);
    }

    //保存或更新菜单
    @PostMapping("saveOrUpdate")
    @Operation(summary = "保存或更新菜单")
    public Result saveOrUpdate(@RequestBody Menu menu) {
        menuService.saveOrUpdate(menu);
        return Result.ok();
    }
    //删除菜单
    @DeleteMapping("removeById")
    @Operation(summary = "删除菜单")
    public Result removeMenu(@RequestParam Long id) {
        menuService.removeById(id);
        return Result.ok();
    }
}
