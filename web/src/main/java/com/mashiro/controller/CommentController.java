package com.mashiro.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mashiro.dto.CommentDto;
import com.mashiro.entity.DrawComment;
import com.mashiro.result.Result;
import com.mashiro.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Tag(name = "评论相关接口")
@Slf4j
@RestController
@RequestMapping("/system/comment/")
public class CommentController {

    @Resource
    private CommentService commentService;

    @Operation(summary = "添加评论")
    @PostMapping("/add")
    public Result addComment(@RequestBody CommentDto commentDto) {
        commentService.addComment(commentDto);
        return Result.ok();
    }

    @Operation(summary = "删除评论")
    @DeleteMapping("/{commentId}")
    public Result deleteComment(@PathVariable Long commentId) {
        // 验证是否是评论作者或管理员
        commentService.deleteComment(commentId);
        return Result.ok();
    }

    @Operation(summary = "点赞评论")
    @PostMapping("/like/{commentId}")
    public Result likeComment(@PathVariable Long commentId) {
        commentService.likeComment(commentId);
        return Result.ok();
    }

    @Operation(summary = "取消点赞")
    @DeleteMapping("/unlike/{commentId}")
    public Result unlikeComment(@PathVariable Long commentId) {
        commentService.unlikeComment(commentId);
        return Result.ok();
    }

    @Operation(summary = "分页获取评论列表")
    @GetMapping("/page/{drawId}")
    public Result<Page<DrawComment>> getCommentPage(
            @PathVariable Long drawId,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<DrawComment> page = commentService.getCommentPage(drawId, pageNum, pageSize);
        return Result.ok(page);
    }
} 