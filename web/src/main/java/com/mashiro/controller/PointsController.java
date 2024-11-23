package com.mashiro.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.mashiro.result.Result;
import com.mashiro.service.PointsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "积分管理")
@Slf4j
@RestController
@RequestMapping("/system/points/")
public class PointsController {

    @Resource
    private PointsService pointsService;

    @Operation(summary = "签到获取积分")
    @PostMapping("/signIn")
    public Result<String> signIn() {
        long userId = StpUtil.getLoginIdAsLong();
        return pointsService.signIn(userId);
    }

    @Operation(summary = "查询当前用户积分")
    @GetMapping("/getPoints")
    public Result<Integer> getPoints() {
        long userId = StpUtil.getLoginIdAsLong();
        return pointsService.getPoints(userId);
    }

    @Operation(summary = "扣除用户积分")
    @PostMapping("/deductPoints")
    public Result<String> deductPoints(int points) {
        long userId = StpUtil.getLoginIdAsLong();
        return pointsService.deductPoints(userId, points);
    }
}
