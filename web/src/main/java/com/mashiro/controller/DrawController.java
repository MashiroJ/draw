package com.mashiro.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.comfyui.common.entity.ComfyWorkFlow;
import com.comfyui.common.entity.ComfyWorkFlowNode;
import com.comfyui.queue.common.DrawingTaskInfo;
import com.comfyui.queue.common.IDrawingTaskSubmit;
import com.mashiro.dto.DrawDto;
import com.mashiro.result.Result;
import com.mashiro.service.DrawService;
import com.mashiro.utils.ComfyUi.ComfyUiProperties;
import com.mashiro.utils.TaskProcessMonitor.TaskProcessMonitor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Tag(name = "绘图相关接口")
@Slf4j
@RestController
@RequestMapping("/system/draw/")
public class DrawController {

    @Resource
    private DrawService drawService;

    @GetMapping("getFlow")
    @Operation(summary = "获取默认工作流")
    public Result getFlow(){
        ComfyWorkFlow flow = drawService.getFlow();
        return Result.ok(flow);
    }

    /**
     * 提交任务
     *
     * @param  drawDto  绘图参数
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
    public Result viewImg(String taskId){
        Result<String> stringResult = drawService.viewImg(taskId);
        return Result.ok(stringResult);
    }
}




