package com.mashiro.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.comfyui.common.entity.ComfyWorkFlow;
import com.comfyui.common.entity.ComfyWorkFlowNode;
import com.comfyui.queue.common.DrawingTaskInfo;
import com.comfyui.queue.common.IDrawingTaskSubmit;
import com.mashiro.dto.DrawDto;
import com.mashiro.entity.DrawRecord;
import com.mashiro.enums.BaseFlowWork;
import com.mashiro.exception.DrawException;
import com.mashiro.mapper.DrawRecordMapper;
import com.mashiro.result.Result;
import com.mashiro.result.ResultCodeEnum;
import com.mashiro.service.DrawService;
import com.mashiro.service.FileService;
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
public class DrawServiceImpl implements DrawService {

    private static final int TASK_WAIT_TIME = 20000; // 10 seconds
    private static final int TASK_TIMEOUT = 5; // 5 minutes

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
    @Resource
    private DrawRecordMapper drawRecordMapper;

    /**
     * 获取指定工作流
     * @param workFlowName 工作流名称
     * @return 工作流对象
     */
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

    /**
     * 查看图片
     * @param taskId
     * @return
     */
    @Override
    public Result<String> viewImg(String taskId) {
        try {
            String fileName = taskProcessMonitor.getTaskOutputFileName(taskId);
            if (!StringUtils.hasText(fileName)) {
                return Result.build(null, ResultCodeEnum.DATA_ERROR);
            }
            String fileUrl = buildFileUrl(fileName);
            String url = fileService.uploadFromUrl(fileUrl);
            return Result.ok(url);
        } catch (Exception e) {
            log.error("查看图片失败，taskId: {}", taskId, e);
            return Result.build(null, ResultCodeEnum.SERVICE_ERROR);
        }
    }

    @Override
    public String text2img(DrawDto drawDto, BaseFlowWork baseFlowWork) {
        validateDrawRequest(drawDto);

        String taskId = generateTaskId();
        int loginUserId = StpUtil.getLoginIdAsInt();
        log.info("用户ID: {}, 提交任务，任务ID: {}", loginUserId, taskId);

        try {
            ComfyWorkFlow flow = prepareWorkFlow(baseFlowWork);
            String negativePrompt = submitDrawingTask(flow, taskId);
            String imageUrl = processTaskResult(taskId);
            saveDrawRecord(drawDto, negativePrompt, imageUrl, loginUserId, taskId, baseFlowWork);
            return imageUrl;
        } catch (InterruptedException e) {
            log.error("任务执行被中断，userId: {}, taskId: {}", loginUserId, taskId, e);
            throw new DrawException(ResultCodeEnum.SERVICE_ERROR);
        } catch (Exception e) {
            log.error("文生图失败，userId: {}, taskId: {}", loginUserId, taskId, e);
            throw new DrawException(ResultCodeEnum.SERVICE_ERROR);
        }
    }

    private void validateDrawRequest(DrawDto drawDto) {
        if (!StringUtils.hasText(drawDto.getPrompt())) {
            throw new DrawException(ResultCodeEnum.PARAM_ERROR);
        }
    }

    private String generateTaskId() {
        if (StpUtil.getLoginId() == null) {
            throw new DrawException(ResultCodeEnum.APP_LOGIN_AUTH);
        }
        return String.valueOf(StpUtil.getLoginIdAsInt() + DEFAULT_TASK_ID);
    }

    private ComfyWorkFlow prepareWorkFlow(BaseFlowWork baseFlowWork) {
        ComfyWorkFlow flow = getFlow(baseFlowWork.toString());
        if (flow == null) {
            throw new DrawException(ResultCodeEnum.SERVICE_ERROR);
        }
        configureRandomSeed(flow);
        return flow;
    }

    private void configureRandomSeed(ComfyWorkFlow flow) {
        ComfyWorkFlowNode node3 = flow.getNode("3");
        if (node3 != null) {
            node3.getInputs().put("seed", Math.abs(new Random().nextInt()));
        } else {
            log.warn("工作流中未找到ID为 '3' 的节点");
        }
    }

    private String submitDrawingTask(ComfyWorkFlow flow, String taskId) throws InterruptedException {
        ComfyWorkFlowNode node7 = flow.getNode("7");
        String negativePrompt = node7 != null ? (String) node7.getInputs().get("text") : "";

        DrawingTaskInfo drawingTaskInfo = new DrawingTaskInfo(taskId, flow, TASK_TIMEOUT, TimeUnit.MINUTES);
        taskSubmit.submit(drawingTaskInfo);
        Thread.sleep(TASK_WAIT_TIME);

        return negativePrompt;
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

    private void saveDrawRecord(DrawDto drawDto, String negativePrompt,
                              String imageUrl, int userId, String taskId, BaseFlowWork baseFlowWork) {
        try {
            DrawRecord drawRecord = new DrawRecord();
            drawRecord.setUserId(userId);
            drawRecord.setTaskId(taskId);
            drawRecord.setPrompt(drawDto.getPrompt());
            drawRecord.setNegativePrompt(negativePrompt);
            drawRecord.setGenerationType("TEXT2IMG");
            drawRecord.setWorkFlowName(baseFlowWork);
            drawRecord.setIsPublic(drawDto.getIsPublic());
            drawRecord.setImageUrl(imageUrl);

            drawRecordMapper.insert(drawRecord);
        } catch (Exception e) {
            log.error("保存绘图记录失败，userId: {}, taskId: {}", userId, taskId, e);
            throw new DrawException(ResultCodeEnum.DATA_ERROR);
        }
    }
}