package com.mashiro.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.mashiro.exception.DrawException;
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
     * 点赞/取消点赞
     * @param drawId
     * @return
     */
    @Operation(summary = "点赞/取消点赞")
    @PostMapping("/{drawId}/like-toggle")
    public Result<?> toggleLike(@PathVariable("drawId") Long drawId) {
        Integer userId = StpUtil.getLoginIdAsInt();
        try {
            boolean liked = drawLikeService.toggleLike(drawId, userId);
            if (liked) {
                return Result.ok("点赞成功");
            } else {
                return Result.ok("取消点赞成功");
            }
        } catch (DrawException e) {
            log.warn("绘画不存在，drawId={}", drawId);
            return Result.error("绘画不存在");
        } catch (Exception e) {
            log.error("操作失败，drawId={}, userId={}, error={}", drawId, userId, e.getMessage(), e);
            return Result.error("操作失败");
        }
    }


    /**
     * 获取点赞数
     * @param drawId
     * @return
     */
    @Operation(summary = "获取点赞数")
    @GetMapping("/count")
    public Result<?> getLikeCount(@RequestParam("drawId") Long drawId) {
        long likeCount = drawLikeService.getLikeCount(drawId);
        return Result.ok(likeCount);
    }

}
