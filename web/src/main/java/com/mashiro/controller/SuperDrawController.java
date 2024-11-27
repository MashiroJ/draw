package com.mashiro.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import com.mashiro.dto.SuperDrawDto;
import com.mashiro.dto.SuperText2ImgRequest;
import com.mashiro.enums.ComfyUi.Checkpoint;
import com.mashiro.enums.ComfyUi.ImageSize;
import com.mashiro.enums.ComfyUi.Sampler;
import com.mashiro.enums.ComfyUi.Scheduler;
import com.mashiro.result.Result;
import com.mashiro.service.PointsService;
import com.mashiro.service.SuperDrawService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.mashiro.constant.DrawPointConstant.SUPER_IMG2IMG_POINT;
import static com.mashiro.constant.DrawPointConstant.SUPER_TEXT2IMG_POINT;

@Tag(name = "超级绘画", description = "AI绘画")
@RestController
@RequestMapping("/system/super/draw/")
public class SuperDrawController {

    @Resource
    SuperDrawService service;
    @Resource
    private PointsService pointsService;

    /**
     * 超级文生图
     */
    @SaCheckPermission("text2img:advanced")
    @Operation(summary = "超级文生图")
    @PostMapping("text2img")
    public Result<String> superText2img(@RequestBody SuperText2ImgRequest request) throws InterruptedException {
        // 将整数代码转换为对应的枚举实例
        ImageSize imageSize = ImageSize.fromCode(request.getImageSize());
        Checkpoint checkpoint = Checkpoint.fromCode(request.getCheckpoint());
        Sampler sampler = Sampler.fromCode(request.getSampler());
        Scheduler scheduler = Scheduler.fromCode(request.getScheduler());
        String result = service.superText2img(request.getDrawDto(), imageSize, checkpoint, sampler, scheduler);
        // 文生图图片创作成功，扣除积分
        long loginUserId = StpUtil.getLoginIdAsLong();
        pointsService.deductPoints(loginUserId, SUPER_TEXT2IMG_POINT);
        return Result.ok(result);
    }

    /**
     * 超级图生图
     */
    @SaCheckPermission("img2img:advanced")
    @Operation(summary = "超级图生图")
    @PostMapping("img2img")
    public Result<String> superImg2img(
            @ModelAttribute SuperDrawDto drawDto,
            @RequestParam("imageSize") Integer imageSizeCode,
            @RequestParam("checkpoint") Integer checkpointCode,
            @RequestParam("sampler") Integer samplerCode,
            @RequestParam("scheduler") Integer schedulerCode,
            @RequestPart(value = "uploadImage", required = false) MultipartFile uploadImage) throws InterruptedException {

        // 将整数代码转换为对应的枚举实例
        ImageSize imageSize = ImageSize.fromCode(imageSizeCode);
        Checkpoint checkpoint = Checkpoint.fromCode(checkpointCode);
        Sampler sampler = Sampler.fromCode(samplerCode);
        Scheduler scheduler = Scheduler.fromCode(schedulerCode);

        String result = service.superImg2img(drawDto, imageSize, checkpoint, sampler, scheduler, uploadImage);
        // 文生图图片创作成功，扣除积分
        long loginUserId = StpUtil.getLoginIdAsLong();
        pointsService.deductPoints(loginUserId, SUPER_IMG2IMG_POINT);
        return Result.ok(result);
    }
}
