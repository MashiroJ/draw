package com.mashiro.service;

import com.mashiro.dto.SuperDrawDto;
import com.mashiro.enums.ComfyUi.Checkpoint;
import com.mashiro.enums.ComfyUi.ImageSize;
import com.mashiro.enums.ComfyUi.Sampler;
import com.mashiro.enums.ComfyUi.Scheduler;
import org.springframework.web.multipart.MultipartFile;

public interface SuperDrawService {

    String superText2img(SuperDrawDto drawDto, ImageSize imageSize, Checkpoint checkpoint, Sampler sampler, Scheduler scheduler) throws InterruptedException;

    String superImg2img(SuperDrawDto drawDto, ImageSize imageSize, Checkpoint checkpoint, Sampler sampler, Scheduler scheduler, MultipartFile uploadImage) throws InterruptedException;
}
