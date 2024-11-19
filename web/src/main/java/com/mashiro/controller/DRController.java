package com.mashiro.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mashiro.entity.DrawRecord;
import com.mashiro.result.Result;
import com.mashiro.service.DRService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "绘图记录相关接口")
@Slf4j
@RestController
@RequestMapping("/system/dr")
public class DRController {

    @Resource
    private DRService dRService;

    /**
     * 分页查询绘图记录
     *
     * @param current
     * @param size
     * @param drawRecord
     * @return
     */
    @Operation(summary = "分页查询绘图记录")
    @GetMapping("/page")
    public Result<IPage<DrawRecord>> pageInfo(
            @Parameter(description = "当前页码") @RequestParam(defaultValue = "1") long current,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") long size,
            @Parameter(description = "查询条件") DrawRecord drawRecord) {
        Page<DrawRecord> page = new Page<>(current, size);
        LambdaQueryWrapper<DrawRecord> queryWrapper = buildQueryWrapper(drawRecord);
        Page<DrawRecord> result = dRService.page(page, queryWrapper);
        return Result.ok(result);
    }

    /**
     * 获取用户所有图片URL
     *
     * @param userId
     * @return
     */
    @Operation(summary = "根据用户Id获取用户所有图片URL")
    @GetMapping("/images/{userId}")
    public Result<List<String>> getUserImages(
            @Parameter(description = "用户ID") @PathVariable Integer userId) {

        // 构建查询条件
        LambdaQueryWrapper<DrawRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DrawRecord::getUserId, userId)
                .select(DrawRecord::getImageUrl);  // 只查询imageUrl字段


        // 按创建时间降序排序
        queryWrapper.orderByDesc(DrawRecord::getCreateTime);

        // 执行查询并提取URL列表
        List<String> imageUrls = dRService.list(queryWrapper)
                .stream()
                .map(DrawRecord::getImageUrl)
                .collect(Collectors.toList());

        return Result.ok(imageUrls);
    }


    /**
     * 删除绘画(逻辑删除)
     * @param id
     * @return
     */
    @Operation(summary = "删除绘图")
    @DeleteMapping("/{id}")
    public Result<Boolean> remove(@Parameter(description = "记录ID") @PathVariable Long id) {
        boolean success = dRService.removeById(id);
        return Result.ok(success);
    }

    /**
     * 构建查询条件
     *
     * @param drawRecord
     * @return
     */
    private LambdaQueryWrapper<DrawRecord> buildQueryWrapper(DrawRecord drawRecord) {
        LambdaQueryWrapper<DrawRecord> queryWrapper = new LambdaQueryWrapper<>();

        if (drawRecord != null) {
            // 按用户ID查询
            if (drawRecord.getUserId() != null) {
                queryWrapper.eq(DrawRecord::getUserId, drawRecord.getUserId());
            }

            // 按任务ID查询
            if (drawRecord.getTaskId() != null) {
                queryWrapper.eq(DrawRecord::getTaskId, drawRecord.getTaskId());
            }

            // 按生成类型查询
            if (drawRecord.getGenerationType() != null) {
                queryWrapper.eq(DrawRecord::getGenerationType, drawRecord.getGenerationType());
            }

            // 按工作流名称查询
            if (drawRecord.getWorkFlowName() != null) {
                queryWrapper.eq(DrawRecord::getWorkFlowName, drawRecord.getWorkFlowName());
            }

            // 按是否公开查询
            if (drawRecord.getIsPublic() != null) {
                queryWrapper.eq(DrawRecord::getIsPublic, drawRecord.getIsPublic());
            }

            // 提示词模糊查询
            if (drawRecord.getPrompt() != null) {
                queryWrapper.like(DrawRecord::getPrompt, drawRecord.getPrompt());
            }
        }

        // 默认按创建时间降序排序
        queryWrapper.orderByDesc(DrawRecord::getCreateTime);
        return queryWrapper;
    }
}