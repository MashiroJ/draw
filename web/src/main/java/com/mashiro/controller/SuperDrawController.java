package com.mashiro.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("img2img")
    public Result<String> superImg2img(
//            @RequestPart("drawDto") String drawDtoJson,
            SuperDrawDto drawDto,
            ImageSize imageSize,
            Checkpoint checkpoint,
            Sampler sampler,
            Scheduler scheduler,
            @RequestPart(value = "uploadImage", required = false) MultipartFile uploadImage) throws InterruptedException, JsonProcessingException {
//        ObjectMapper mapper = new ObjectMapper();
//        SuperDrawDto drawDto = mapper.readValue(drawDtoJson, SuperDrawDto.class);
        String superText2img = service.superImg2img(drawDto, imageSize, checkpoint, sampler, scheduler, uploadImage);
        return Result.ok(superText2img);
    }
}
