package com.mashiro.service;

import com.comfyui.common.entity.ComfyWorkFlow;
import com.mashiro.dto.DrawDto;
import com.mashiro.enums.BaseFlowWork;
import com.mashiro.result.Result;
import org.springframework.web.multipart.MultipartFile;

public interface DrawService {


    Result<String> viewImg(String taskId);

    ComfyWorkFlow getFlow(String workFlowName);


    String text2img(DrawDto drawDto, BaseFlowWork baseFlowWork) throws InterruptedException;

    String img2img(DrawDto drawDto, MultipartFile uploadImage, BaseFlowWork baseFlowWork);
}
