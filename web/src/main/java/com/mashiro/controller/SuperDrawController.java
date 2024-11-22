package com.mashiro.controller;

import com.mashiro.dto.SuperDrawDto;
import com.mashiro.enums.ComfyUi.Checkpoint;
import com.mashiro.enums.ComfyUi.ImageSize;
import com.mashiro.enums.ComfyUi.Sampler;
import com.mashiro.enums.ComfyUi.Scheduler;
import com.mashiro.result.Result;
import com.mashiro.service.SuperDrawService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "SuperDraw", description = "AI绘画")
@RestController
@RequestMapping("/system/super/draw/")
public class SuperDrawController {

    @Resource
    SuperDrawService service;

    @PostMapping("text2img")
    public Result<String> superText2img(SuperDrawDto drawDto, ImageSize imageSize, Checkpoint checkpoint, Sampler sampler, Scheduler scheduler) throws InterruptedException {
        String superText2img = service.superText2img(drawDto, imageSize, checkpoint, sampler, scheduler);
        return Result.ok(superText2img);
    }
}
