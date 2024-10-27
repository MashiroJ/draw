package com.mashiro.controller;


import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mashiro.dto.LoginDto;
import com.mashiro.dto.RegisterDto;
import com.mashiro.entity.User;
import com.mashiro.enums.BaseRole;
import com.mashiro.exception.DrawException;
import com.mashiro.mapper.RoleMapper;
import com.mashiro.result.Result;
import com.mashiro.result.ResultCodeEnum;
import com.mashiro.service.LoginService;
import com.mashiro.service.UserService;
import com.mashiro.vo.CaptchaVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@Tag(name = "登录管理")
@RestController
@RequestMapping("/system/login")
public class LoginController {

    @Resource
    private LoginService loginService;

    @Resource
    private UserService userService;
    @Resource
    private RoleMapper roleMapper;

    /**
     * 注册
     * @param registerDto
     * @return
     */
    @Operation(summary = "注册")
    @PostMapping("register")
    public Result<User> register(@RequestBody RegisterDto registerDto){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, registerDto.getUsername());
        if (userService.getOne(queryWrapper) != null) {
            throw new DrawException(ResultCodeEnum.ADMIN_ACCOUNT_EXIST_ERROR);
        }
        if (userService.getOne(queryWrapper) != null) {
            throw new DrawException(ResultCodeEnum.ADMIN_ACCOUNT_EXIST_ERROR);
        }

        User user = userService.register(registerDto);
        if (user == null) {
            throw new DrawException(ResultCodeEnum.USER_REGISTRATION_FAILED);
        }

        Long userId = user.getId();
        if (userId == null) {
            throw new DrawException(ResultCodeEnum.USER_ID_NOT_FOUND);
        }

        roleMapper.grantRoleByid(userId, BaseRole.USER);
        return Result.ok();
    }

    /**
     * 获取验证码
     * @return
     */
    @Operation(summary = "获取验证码")
    @GetMapping("/captcha")
    public Result<CaptchaVo> getCaptcha() {
        CaptchaVo result = loginService.getCaptcha();
        return Result.ok(result);
    }

    /**
     * 登录
     * @param loginDto
     * @return
     */
    @Operation(summary = "登录")
    @PostMapping("")
    public SaResult login(@RequestBody LoginDto loginDto) {
        SaResult login = loginService.login(loginDto);
        return SaResult.data(login.getData());
    }

    /**
     * 是否登录
     * @return
     */
    @Operation(summary = "是否登录")
    @GetMapping("isLogin")
    public SaResult isLogin() {
        return SaResult.ok("是否登录：" + StpUtil.isLogin());
    }

    /**
     * 获取token信息
     * @return
     */
    @Operation(summary = "获取token信息")
    @GetMapping("tokenInfo")
    public SaResult tokenInfo() {
        return SaResult.data(StpUtil.getTokenInfo());
    }

    /**
     * 退出登录
     * @return
     */
    @Operation(summary = "退出登录")
    @PostMapping("logout")
    public SaResult logout() {
        StpUtil.logout();
        return SaResult.ok();
    }
}