package com.mashiro.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.mashiro.entity.User;
import com.mashiro.exception.DrawException;
import com.mashiro.result.Result;
import com.mashiro.result.ResultCodeEnum;
import com.mashiro.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Tag(name = "积分管理")
@Slf4j
@RestController
@RequestMapping("/system/points/")
public class PointsController {

    @Resource
    private UserService userService;

    // 用于记录签到时间的缓存
    private final ConcurrentHashMap<Long, Long> signInCache = new ConcurrentHashMap<>();

    /**
     * 签到获取积分接口
     *
     * @return Result对象
     */
    @Operation(summary = "签到获取积分")
    @PostMapping("/signIn")
    public Result<String> signIn() {
        long userId = StpUtil.getLoginIdAsLong();

        // 判断是否已签到（每天仅限一次）
        Long lastSignInTime = signInCache.get(userId);
        if (lastSignInTime != null && System.currentTimeMillis() - lastSignInTime < TimeUnit.DAYS.toMillis(1)) {
            return Result.error("今天已签到，请明天再来！");
        }

        // 更新签到记录
        signInCache.put(userId, System.currentTimeMillis());

        // 查询用户信息
        User user = userService.getById(userId);
        if (user == null) {
            throw new DrawException(ResultCodeEnum.USER_NOT_FOUND);
        }

        // 管理员角色不需要积分操作
        if (user.getPoints() == null) {
            return Result.error("管理员无需签到积分操作");
        }

        // 更新用户积分（签到奖励10积分）
        int randomPoints = ThreadLocalRandom.current().nextInt(1, 4);
        int newPoints = user.getPoints() + randomPoints;
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(User::getId, userId)
                .set(User::getPoints, newPoints);
        userService.update(updateWrapper);

        return Result.ok("签到成功，已获得10积分！");
    }

    /**
     * 查询当前用户积分接口
     *
     * @return Result对象，包含用户积分
     */
    @Operation(summary = "查询当前用户积分")
    @GetMapping("/getPoints")
    public Result<Integer> getPoints() {
        long userId = StpUtil.getLoginIdAsLong();

        // 查询用户信息
        User user = userService.getById(userId);
        if (user == null) {
            throw new DrawException(ResultCodeEnum.USER_NOT_FOUND);
        }

        // 管理员积分为无限，用 Integer.MAX_VALUE 表示
        if (user.getPoints() == null) {
            return Result.ok(Integer.MAX_VALUE); // 管理员积分无限
        }

        return Result.ok(user.getPoints());
    }
}
