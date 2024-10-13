package com.mashiro.controller;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mashiro.dto.UpdateUserDto;
import com.mashiro.entity.User;
import com.mashiro.enums.BaseRole;
import com.mashiro.enums.BaseStatus;
import com.mashiro.mapper.RoleMapper;
import com.mashiro.result.Result;
import com.mashiro.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.mashiro.constant.UserConstant.DEFAULT_AVATAR_URL;

@Tag(name = "用户管理")
@Slf4j
@RestController
@RequestMapping("/system/user")
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private RoleMapper roleMapper;

    /**
     * 获取当前登录用户的信息
     * @return 包含用户信息的 Result 对象
     */
    @Operation(summary = "获取当前登录用户信息")
    @GetMapping("/userInfo")
    public Result<User> userInfo() {
        int id = StpUtil.getLoginIdAsInt();
        User user = userService.getById(id);
        return Result.ok(user);
    }

    /**
     * 分页查询用户信息
     * @param current 当前页码
     * @param size 每页显示的记录数
     * @param user 用户查询条件
     * @return 分页后的用户信息
     */
    @Operation(summary = "分页查询用户信息")
    @GetMapping("/page")
    public Result<IPage<User>> pageInfo(@RequestParam long current, @RequestParam long size, User user) {
        Page<User> page = new Page<>(current, size);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(user.getPhone() != null, User::getPhone, user.getPhone());
        queryWrapper.eq(user.getStatus() != null, User::getStatus, user.getStatus());
        Page<User> result = userService.page(page, queryWrapper);
        return Result.ok(result);
    }

    /**
     * 新增用户信息
     * @param user 用户信息对象
     * @return 执行操作的结果
     */
    @Operation(summary = "新增用户信息")
    @PostMapping("/save")
    public Result save(@RequestBody User user) {
        // 对于非空密码进行加密
        if (user.getPassword() != null) {
            try {
                String encryptedPassword = SaSecureUtil.sha256(user.getPassword());
                user.setPassword(encryptedPassword);
            } catch (Exception e) {
                log.error("密码加密失败", e);
                return Result.error();
            }
        }
        // 设置默认头像
        user.setAvatarUrl(DEFAULT_AVATAR_URL);
        user.setStatus(BaseStatus.ENABLE);
        // 保存用户
        if (userService.save(user)) {
            // 获取新用户的ID
            Long userId = user.getId();
            if (userId != null) {
                log.info("用户信息保存或更新成功，用户ID: {}", userId);
                roleMapper.grantRoleByid(userId, BaseRole.USER);
            } else {
                log.warn("用户ID未能正确获取");
                return Result.error("用户ID未能正确获取");
            }
        }
        return Result.ok();
    }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    @Operation(summary = "更新用户信息")
    @PatchMapping("/updateUser")
    @Transactional
    public Result<Void> updateUser(@RequestBody UpdateUserDto user){

        LambdaUpdateWrapper<User> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(User::getId, user.getId())
                .set(user.getUsername() != null && !user.getUsername().trim().isEmpty(), User::getUsername, user.getUsername())
                .set(user.getPhone() != null && !user.getPhone().trim().isEmpty(), User::getPhone, user.getPhone())
                .set(user.getPassword() != null && !user.getPassword().trim().isEmpty(), User::getPassword, user.getPassword())
                .set(user.getAvatarUrl() != null && !user.getAvatarUrl().trim().isEmpty(), User::getAvatarUrl, user.getAvatarUrl());

        userService.update(lambdaUpdateWrapper);
        return Result.ok();
    }

    /**
     * 根据用户ID删除用户
     * @param id 用户ID
     * @return 执行操作的结果
     */
    @Operation(summary = "根据用户ID删除用户")
    @DeleteMapping("/deleteById")
    public Result removeUser(@RequestParam long id) {
        userService.removeById(id);
        return Result.ok();
    }

    /**
     * 更新当前登录用户的头像
     * @param avatarUrl 新的头像URL
     * @return 执行操作的结果
     */
    @Operation(summary = "更新当前用户头像")
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam String avatarUrl) {
        int id = StpUtil.getLoginIdAsInt();
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(User::getId, id);
        updateWrapper.set(User::getAvatarUrl, avatarUrl);
        userService.update(updateWrapper);
        return Result.ok();
    }

    /**
     * 更新用户状态
     * @param id 用户ID
     * @param status 新的用户状态
     * @return 执行操作的结果
     */
    @Operation(summary = "更新用户状态信息")
    @PostMapping("/updateStatus")
    public Result updateStatus(@RequestParam long id, @RequestParam BaseStatus status) {
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(User::getId, id);
        updateWrapper.set(User::getStatus, status);
        userService.update(updateWrapper);
        return Result.ok();
    }

    /**
     * 为用户分配角色
     * @param userId 用户ID
     * @param roleId 要分配的角色
     * @return 执行操作的结果
     */
    @Operation(summary = "为用户分配角色")
    @PostMapping("/grantRole")
    public Result<Void> grantRole(@RequestParam long userId, @RequestParam BaseRole roleId) {
        userService.grantRole(userId, roleId);
        return Result.ok();
    }

    /**
     * 删除用户的指定角色
     * @param userId 用户ID
     * @param roleId 角色ID
     * @return 执行操作的结果
     */
    @Operation(summary = "删除用户的指定角色")
    @DeleteMapping("/deleteRole")
    public Result<Void> deleteRole(@RequestParam Long userId, @RequestParam BaseRole roleId) {
        userService.removeRole(userId, roleId);
        return Result.ok();
    }

    /**
     * 查询用户拥有的角色
     * @param userId 用户ID
     * @return 用户拥有的角色信息
     */
    @Operation(summary = "查询用户拥有的角色")
    @GetMapping("/getRoleIdsByUserId")
    public Result getRoleIdsByUserId(@RequestParam Long userId) {
        Long roleIdsByUserId = userService.getRoleIdsByUserId(userId);
        BaseRole role = BaseRole.fromCode(roleIdsByUserId.intValue());
        if (role != null) {
            return Result.ok(role);
        } else {
            return Result.error("无效的角色ID");
        }
    }

    /**
     * 查询用户拥有的菜单
     * @param userId 用户ID
     * @return 用户拥有的菜单信息
     */
    @Operation(summary = "查询用户拥有的菜单")
    @GetMapping("/getMenuIdsByUserId")
    public Result<Map<String, Object>> getMenuIdsByUserId(@RequestParam Long userId) {
        Map<String, Object> menuIdsByUserId = userService.getMenuIdsByUserId(userId);
        return Result.ok(menuIdsByUserId);
    }
}