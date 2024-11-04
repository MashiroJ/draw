package com.mashiro.controller;

import com.comfyui.common.entity.ComfyWorkFlow;
import com.comfyui.common.entity.ComfyWorkFlowNode;
import com.comfyui.queue.common.DrawingTaskInfo;
import com.comfyui.queue.common.IDrawingTaskSubmit;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Tag(name = "绘图相关接口")
@Slf4j
@RestController
@RequestMapping("/system/draw/")
public class DrawController {

    @jakarta.annotation.Resource
    private IDrawingTaskSubmit taskSubmit;
    @jakarta.annotation.Resource
    private ResourceLoader resourceLoader;

    /**
     * 提交任务
     *
     * @param taskId 自定义任务id
     */
    @Operation(summary = "提交任务")
    @PostMapping("submitTask")
    public void submitTask(String taskId) {
        ComfyWorkFlow flow = getFlow();
        assert flow != null;
        ComfyWorkFlowNode node = flow.getNode("3");
        // 设置随机种子
        int randomSeed = Math.abs(new Random().nextInt());
        node.getInputs().put("seed", randomSeed);
        // 提交任务
        taskSubmit.submit(new DrawingTaskInfo(taskId, flow, 5, TimeUnit.MINUTES));
    }

    /**
     * 获取默认的工作流
     */
    @Operation(summary = "获取默认的工作流")
    @GetMapping("getFlow")
    private ComfyWorkFlow getFlow() {
        //从resources文件夹下读取default.json文件
        Resource resource = resourceLoader.getResource("classpath:" + "default.json");
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


}
