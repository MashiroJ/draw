package com.mashiro.controller;

import com.mashiro.result.Result;
import com.mashiro.service.DRService;
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
@RequestMapping("/system/dr")
public class DRController {

    @Resource
    private DRService dRService;

    @Operation(summary = "查询所有绘图记录")
    @GetMapping("/list")
    public Result<List<DrawRecordVO>> listAll() {
        // 调用服务层方法获取所有绘图记录
        List<DrawRecordVO> drawRecords = dRService.listAllDrawRecords();
        // 返回查询结果
        return Result.ok(drawRecords);
    }

    @Operation(summary = "根据点赞数量排序绘图记录")
    @GetMapping("/list/sorted")
    public Result<List<DrawRecordVO>> listSortedByLikes() {
        // 调用服务层方法获取根据点赞数量排序的绘图记录
        List<DrawRecordVO> drawRecords = dRService.listDrawRecordsSortedByLikes();
        // 返回查询结果
        return Result.ok(drawRecords);
    }

    @Operation(summary = "根据创建时间排序绘图记录")
    @GetMapping("/list/latest")
    public Result<List<DrawRecordVO>> listSortedByLatest() {
        // 调用服务层方法获取根据创建时间排序的绘图记录
        List<DrawRecordVO> drawRecords = dRService.listDrawRecordsSortedByLatest();
        // 返回查询结果
        return Result.ok(drawRecords);
    }

    @Operation(summary = "更新绘图记录可见性")
    @PutMapping("/visibility/{id}")
    public Result<Void> updateVisibility(@PathVariable Long id, @RequestParam Boolean isPublic) {
        // 调用服务层方法更新可见性
        dRService.updateDrawRecordVisibility(id, isPublic);
        return Result.ok();
    }

    @Operation(summary = "根据用户ID查询所有绘图记录")
    @GetMapping("/list/user/{userId}")
    public Result<List<DrawRecordVO>> listByUserId(@PathVariable Integer userId) {
        // 调用服务层方法获取该用户的所有绘图记录
        List<DrawRecordVO> drawRecords = dRService.listDrawRecordsByUserId(userId);
        // 返回查询结果
        return Result.ok(drawRecords);
    }

    /**
     * 删除绘画(逻辑删除)
     *
     * @param id
     * @return
     */
    @Operation(summary = "删除绘图")
    @DeleteMapping("/{id}")
    public Result<Boolean> remove(@Parameter(description = "记录ID") @PathVariable Long id) {
        boolean success = dRService.removeById(id);
        return Result.ok(success);
    }
}