package com.mashiro.service;

import com.comfyui.common.entity.ComfyWorkFlow;
import com.mashiro.dto.DrawDto;
import com.mashiro.result.Result;

public interface DrawService {

    void text2img(DrawDto drawDto);

    Result<String> viewImg(String taskId);

    ComfyWorkFlow getFlow();
}
