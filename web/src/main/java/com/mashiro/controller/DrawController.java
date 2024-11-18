package com.mashiro.controller;

import com.comfyui.common.entity.ComfyWorkFlow;
import com.mashiro.dto.DrawDto;
import com.mashiro.enums.BaseFlowWork;
import com.mashiro.result.Result;
import com.mashiro.service.DrawService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "绘图相关接口")
@Slf4j
@RestController
@RequestMapping("/system/draw/")
public class DrawController {

    @Resource
    private DrawService drawService;

    @GetMapping("getFlow")
    @Operation(summary = "获取工作流")
    public Result getFlow(String workFlowName) {
        ComfyWorkFlow flow = drawService.getFlow(workFlowName);
        return Result.ok(flow);
    }

    @Operation(summary = "查看图片")
    @GetMapping("viewImg")
    public Result viewImg(String taskId) {
        Result<String> stringResult = drawService.viewImg(taskId);
        return Result.ok(stringResult);
    }

    /**
     * 提交任务
     *
     * @param drawDto 绘图参数
     * @return
     */
    @Operation(summary = "文生图")
    @PostMapping("text2img")
    public Result<String> text2img(DrawDto drawDto, @RequestParam BaseFlowWork baseFlowWork) {
        String text2imgUrl = drawService.text2img(drawDto, baseFlowWork);
        return Result.ok(text2imgUrl);
    }

    @Operation(summary = "图生图")
    @PostMapping("img2img")
    public Result<String> img2img(DrawDto drawDto, @RequestParam BaseFlowWork baseFlowWork, @RequestPart(required = false) MultipartFile uploadImage) {
        String img2imgUrl = drawService.img2img(drawDto,uploadImage, baseFlowWork);
        return Result.ok(img2imgUrl);
    }


}




