package com.mashiro.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.comfyui.common.entity.ComfyWorkFlow;
import com.comfyui.common.entity.ComfyWorkFlowNode;
import com.comfyui.queue.common.DrawingTaskInfo;
import com.comfyui.queue.common.IDrawingTaskSubmit;
import com.mashiro.dto.SuperDrawDto;
import com.mashiro.enums.BaseFlowWork;
import com.mashiro.enums.ComfyUi.Checkpoint;
import com.mashiro.enums.ComfyUi.ImageSize;
import com.mashiro.enums.ComfyUi.Sampler;
import com.mashiro.enums.ComfyUi.Scheduler;
import com.mashiro.exception.DrawException;
import com.mashiro.result.ResultCodeEnum;
import com.mashiro.service.FileService;
import com.mashiro.service.SuperDrawService;
import com.mashiro.utils.ComfyUi.ComfyUiProperties;
import com.mashiro.utils.TaskProcessMonitor.TaskProcessMonitor;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.mashiro.constant.UserConstant.DEFAULT_TASK_ID;

@Slf4j
@Service
public class SuperDrawServiceImpl implements SuperDrawService {

    private static final int TASK_WAIT_TIME = 20000; // 20秒
    private static final int TASK_TIMEOUT = 5; // 5分钟
    final String QUALITY_PREFIX = "masterpiece, best quality,";


    @Resource
    private IDrawingTaskSubmit taskSubmit;
    @Resource
    private ResourceLoader resourceLoader;
    @Resource
    private TaskProcessMonitor taskProcessMonitor;
    @Resource
    private ComfyUiProperties comfyUiProperties;
    @Resource
    private FileService fileService;

    public ComfyWorkFlow getFlow(String workFlowName) {
        String flowName = workFlowName + ".json";
        org.springframework.core.io.Resource resource = resourceLoader.getResource("classpath:" + flowName);
        StringBuilder flowStr = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                flowStr.append(line);
            }
        } catch (IOException e) {
            log.error("读取工作流文件失败: {}", flowName, e);
            return null;
        }

        return ComfyWorkFlow.of(flowStr.toString());
    }

    @Override
    public String superText2img(SuperDrawDto superDrawDto, ImageSize imageSize, Checkpoint checkpoint, Sampler sampler, Scheduler scheduler) throws InterruptedException {
        // 生成任务ID
        String taskId = generateTaskId();
        // 获取用户ID
        int loginUserId = StpUtil.getLoginIdAsInt();
        BaseFlowWork baseFlowWork = BaseFlowWork.SUPERTEXT2IMG;
        // 获取工作流
        ComfyWorkFlow flow = getFlow(baseFlowWork.toString());
        if (flow == null) {
            throw new DrawException(ResultCodeEnum.SERVICE_ERROR);
        }
        //配置采样器
        ComfyWorkFlowNode KSampler = flow.getNode("3");
        KSampler.getInputs().put("seed", Math.abs(new Random().nextInt()));
        KSampler.getInputs().put("steps", superDrawDto.getSteps());
        KSampler.getInputs().put("cfg", superDrawDto.getCfg());
        KSampler.getInputs().put("sampler_name", sampler.getName());
        KSampler.getInputs().put("scheduler", scheduler.getName());
        KSampler.getInputs().put("denoise", superDrawDto.getDenoise());

        // 替换工作流里面的模型
        ComfyWorkFlowNode checkPoint = flow.getNode("4");
        checkPoint.getInputs().put("ckpt_name", checkpoint.getName());

        // 替换工作流里面的图片尺寸
        ComfyWorkFlowNode imageNode = flow.getNode("5");
        imageNode.getInputs().put("width", imageSize.getWidth());
        imageNode.getInputs().put("height", imageSize.getHeight());
        // 替换工作流里面的默认提示词
        ComfyWorkFlowNode positive = flow.getNode("6");
        positive.getInputs().put("text", QUALITY_PREFIX + superDrawDto.getPrompt());

        // 替换工作流里面的反向提示词
        ComfyWorkFlowNode negative = flow.getNode("7");
        negative.getInputs().put("text", superDrawDto.getNegativePrompt());
        log.info("更新后的工作流{}", flow);

        submitDrawingTask(flow, taskId);
        String imageUrl = processTaskResult(taskId);

        return imageUrl;

    }

    private void submitDrawingTask(ComfyWorkFlow flow, String taskId) throws InterruptedException {
        DrawingTaskInfo drawingTaskInfo = new DrawingTaskInfo(taskId, flow, TASK_TIMEOUT, TimeUnit.MINUTES);
        taskSubmit.submit(drawingTaskInfo);
        Thread.sleep(TASK_WAIT_TIME);
    }


    private String generateTaskId() {
        if (StpUtil.getLoginId() == null) {
            throw new DrawException(ResultCodeEnum.APP_LOGIN_AUTH);
        }
        return String.valueOf(StpUtil.getLoginIdAsInt() + DEFAULT_TASK_ID);
    }

    private String processTaskResult(String taskId) {
        String fileName = taskProcessMonitor.getTaskOutputFileName(taskId);
        if (!StringUtils.hasText(fileName)) {
            throw new DrawException(ResultCodeEnum.DATA_ERROR);
        }

        String fileUrl = buildFileUrl(fileName);
        String url = fileService.uploadFromUrl(fileUrl);
        if (!StringUtils.hasText(url)) {
            throw new DrawException(ResultCodeEnum.SERVICE_ERROR);
        }
        return url;
    }

    private String buildFileUrl(String fileName) {
        return String.format("http://%s:%s/view?filename=%s&type=output&preview=WEBP",
                comfyUiProperties.getHost(),
                comfyUiProperties.getPort(),
                fileName);
    }
}

