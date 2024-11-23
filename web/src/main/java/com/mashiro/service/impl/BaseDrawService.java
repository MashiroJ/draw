package com.mashiro.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.comfyui.common.entity.ComfyWorkFlow;
import com.comfyui.queue.common.DrawingTaskInfo;
import com.comfyui.queue.common.IDrawingTaskSubmit;
import com.mashiro.dto.SuperDrawDto;
import com.mashiro.entity.DrawRecord;
import com.mashiro.enums.BaseFlowWork;
import com.mashiro.exception.DrawException;
import com.mashiro.mapper.DrawRecordMapper;
import com.mashiro.result.ResultCodeEnum;
import com.mashiro.service.FileService;
import com.mashiro.utils.ComfyUi.ComfyUiProperties;
import com.mashiro.utils.TaskProcessMonitor.TaskProcessMonitor;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import static com.mashiro.constant.UserConstant.DEFAULT_TASK_ID;

@Slf4j
public abstract class BaseDrawService {

    protected static final int TASK_WAIT_TIME = 20000;
    protected static final int TASK_TIMEOUT = 5;
    protected static final String QUALITY_PREFIX = "masterpiece, best quality,";

    @Resource
    protected IDrawingTaskSubmit taskSubmit;
    @Resource
    protected ResourceLoader resourceLoader;
    @Resource
    protected TaskProcessMonitor taskProcessMonitor;
    @Resource
    protected ComfyUiProperties comfyUiProperties;
    @Resource
    protected FileService fileService;
    @Resource
    private DrawRecordMapper drawRecordMapper;

    protected ComfyWorkFlow getFlow(String workFlowName) {
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

    protected String generateTaskId() {
        if (StpUtil.getLoginId() == null) {
            throw new DrawException(ResultCodeEnum.APP_LOGIN_AUTH);
        }
        return String.valueOf(StpUtil.getLoginIdAsInt() + DEFAULT_TASK_ID);
    }

    protected void submitDrawingTask(ComfyWorkFlow flow, String taskId) throws InterruptedException {
        DrawingTaskInfo drawingTaskInfo = new DrawingTaskInfo(taskId, flow, TASK_TIMEOUT, TimeUnit.MINUTES);
        taskSubmit.submit(drawingTaskInfo);
        Thread.sleep(TASK_WAIT_TIME);
    }

    protected String processTaskResult(String taskId) {
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

    protected void saveDrawRecord(int userId, String taskId, SuperDrawDto superDrawDto, String imageUrl, BaseFlowWork baseFlowWork) {
        try {
            DrawRecord drawRecord = new DrawRecord();
            drawRecord.setUserId(userId);
            drawRecord.setTaskId(taskId);
            drawRecord.setPrompt(superDrawDto.getPrompt());
            drawRecord.setNegativePrompt(superDrawDto.getNegativePrompt());

            // 根据 baseFlowWork 设置生成类型
            String generationType;
            if (baseFlowWork == BaseFlowWork.SUPERTEXT2IMG) {
                generationType = "SUPERTEXT2IMG";
            } else if (baseFlowWork == BaseFlowWork.SUPERIMG2IMG) {
                generationType = "SUPERIMG2IMG";
            } else {
                generationType = "DEFAULT";
            }
            drawRecord.setGenerationType(generationType);
            drawRecord.setWorkFlowName(baseFlowWork);
            drawRecord.setIsPublic(superDrawDto.getIsPublic());
            drawRecord.setImageUrl(imageUrl);

            drawRecordMapper.insert(drawRecord);
        } catch (Exception e) {
            log.error("保存绘图记录失败，userId: {}, taskId: {}", userId, taskId, e);
            throw new DrawException(ResultCodeEnum.DATA_ERROR);
        }
    }

    protected String handleUploadedImage(MultipartFile uploadImage, String taskId) {
        try {
            // 获取原始文件名
            String originalFilename = uploadImage.getOriginalFilename();
            // 获取文件扩展名
            String fileExtension = StringUtils.getFilenameExtension(originalFilename);
            // 生成保存的文件名
            String savedFileName = taskId + "." + fileExtension;

            // 保存上传的图片到指定目录
            Path destinationPath = Paths.get(comfyUiProperties.getInputPath(), savedFileName);
            // 创建父目录
            Files.createDirectories(destinationPath.getParent());
            // 保存文件
            uploadImage.transferTo(destinationPath.toFile());
            // 返回保存的文件名
            return savedFileName;
        } catch (IOException e) {
            log.error("图片上传处理失败", e);
            throw new DrawException(ResultCodeEnum.SERVICE_ERROR);
        }
    }
}