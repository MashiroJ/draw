package com.mashiro.controller;


import cn.dev33.satoken.util.SaResult;
import com.mashiro.dto.LoginDto;
import com.mashiro.result.Result;
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



}