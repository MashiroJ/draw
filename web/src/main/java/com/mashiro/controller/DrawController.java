package com.mashiro.controller;

import com.comfyui.common.entity.ComfyWorkFlow;
import com.mashiro.dto.DrawDto;
import com.mashiro.result.Result;
import com.mashiro.service.DrawService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Tag(name = "绘图相关接口")
@Slf4j
@RestController
@RequestMapping("/system/draw/")
public class DrawController {

    @Resource
    private DrawService drawService;

    @GetMapping("getFlow")
    @Operation(summary = "获取默认工作流")
    public Result getFlow() {
        ComfyWorkFlow flow = drawService.getFlow();
        return Result.ok(flow);
    }

    /**
     * 提交任务
     *
     * @param drawDto 绘图参数
     * @return
     */
    @Operation(summary = "提交任务")
    @PostMapping("text2img")
    public Result text2img(@RequestBody DrawDto drawDto) {
        drawService.text2img(drawDto);
        return Result.ok();
    }

    @Operation(summary = "查看图片")
    @GetMapping("viewImg")
    public Result viewImg(String taskId) {
        Result<String> stringResult = drawService.viewImg(taskId);
        return Result.ok(stringResult);
    }
}




