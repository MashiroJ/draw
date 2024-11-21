package com.mashiro.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.mashiro.result.Result;
import com.mashiro.service.DrawLikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Tag(name = "绘画点赞管理")
@Slf4j
@RestController
@RequestMapping("/system/draw/like")
public class DrawLikeController {

    @Resource
    private DrawLikeService drawLikeService;

    /**
     * 用户点赞
     */
    @Operation(summary = "点赞")
    @PostMapping("/like")
    public Result<?> like(@RequestParam("draw_id") Long drawId) {
        Integer userId = StpUtil.getLoginIdAsInt();  // 使用 sa-token 获取当前用户 ID

        // 判断用户是否已经点赞
        boolean alreadyLiked = drawLikeService.checkIfLiked(drawId, userId);
        if (alreadyLiked) {
            return Result.error("您已点赞过该绘画");
        }

        // 执行点赞操作
        boolean success = drawLikeService.addLike(drawId, userId);
        if (success) {
            return Result.ok("点赞成功");
        } else {
            return Result.error("点赞失败");
        }
    }

    /**
     * 取消点赞
     */
    @Operation(summary = "取消点赞")
    @DeleteMapping("/unlike")
    public Result<?> unlike(@RequestParam("draw_id") Long drawId) {
        Integer userId = StpUtil.getLoginIdAsInt();  // 使用 sa-token 获取当前用户 ID

        // 判断用户是否已点赞
        boolean alreadyLiked = drawLikeService.checkIfLiked(drawId, userId);
        if (!alreadyLiked) {
            return Result.error("您尚未点赞过该绘画");
        }

        // 执行取消点赞操作
        boolean success = drawLikeService.removeLike(drawId, userId);
        if (success) {
            return Result.ok("取消点赞成功");
        } else {
            return Result.error("取消点赞失败");
        }
    }

    /**
     * 获取绘画的点赞数
     */
    @Operation(summary = "获取点赞数")
    @GetMapping("/count")
    public Result<?> getLikeCount(@RequestParam("draw_id") Long drawId) {
        long likeCount = drawLikeService.getLikeCount(drawId);
        return Result.ok(likeCount);
    }

    /**
     * 获取当前用户对绘画的点赞状态
     */
    @GetMapping("/status")
    @Operation(summary = "获取点赞状态")
    public Result<?> getLikeStatus(@RequestParam("draw_id") Long drawId) {
        Integer userId = StpUtil.getLoginIdAsInt();  // 使用 sa-token 获取当前用户 ID
        boolean liked = drawLikeService.checkIfLiked(drawId, userId);
        return Result.ok(liked);
    }
}
