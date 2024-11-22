package com.mashiro.controller;

import com.comfyui.common.entity.ComfyWorkFlow;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashiro.dto.DrawDto;
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
     * 文生图提交任务
     *
     * @param drawDto 绘图参数
     * @return
     */
    @Operation(summary = "文生图")
    @PostMapping("text2img")
    public Result<String> text2img(@RequestBody DrawDto drawDto) {
        String text2imgUrl = drawService.text2img(drawDto);
        return Result.ok(text2imgUrl);
    }

    /**
     * 图生图提交任务
     *
     * @param drawDto
     * @param uploadImage
     * @return
     */
    @Operation(summary = "图生图")
    @PostMapping("img2img")
    public Result<String> img2img(
            @RequestPart("drawDto") String drawDtoJson,
            @RequestPart(value = "uploadImage", required = false) MultipartFile uploadImage
    ) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            DrawDto drawDto = mapper.readValue(drawDtoJson, DrawDto.class);
            String img2imgUrl = drawService.img2img(drawDto, uploadImage);
            return Result.ok(img2imgUrl);
        } catch (JsonProcessingException e) {
            return Result.error("参数解析失败：" + e.getMessage());
        }
    }
}




