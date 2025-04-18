package com.mashiro.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import com.comfyui.common.entity.ComfyWorkFlow;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashiro.dto.DrawDto;
import com.mashiro.enums.ComfyUi.Checkpoint;
import com.mashiro.enums.ComfyUi.ImageSize;
import com.mashiro.result.Result;
import com.mashiro.service.DrawService;
import com.mashiro.service.PointsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.mashiro.constant.DrawPointConstant.DRAW_POINT;

@Tag(name = "绘图相关接口")
@Slf4j
@RestController
@RequestMapping("/system/draw/")
public class DrawController {

    @Resource
    private DrawService drawService;
    @Resource
    private PointsService pointsService;

    @SaCheckLogin
    @GetMapping("getFlow")
    @Operation(summary = "获取工作流")
    public Result getFlow(String workFlowName) {
        ComfyWorkFlow flow = drawService.getFlow(workFlowName);
        return Result.ok(flow);
    }

    @SaCheckPermission("gallery:view")
    @Operation(summary = "查看图片")
    @GetMapping("viewImg")
    public Result viewImg(String taskId) {
        Result<String> stringResult = drawService.viewImg(taskId);
        return Result.ok(stringResult);
    }

    /**
     * 文生图提交任务
     */
    @SaCheckPermission("text2img:basic")
    @Operation(summary = "文生图")
    @PostMapping("text2img")
    public Result<String> text2img(@RequestBody DrawDto drawDto,
                                   @RequestParam("checkpoint") Integer checkpointCode,
                                   @RequestParam("imageSize") Integer imageSizeCode) {
        Checkpoint checkpoint = Checkpoint.fromCode(checkpointCode);
        ImageSize imageSize = ImageSize.fromCode(imageSizeCode);
        long loginUserId = StpUtil.getLoginIdAsLong();
        String text2imgUrl = drawService.text2img(drawDto,checkpoint,imageSize);
        // 文生图图片创作成功，扣除积分
        pointsService.deductPoints(loginUserId, DRAW_POINT);
        return Result.ok(text2imgUrl);
    }

    /**
     * 图生图提交任务
     */
    @SaCheckPermission("img2img:basic")
    @Operation(summary = "图生图")
    @PostMapping("img2img")
    public Result<String> img2img(
            @RequestPart("drawDto") String drawDtoJson,
            @RequestParam("checkpoint") Integer checkpointCode,
            @RequestPart(value = "uploadImage", required = false) MultipartFile uploadImage
    ) {
        Checkpoint checkpoint = Checkpoint.fromCode(checkpointCode);
        long loginUserId = StpUtil.getLoginIdAsLong();
        try {
            ObjectMapper mapper = new ObjectMapper();
            DrawDto drawDto = mapper.readValue(drawDtoJson, DrawDto.class);
            String img2imgUrl = drawService.img2img(drawDto, uploadImage,checkpoint);
            // 图生图图片创作成功，扣除积分
            pointsService.deductPoints(loginUserId, DRAW_POINT);
            return Result.ok(img2imgUrl);
        } catch (JsonProcessingException e) {
            return Result.error("参数解析失败：" + e.getMessage());
        }
    }
}




