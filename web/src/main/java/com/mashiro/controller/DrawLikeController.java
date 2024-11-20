package com.mashiro.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.mashiro.entity.DrawLike;
import com.mashiro.result.Result;
import com.mashiro.service.DrawLikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "绘画点赞管理")
@Slf4j
@RestController
@RequestMapping("/system/draw/like")
public class DrawLikeController {

    @Resource
    private DrawLikeService drawLikeService;

    /**
     * 切换点赞状态(点赞/取消点赞)
     *
     * @param drawId 绘画ID
     * @return 点赞记录
     */
    @Operation(summary = "切换点赞状态")
    @PostMapping("/{drawId}")
    public Result<DrawLike> toggleLike(@PathVariable Long drawId) {
        int userId = StpUtil.getLoginIdAsInt();
        // 记录请求参数日志
        log.info("切换点赞状态: drawId={}, userId={}", drawId, userId);

        // 切换点赞状态
        DrawLike like = drawLikeService.toggleLike(drawId, userId);

        // 返回点赞记录
        return Result.ok(like);
    }

    /**
     * 获取某个绘画的点赞列表
     *
     * @param drawId 绘画ID
     * @return 点赞列表
     */
    @Operation(summary = "获取某个绘画的点赞列表")
    @GetMapping("/draw/{drawId}")
    public Result<List<DrawLike>> getLikesByDrawId(@PathVariable Long drawId) {
        // 记录请求参数日志
        log.info("获取某个绘画的点赞列表: drawId={}", drawId);

        // 获取点赞列表
        List<DrawLike> likes = drawLikeService.getLikesByDrawId(drawId);

        // 返回点赞列表
        return Result.ok(likes);
    }

    /**
     * 获取某个用户的点赞列表
     *
     * @param userId 用户ID
     * @return 点赞列表
     */
    @Operation(summary = "获取某个用户的点赞列表")
    @GetMapping("/user/{userId}")
    public Result<List<DrawLike>> getLikesByUserId(@PathVariable Integer userId) {
        // 记录请求参数日志
        log.info("获取某个用户的点赞列表: userId={}", userId);

        // 获取点赞列表
        List<DrawLike> likes = drawLikeService.getLikesByUserId(userId);

        // 返回点赞列表
        return Result.ok(likes);
    }
}