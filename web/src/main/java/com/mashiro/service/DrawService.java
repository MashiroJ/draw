package com.mashiro.service;

import com.comfyui.common.entity.ComfyWorkFlow;
import com.mashiro.dto.DrawDto;
import com.mashiro.enums.BaseFlowWork;
import com.mashiro.result.Result;

public interface DrawService {


    Result<String> viewImg(String taskId);

    ComfyWorkFlow getFlow(String workFlowName);


    String text2img(DrawDto drawDto, BaseFlowWork baseFlowWork) throws InterruptedException;
}
