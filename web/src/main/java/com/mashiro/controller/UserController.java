package com.mashiro.controller;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mashiro.dto.GrantRoleDto;
import com.mashiro.entity.User;
import com.mashiro.enums.BaseStatus;
import com.mashiro.result.Result;
import com.mashiro.service.FileService;
import com.mashiro.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
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
    private FileService fileService;
    /**
     * 获取用户信息
     * @return
     */
    @Operation(summary = "获取用户信息")
    @GetMapping("/userInfo")
    public Result<User> userInfo() {
        int id = StpUtil.getLoginIdAsInt();
        User user = userService.getById(id);
        return Result.ok(user);
    }
    /**
     * 用户分页实现
     */


    @Operation(summary = "用户分页查询")
    @GetMapping("/page")
    private Result<IPage<User>> pageInfo(@RequestParam long current, @RequestParam long size, User user) {
        Page<User> page = new Page<>(current, size);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(user.getPhone() != null, User::getPhone, user.getPhone());
        queryWrapper.eq(user.getStatus() != null, User::getStatus, user.getStatus());
        Page<User> result = userService.page(page, queryWrapper);
        return Result.ok(result);
    }

    /**
     * 保存或更新用户信息
     * @param user
     * @return
     */
    @Operation(summary = "保存或更新后台用户信息")
    @PostMapping("saveOrUpdate")
    public Result saveOrUpdate(@RequestBody User user) {
        // 密码加密
        if (user.getPassword() != null) {
            try {
                String encryptedPassword = SaSecureUtil.sha256(user.getPassword());
                user.setPassword(encryptedPassword);
            } catch (Exception e) {
                log.error("密码加密失败", e);
                return Result.error();
            }
        }
        //设置默认头像
        if (user.getAvatarUrl() ==null) {
            user.setAvatarUrl(DEFAULT_AVATAR_URL);
        }
        userService.saveOrUpdate(user);
        return Result.ok();
    }

    /**
     * 删除用户
     */
    @Operation(summary = "删除用户")
    @DeleteMapping("/deleteById")
    public Result removeUser(@RequestParam long id) {
        userService.removeById(id);
        return Result.ok();
    }

    /**
     * 更新用户头像
     */
    @Operation(summary = "更新用户头像")
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
     * 根据id更新用户状态
     */
    @Operation(summary = "更新用户状态")
    @PostMapping("/updateStatus")
    public Result updateStatus(@RequestParam long id, @RequestParam BaseStatus status) {
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(User::getId, id);
        updateWrapper.set(User::getStatus, status);
        userService.update(updateWrapper);
        return Result.ok();
    }

    /**
     * 通过用户id授权角色
     * @param grantRoleDto
     * @return
     */
    @Operation(summary = "通过用户id授权角色")
    @PostMapping("/grantRole")
    public Result<Void> grantRole(@RequestBody GrantRoleDto grantRoleDto) {
        userService.grantRole(grantRoleDto);
        return Result.ok();
    }
    /**
     * 删除用户所授权的角色
     * @param userId
     * @param roleId
     * @return
     */
    @Operation(summary = "删除用户所授权的角色")
    @DeleteMapping("/deleteRole")
    public Result<Void> deleteRole(@RequestParam Long userId, @RequestParam Long roleId) {
        userService.removeRole(userId, roleId);
        return Result.ok();
    }

    //查询用户所拥有的菜单
    @Operation(summary = "查询用户所拥有的菜单")
    @GetMapping("/getMenuIdsByUserId")
    public Result<Map<String,Object>> getMenuIdsByUserId(@RequestParam Long userId) {
        Map<String, Object> menuIdsByUserId = userService.getMenuIdsByUserId(userId);
        return Result.ok(menuIdsByUserId);
    }
}
