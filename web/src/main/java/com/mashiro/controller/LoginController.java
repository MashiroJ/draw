package com.mashiro.controller;


import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.mashiro.dto.LoginDto;
import com.mashiro.result.Result;
import com.mashiro.result.ResultCodeEnum;
import com.mashiro.service.LoginService;
import com.mashiro.vo.CaptchaVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@Tag(name = "登录管理")
@RestController
@RequestMapping("/login")
public class LoginController {

    @Resource
    private LoginService loginService;

    @Operation(summary = "获取验证码")
    @GetMapping("/captcha")
    public Result<CaptchaVo> getCaptcha() {
        CaptchaVo result = loginService.getCaptcha();
        return Result.ok(result);
    }

    @Operation(summary = "登录")
    @PostMapping("")
    public SaResult login(@RequestBody LoginDto loginDto) {
        loginService.login(loginDto);
        return SaResult.ok("登录成功");
    }
    @Operation(summary = "是否登录")
    @GetMapping("isLogin")
    public SaResult isLogin() {
        return SaResult.ok("是否登录：" + StpUtil.isLogin());
    }

    @Operation(summary = "获取token信息")
    @GetMapping("tokenInfo")
    public SaResult tokenInfo() {
        return SaResult.data(StpUtil.getTokenInfo());
    }

    @Operation(summary = "退出登录")
    @PostMapping("logout")
    public SaResult logout() {
        StpUtil.logout();
        return SaResult.ok();
    }




}