package com.mashiro.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.comfyui.common.entity.ComfyWorkFlow;
import com.comfyui.common.entity.ComfyWorkFlowNode;
import com.comfyui.queue.common.DrawingTaskInfo;
import com.comfyui.queue.common.IDrawingTaskSubmit;
import com.mashiro.dto.DrawDto;
import com.mashiro.entity.DrawRecord;
import com.mashiro.enums.BaseFlowWork;
import com.mashiro.mapper.DrawRecordMapper;
import com.mashiro.result.Result;
import com.mashiro.service.DrawService;
import com.mashiro.service.FileService;
import com.mashiro.utils.ComfyUi.ComfyUiProperties;
import com.mashiro.utils.TaskProcessMonitor.TaskProcessMonitor;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.mashiro.constant.UserConstant.DEFAULT_TASK_ID;

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
    @Resource
    private DrawRecordMapper drawRecordMapper;


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


    /**
     * 获取指定工作流
     *
     * @return
     */
    public ComfyWorkFlow getFlow(String workFlowName) {
        String FlowName = workFlowName + ".json";
        org.springframework.core.io.Resource resource = resourceLoader.getResource("classpath:" + FlowName);
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
     * @param baseFlowWork
     * @return
     */
    @Override
    public Result<String> text2img(@RequestBody DrawDto drawDto, BaseFlowWork baseFlowWork) throws InterruptedException {
        int loginUserId = StpUtil.getLoginIdAsInt();
        String taskId = String.valueOf(loginUserId + DEFAULT_TASK_ID);
        log.info("用户ID: {}, 提交任务，任务ID: {}", loginUserId, taskId);
        String ChinesePrompt = drawDto.getPrompt();
        if (ChinesePrompt == null || ChinesePrompt.trim().isEmpty()) {
            throw new IllegalArgumentException("绘画提示词不能为空");
        }

        // 获取工作流并设置参数
        ComfyWorkFlow flow = getFlow(String.valueOf(baseFlowWork));
        if (flow == null) {
            throw new RuntimeException("工作流获取失败");
        }
        log.info("已获取工作流: {}", flow);

        // 根据工作流节点中的节点ID获取节点对象，并设置随机种子
        ComfyWorkFlowNode node3 = flow.getNode("3");
        if (node3 != null) {
            int randomSeed = Math.abs(new Random().nextInt());
            node3.getInputs().put("seed", randomSeed);
            log.info("已设置节点3的随机种子: {}", randomSeed);
        } else {
            log.warn("工作流中未找到ID为 '3' 的节点");
        }

        // 反向提示词
        ComfyWorkFlowNode node7 = flow.getNode("7");
        String NegativePrompt = (String) node7.getInputs().get("text");

        // 提交任务
        DrawingTaskInfo drawingTaskInfo = new DrawingTaskInfo(taskId, flow, 5, TimeUnit.MINUTES);
        taskSubmit.submit(drawingTaskInfo);

        //20秒
        Thread.sleep(10000);
        String host = comfyUiProperties.getHost();
        String port = comfyUiProperties.getPort();
        String fileName = taskProcessMonitor.getTaskOutputFileName(taskId);
        if (fileName != null) {
            String fileUrl = "http://" + host + ":" + port + "/view?filename=" + fileName + "&type=output&preview=WEBP";
            String url = fileService.uploadFromUrl(fileUrl);
            // 创建并保存 DrawRecord 实例
            DrawRecord drawRecord = new DrawRecord();
            drawRecord.setUserId(loginUserId);
            drawRecord.setTaskId(taskId);
            drawRecord.setPrompt(drawDto.getPrompt());
            drawRecord.setNegativePrompt(NegativePrompt);
            drawRecord.setGenerationType("TEXT2IMG");
            drawRecord.setWorkFlowName(baseFlowWork);
            drawRecord.setIsPublic(drawDto.getIsPublic());
            drawRecord.setImageUrl(url);
            drawRecordMapper.insert(drawRecord);
            log.info("已创建 DrawRecord，任务ID: {}", taskId);
            return Result.ok(url);
        } else {
            return Result.error("未找到对应任务的输出文件名");
        }
    }
}



