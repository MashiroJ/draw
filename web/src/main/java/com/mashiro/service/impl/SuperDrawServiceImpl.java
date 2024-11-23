package com.mashiro.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.comfyui.common.entity.ComfyWorkFlow;
import com.comfyui.common.entity.ComfyWorkFlowNode;
import com.mashiro.config.comfyUi.WorkflowNodeConfig;
import com.mashiro.dto.SuperDrawDto;
import com.mashiro.enums.BaseFlowWork;
import com.mashiro.enums.ComfyUi.Checkpoint;
import com.mashiro.enums.ComfyUi.ImageSize;
import com.mashiro.enums.ComfyUi.Sampler;
import com.mashiro.enums.ComfyUi.Scheduler;
import com.mashiro.exception.DrawException;
import com.mashiro.result.ResultCodeEnum;
import com.mashiro.service.SuperDrawService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Random;

@Slf4j
@Service
public class SuperDrawServiceImpl extends BaseDrawService implements SuperDrawService {

    /**
     * 超级文生图
     *
     * @param superDrawDto
     * @param imageSize
     * @param checkpoint
     * @param sampler
     * @param scheduler
     * @return
     * @throws InterruptedException
     */
    @Override
    public String superText2img(SuperDrawDto superDrawDto, ImageSize imageSize,
                                Checkpoint checkpoint, Sampler sampler,
                                Scheduler scheduler) throws InterruptedException {
        int userId = StpUtil.getLoginIdAsInt();
        String taskId = generateTaskId();

        ComfyWorkFlow flow = getFlow(BaseFlowWork.SUPERTEXT2IMG.toString());
        if (flow == null) {
            throw new DrawException(ResultCodeEnum.SERVICE_ERROR);
        }
        text2ImgConfigureWorkflow(flow, superDrawDto, imageSize, checkpoint, sampler, scheduler);
        submitDrawingTask(flow, taskId);
        String superTextImgUrl = processTaskResult(taskId);
        saveDrawRecord(userId, taskId, superDrawDto, superTextImgUrl, BaseFlowWork.SUPERTEXT2IMG);
        return superTextImgUrl;
    }

    @Override
    public String superImg2img(SuperDrawDto superDrawDto, ImageSize imageSize,
                               Checkpoint checkpoint, Sampler sampler,
                               Scheduler scheduler, MultipartFile uploadImage) throws InterruptedException {
        int userId = StpUtil.getLoginIdAsInt();
        String taskId = generateTaskId();
        String uploadedImagePath = handleUploadedImage(uploadImage, taskId);

        ComfyWorkFlow flow = getFlow(BaseFlowWork.SUPERIMG2IMG.toString());
        if (flow == null) {
            throw new DrawException(ResultCodeEnum.SERVICE_ERROR);
        }

        img2ImgConfigureWorkflow(flow, superDrawDto, checkpoint, sampler, scheduler, uploadedImagePath);
        submitDrawingTask(flow, taskId);
        String superTextImgUrl = processTaskResult(taskId);
        saveDrawRecord(userId, taskId, superDrawDto, superTextImgUrl, BaseFlowWork.SUPERIMG2IMG);
        return superTextImgUrl;
    }


    /**
     * 配置文生图工作流
     *
     * @param flow
     * @param superDrawDto
     * @param imageSize
     * @param checkpoint
     * @param sampler
     * @param scheduler
     */
    private void text2ImgConfigureWorkflow(ComfyWorkFlow flow, SuperDrawDto superDrawDto,
                                           ImageSize imageSize, Checkpoint checkpoint,
                                           Sampler sampler, Scheduler scheduler) {

        WorkflowNodeConfig nodeConfig = WorkflowNodeConfig.superText2ImgConfig();
        // 配置采样器
        ComfyWorkFlowNode kSampler = flow.getNode(nodeConfig.getKSamplerId());
        configureKSampler(kSampler, superDrawDto, sampler, scheduler);

        // 配置模型
        ComfyWorkFlowNode checkPoint = flow.getNode(nodeConfig.getCheckPointId());
        checkPoint.getInputs().put("ckpt_name", checkpoint.getName());

        // 配置图片尺寸
        ComfyWorkFlowNode imageNode = flow.getNode(nodeConfig.getImageNodeId());
        imageNode.getInputs().put("width", imageSize.getWidth());
        imageNode.getInputs().put("height", imageSize.getHeight());

        // 配置提示词
        ComfyWorkFlowNode positive = flow.getNode(nodeConfig.getPositiveId());
        positive.getInputs().put("text", QUALITY_PREFIX + superDrawDto.getPrompt());

        // 配置反向提示词
        ComfyWorkFlowNode negative = flow.getNode(nodeConfig.getNegativeId());
        negative.getInputs().put("text", superDrawDto.getNegativePrompt());

        log.info("更新后的文生图工作流{}", flow);
    }

    /**
     * 配置图生图工作流
     * @param flow
     * @param superDrawDto
     * @param checkpoint
     * @param sampler
     * @param scheduler
     * @param uploadedImagePath
     */

    private void img2ImgConfigureWorkflow(ComfyWorkFlow flow, SuperDrawDto superDrawDto,
                                          Checkpoint checkpoint, Sampler sampler,
                                          Scheduler scheduler, String uploadedImagePath) {

        WorkflowNodeConfig nodeConfig = WorkflowNodeConfig.superImg2ImgConfig();
        // 配置采样器
        ComfyWorkFlowNode kSampler = flow.getNode(nodeConfig.getKSamplerId());
        configureKSampler(kSampler, superDrawDto, sampler, scheduler);

        // 配置模型
        ComfyWorkFlowNode checkPoint = flow.getNode(nodeConfig.getCheckPointId());
        checkPoint.getInputs().put("ckpt_name", checkpoint.getName());

        ComfyWorkFlowNode inputImage = flow.getNode(nodeConfig.getInputImageId());
        inputImage.getInputs().put("image", uploadedImagePath);


        // 配置提示词
        ComfyWorkFlowNode positive = flow.getNode(nodeConfig.getPositiveId());
        positive.getInputs().put("text", QUALITY_PREFIX + superDrawDto.getPrompt());

        // 配置反向提示词
        ComfyWorkFlowNode negative = flow.getNode(nodeConfig.getNegativeId());
        negative.getInputs().put("text", superDrawDto.getNegativePrompt());

        log.info("更新后的图生图工作流{}", flow);
    }

    /**
     * 配置采样器
     *
     * @param kSampler
     * @param superDrawDto
     * @param sampler
     * @param scheduler
     */
    private void configureKSampler(ComfyWorkFlowNode kSampler, SuperDrawDto superDrawDto,
                                   Sampler sampler, Scheduler scheduler) {
        kSampler.getInputs().put("seed", Math.abs(new Random().nextInt()));
        kSampler.getInputs().put("steps", superDrawDto.getSteps());
        kSampler.getInputs().put("cfg", superDrawDto.getCfg());
        kSampler.getInputs().put("sampler_name", sampler.getName());
        kSampler.getInputs().put("scheduler", scheduler.getName());
        kSampler.getInputs().put("denoise", superDrawDto.getDenoise());
    }
}