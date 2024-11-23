package com.mashiro.controller;

import com.mashiro.dto.SuperDrawDto;
import com.mashiro.dto.SuperText2ImgRequest;
import com.mashiro.enums.ComfyUi.Checkpoint;
import com.mashiro.enums.ComfyUi.ImageSize;
import com.mashiro.enums.ComfyUi.Sampler;
import com.mashiro.enums.ComfyUi.Scheduler;
import com.mashiro.result.Result;
import com.mashiro.service.SuperDrawService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "超级绘画", description = "AI绘画")
@RestController
@RequestMapping("/system/super/draw/")
public class SuperDrawController {

    @Resource
    SuperDrawService service;

    @Operation(summary = "超级文生图")
    @PostMapping("text2img")
    public Result<String> superText2img(@RequestBody SuperText2ImgRequest request) throws InterruptedException {
        // 将整数代码转换为对应的枚举实例
        ImageSize imageSize = ImageSize.fromCode(request.getImageSize());
        Checkpoint checkpoint = Checkpoint.fromCode(request.getCheckpoint());
        Sampler sampler = Sampler.fromCode(request.getSampler());
        Scheduler scheduler = Scheduler.fromCode(request.getScheduler());
        String result = service.superText2img(request.getDrawDto(), imageSize, checkpoint, sampler, scheduler);
        return Result.ok(result);
    }


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
        return Result.ok(result);
    }
}
