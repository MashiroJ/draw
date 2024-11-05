package com.mashiro.service.impl;

import com.comfyui.common.entity.ComfyWorkFlow;
import com.comfyui.common.entity.ComfyWorkFlowNode;
import com.comfyui.queue.common.DrawingTaskInfo;
import com.comfyui.queue.common.IDrawingTaskSubmit;
import com.mashiro.dto.DrawDto;
import com.mashiro.result.Result;
import com.mashiro.service.DrawService;
import com.mashiro.service.FileService;
import com.mashiro.utils.ComfyUi.ComfyUiProperties;
import com.mashiro.utils.TaskProcessMonitor.TaskProcessMonitor;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class DrawServiceImpl implements DrawService {

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

    /**
     * 获取默认工作流
     *
     * @return
     */
    public ComfyWorkFlow getFlow() {
        org.springframework.core.io.Resource resource = resourceLoader.getResource("classpath:" + "default.json");
        StringBuilder flowStr = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                flowStr.append(line);
            }
        } catch (IOException e) {
            return null;
        }
        return ComfyWorkFlow.of(flowStr.toString());
    }

    /**
     * 提交文生图任务
     *
     * @param drawDto
     */
    @Override
    public void text2img(DrawDto drawDto) {
        String taskId = drawDto.getTaskId();
        String text = drawDto.getText();
        if (taskId == null) {
            throw new RuntimeException("taskId不能为空");
        }
        if (text == null) {
            throw new RuntimeException("text不能为空");
        }
        ComfyWorkFlow flow = getFlow();
        assert flow != null;
        // 根据工作流节点中的节点id获取节点对象，并设置随机种子
        ComfyWorkFlowNode node3 = flow.getNode("3");
        int randomSeed = Math.abs(new Random().nextInt());
        node3.getInputs().put("seed", randomSeed);

        ComfyWorkFlowNode node6 = flow.getNode(6);
        Object text1 = node6.getInputs().get("text");
        log.info("text1: {}", text1);
        node6.getInputs().put("text", text);
        Object text2 = node6.getInputs().get("text");
        log.info("text2: {}", text2);
        // 提交任务
        DrawingTaskInfo drawingTaskInfo = new DrawingTaskInfo(taskId, flow, 5, TimeUnit.MINUTES);
        taskSubmit.submit(drawingTaskInfo);
    }

    /**
     * 根据TaskId查看图片
     *
     * @param taskId
     * @return
     */
    @Override
    public Result<String> viewImg(String taskId) {
        String host = comfyUiProperties.getHost();
        String port = comfyUiProperties.getPort();
        String fileName = taskProcessMonitor.getTaskOutputFileName(taskId);

        if (fileName != null) {
            String fileUrl = "http://" + host + ":" + port + "/view?filename=" + fileName + "&type=output&preview=WEBP";
            String url = fileService.uploadFromUrl(fileUrl);
            return Result.ok(url);
        } else {
            return Result.error("未找到对应任务的输出文件名");
        }
    }
}
