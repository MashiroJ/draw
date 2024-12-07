package com.mashiro.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.mashiro.entity.User;
import com.mashiro.enums.BaseRole;
import com.mashiro.exception.DrawException;
import com.mashiro.result.Result;
import com.mashiro.result.ResultCodeEnum;
import com.mashiro.service.PointsService;
import com.mashiro.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class PointsServiceImpl implements PointsService {

    @Resource
    private UserService userService;

    private final ConcurrentHashMap<Long, Long> signInCache = new ConcurrentHashMap<>();

    @Override
    public Result<String> signIn(long userId) {
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

        // 更新用户积分（签到奖励随机积分）
        int randomPoints = ThreadLocalRandom.current().nextInt(1, 4);
        int newPoints = user.getPoints() + randomPoints;
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(User::getId, userId)
                .set(User::getPoints, newPoints);
        userService.update(updateWrapper);

        return Result.ok("签到成功，已获得" + randomPoints + "积分！");
    }

    @Override
    public Result<Integer> getPoints(long userId) {
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

    @Override
    public Result<String> deductPoints(long userId, int points) {
        // 查询用户信息
        User user = userService.getById(userId);
        if (user == null) {
            throw new DrawException(ResultCodeEnum.USER_NOT_FOUND);
        }

        List<Long> roleIdsByUserId = userService.getRoleIdsByUserId(userId);
        log.info("用户角色列表：{}", roleIdsByUserId);
        //通过用户ID获取用户相关联的角色ID：[3]
        // 管理员无需扣除积分
        if (roleIdsByUserId.contains((long)BaseRole.ADMIN.getCode())) {
            return Result.error("管理员无需扣除积分");
        }

        // 会员用户享受 0.5 折扣，即扣除积分减半
        if (roleIdsByUserId.contains((long)BaseRole.MUSER.getCode())) {
            if (points <= 0) {
                return Result.error("扣除积分必须为正数");
            }
            int actualDeductPoints = points / 2;
            if (actualDeductPoints <= 0) {
                return Result.error("扣除积分过低，无法扣除");
            }
            int currentPoints = user.getPoints();
            if (currentPoints < actualDeductPoints) {
                return Result.error("积分不足，无法扣除！");
            }

            // 更新用户积分
            int updatedPoints = currentPoints - actualDeductPoints;
            LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(User::getId, userId)
                    .set(User::getPoints, updatedPoints);
            userService.update(updateWrapper);

            return Result.ok("扣除成功，实际扣除 " + actualDeductPoints + " 积分，剩余积分：" + updatedPoints);
        }

        // 普通用户正常扣除积分
        if (points <= 0) {
            return Result.error("扣除积分必须为正数");
        }
        int currentPoints = user.getPoints();
        if (currentPoints < points) {
            return Result.error("积分不足，无法扣除！");
        }

        // 更新用户积分
        int updatedPoints = currentPoints - points;
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(User::getId, userId)
                .set(User::getPoints, updatedPoints);
        userService.update(updateWrapper);

        return Result.ok("扣除成功，剩余积分：" + updatedPoints);
    }
}