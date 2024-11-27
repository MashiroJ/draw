package com.mashiro.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.mashiro.result.Result;
import com.mashiro.service.DrawRecordService;
import com.mashiro.vo.DrawRecordVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "绘图记录相关接口")
@Slf4j
@RestController
@RequestMapping("/system/draw/record")
public class DrawRecordController {

    @Resource
    private DrawRecordService drawRecordService;

    /**
     * 查询所有公开的绘图记录
     */
    @SaCheckPermission("gallery:view")
    @Operation(summary = "查询所有绘图记录")
    @GetMapping("/list")
    public Result<List<DrawRecordVO>> listAll() {
        List<DrawRecordVO> drawRecords = drawRecordService.listAllDrawRecords();
        return Result.ok(drawRecords);
    }

    /**
     * 按点赞数排序的绘图记录
     */
    @SaCheckPermission("gallery:view")
    @Operation(summary = "根据点赞数量排序绘图记录")
    @GetMapping("/list/sorted")
    public Result<List<DrawRecordVO>> listSortedByLikes() {
        List<DrawRecordVO> drawRecords = drawRecordService.listDrawRecordsSortedByLikes();
        return Result.ok(drawRecords);
    }

    /**
     * 按创建时间排序的绘图记录
     */
    @SaCheckPermission("gallery:view")
    @Operation(summary = "根据创建时间排序绘图记录")
    @GetMapping("/list/latest")
    public Result<List<DrawRecordVO>> listSortedByLatest() {
        List<DrawRecordVO> drawRecords = drawRecordService.listDrawRecordsSortedByLatest();
        return Result.ok(drawRecords);
    }

    /**
     * 更新绘图记录可见性
     */
    @SaCheckLogin
    @Operation(summary = "更新绘图记录可见性")
    @PutMapping("/visibility/{id}")
    public Result<Void> updateVisibility(@PathVariable Long id, @RequestParam Boolean isPublic) {
        drawRecordService.updateDrawRecordVisibility(id, isPublic);
        return Result.ok();
    }

    /**
     * 查询用户的绘图记录
     */
    @SaCheckLogin
    @Operation(summary = "根据用户ID查询所有绘图记录")
    @GetMapping("/list/user/{userId}")
    public Result<List<DrawRecordVO>> listByUserId(@PathVariable Integer userId) {
        List<DrawRecordVO> drawRecords = drawRecordService.listDrawRecordsByUserId(userId);
        return Result.ok(drawRecords);
    }

    /**
     * 删除绘图记录
     */
    @SaCheckPermission("gallery:delete")
    @Operation(summary = "删除绘图")
    @DeleteMapping("/{id}")
    public Result<Boolean> remove(@Parameter(description = "记录ID") @PathVariable Long id) {
        boolean success = drawRecordService.removeById(id);
        return Result.ok(success);
    }
}