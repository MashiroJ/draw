package com.mashiro.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mashiro.dto.UpdatePasswordDto;
import com.mashiro.dto.UpdateUserDto;
import com.mashiro.entity.User;
import com.mashiro.enums.BaseRole;
import com.mashiro.enums.BaseStatus;
import com.mashiro.exception.DrawException;
import com.mashiro.mapper.RoleMapper;
import com.mashiro.result.Result;
import com.mashiro.result.ResultCodeEnum;
import com.mashiro.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.simpleframework.xml.core.Validate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import static com.mashiro.constant.UserConstant.DEFAULT_AVATAR_URL;
import static com.mashiro.constant.UserConstant.DEFAULT_POINTS;

@Tag(name = "用户管理")
@Slf4j
@RestController
@RequestMapping("/system/user")
public class UserController {

    // 用于记录签到时间的缓存
    private final ConcurrentHashMap<Long, Long> signInCache = new ConcurrentHashMap<>();

    @Resource
    private UserService userService;
    @Resource
    private RoleMapper roleMapper;

    /**
     * 获取当前登录用户的信息
     *
     * @return 包含用户信息的 Result 对象
     */
    @Operation(summary = "获取当前登录用户信息")
    @GetMapping("/userInfo")
    public Result<User> userInfo() {
        int id = StpUtil.getLoginIdAsInt();
        User user = userService.getById(id);
        return Result.ok(user);
    }

    @Operation(summary = "根据Id获取用户信息")
    @GetMapping("/userInfoById")
    public Result<User> userInfoById(@RequestParam long id) {
        User user = userService.getById(id);
        return Result.ok(user);
    }

    /**
     * 分页查询用户信息
     *
     * @param current 当前页码
     * @param size    每页显示的记录数
     * @param user    包含查询条件的用户对象
     * @return 分页查询结果
     */
    @Operation(summary = "分页查询用户信息")
    @GetMapping("/page")
    public Result<IPage<User>> pageInfo(@RequestParam long current, @RequestParam long size, User user) {
        // 记录请求参数日志
        log.info("分页查询用户信息: current={}, size={}, user={}", current, size, user);

        // 创建分页对象
        Page<User> page = new Page<>(current, size);

        // 构建查询条件
        LambdaQueryWrapper<User> queryWrapper = buildQueryWrapper(user);

        // 执行分页查询
        Page<User> result = userService.page(page, queryWrapper);

        // 返回查询结果
        return Result.ok(result);
    }

    /**
     * 构建用户查询条件
     *
     * @param user 包含查询条件的用户对象
     * @return 查询条件包装器
     */
    private LambdaQueryWrapper<User> buildQueryWrapper(User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();

        // 如果用户名不为空，则添加模糊查询条件
        Optional.ofNullable(user.getUsername())
                .ifPresent(username -> queryWrapper.like(User::getUsername, username));

        // 如果手机号不为空，则添加模糊查询条件
        Optional.ofNullable(user.getPhone())
                .ifPresent(phone -> queryWrapper.like(User::getPhone, phone));

        // 如果状态不为空，则添加精确匹配条件
        Optional.ofNullable(user.getStatus())
                .ifPresent(status -> queryWrapper.eq(User::getStatus, status));

        return queryWrapper;
    }

    /**
     * 新增用户信息
     *
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
        // 设置默认状态为启用
        user.setStatus(BaseStatus.ENABLE);
        // 设置默认积分
        user.setPoints(DEFAULT_POINTS);
        // 保存用户
        if (userService.save(user)) {
            // 获取新用户的ID,并授权普通角色
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
     *
     * @param user
     * @return
     */
    @Operation(summary = "更新用户信息")
    @PatchMapping("/updateUser")
    @Transactional
    public Result<Void> updateUser(@RequestBody UpdateUserDto user) {

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
     *
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
     *
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
     *
     * @param id     用户ID
     * @param status 新的用户状态
     * @return 执行操作的结果
     */
    @SaCheckRole("管理员")
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
     *
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
     *
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
     *
     * @param userId 用户ID
     * @return 用户拥有的角色信息
     */
    @Operation(summary = "查询用户拥有的角色")
    @GetMapping("/getRoleIdsByUserId")
    public Result getRoleIdsByUserId(@RequestParam Long userId) {
        List<Long> roleIdsByUserId = userService.getRoleIdsByUserId(userId);
        // 假设您需要处理每个角色ID
        for (Long roleId : roleIdsByUserId) {
            BaseRole role = BaseRole.fromCode(roleId.intValue());
            return Result.ok(role);
        }

        return Result.error("无效的角色ID");
    }

    /**
     * 查询用户拥有的菜单
     *
     * @param userId 用户ID
     * @return 用户拥有的菜单信息
     */
    @Operation(summary = "查询用户拥有的菜单")
    @GetMapping("/getMenuIdsByUserId")
    public Result<Map<String, Object>> getMenuIdsByUserId(@RequestParam Long userId) {
        Map<String, Object> menuIdsByUserId = userService.getMenuIdsByUserId(userId);
        return Result.ok(menuIdsByUserId);
    }

    /**
     * 查询用户拥有的权限
     *
     * @param userId
     * @return
     */
    @Operation(summary = "查询用户拥有的权限")
    @GetMapping("/getPermissions")
    public List<String> getPermissions(@RequestParam Long userId) {
        return userService.getPermissionsByUserId(userId);
    }

    /**
     * 修改当前用户的密码
     *
     * @param updatePasswordDto
     * @return
     */
    @Operation(summary = "修改当前用户的密码")
    @PostMapping("/updatePassword")
    public Result updatePassword(@RequestBody @Validate UpdatePasswordDto updatePasswordDto) {
        // 获取用户输入的原始密码、新密码和确认密码
        String oldPassword = updatePasswordDto.getOldPassword();
        String newPassword = updatePasswordDto.getNewPassword();
        String confirmPassword = updatePasswordDto.getConfirmPassword();

        // 获取当前登录用户的信息，并显式包含密码字段
        long loginId = StpUtil.getLoginIdAsLong();
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId, loginId)
                .select(User::getId, User::getPassword); // 指定要查询的字段
        User user = userService.getOne(queryWrapper);

        // 判断用户是否存在
        if (user == null) {
            throw new DrawException(ResultCodeEnum.USER_NOT_FOUND);
        }

        // 判断旧密码是否正确
        if (!user.getPassword().equals(SaSecureUtil.sha256(oldPassword))) {
            throw new DrawException(ResultCodeEnum.ADMIN_PASSWORD_ERROR);
        }

        // 判断新密码和确认密码是否一致
        if (!newPassword.equals(confirmPassword)) {
            throw new DrawException(ResultCodeEnum.PASSWORD_MISMATCH);
        }

        String sha256Password = SaSecureUtil.sha256(newPassword);
        user.setPassword(sha256Password);
        userService.updateById(user);
        return Result.ok();
    }
}