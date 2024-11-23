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
import com.mashiro.service.PointsService;
import com.mashiro.service.UserService;
import com.mashiro.utils.ComfyUi.ComfyUiProperties;
import com.mashiro.utils.TaskProcessMonitor.TaskProcessMonitor;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.mashiro.constant.UserConstant.DEFAULT_TASK_ID;

@Slf4j
@Service
public class DrawServiceImpl implements DrawService {

    private static final int TASK_WAIT_TIME = 20000; // 20秒
    private static final int TASK_TIMEOUT = 5; // 5分钟
    final String QUALITY_PREFIX = "杰作,质量最好的,";


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
     *
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
     *
     * @param taskId 任务ID
     * @return 图片URL
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

    /**
     * 文生图接口
     *
     * @param drawDto
     * @return
     */
    @Override
    public String text2img(DrawDto drawDto) {
        // 参数校验
        validateDrawRequest(drawDto);
        // 提示词
        String prompt = QUALITY_PREFIX + drawDto.getPrompt();
        // 生成任务ID
        String taskId = generateTaskId();
        // 获取用户ID
        int loginUserId = StpUtil.getLoginIdAsInt();
        log.info("用户ID: {}, 提交任务，任务ID: {}", loginUserId, taskId);

        try {
            // 准备工作流
            BaseFlowWork baseFlowWork = BaseFlowWork.TEXT2IMG;
            // 获取工作流, 并替换工作流里面的默认提示词
            ComfyWorkFlow flow = prepareWorkFlow(baseFlowWork, prompt);
            // 获取反向提示词
            String negativePrompt = getNegativePrompt(flow);
            // 提交任务
            submitDrawingTask(flow, taskId);
            // 根据taskId获取图像Url处理任务结果
            String imageUrl = processTaskResult(taskId);

            // 保存绘图记录
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

    /**
     * 图生图接口
     *
     * @param drawDto
     * @param uploadImage
     * @return
     */
    @Override
    public String img2img(DrawDto drawDto, MultipartFile uploadImage) {
        // 参数校验
        validateImg2ImgRequest(drawDto, uploadImage);
        // 提示词
        String prompt = QUALITY_PREFIX + drawDto.getPrompt();
        // 生成任务ID
        String taskId = generateTaskId();
        // 获取用户ID
        int loginUserId = StpUtil.getLoginIdAsInt();
        log.info("用户ID: {}, 提交图生图任务，任务ID: {}", loginUserId, taskId);

        try {
            // 处理上传图片
            String uploadedImagePath = handleUploadedImage(uploadImage, taskId);
            // 准备工作流
            BaseFlowWork baseFlowWork = BaseFlowWork.IMG2IMG;
            // 获取工作流, 并替换工作流里面的默认提示词和默认文件
            ComfyWorkFlow flow = prepareImg2ImgWorkFlow(baseFlowWork, uploadedImagePath, prompt);
            // 获取反向提示词
            String negativePrompt = getNegativePrompt(flow);
            // 提交任务
            submitDrawingTask(flow, taskId);
            // 根据taskId获取图像Url处理任务结果
            String imageUrl = processTaskResult(taskId);

            // 保存绘图记录
            saveDrawRecord(drawDto, negativePrompt, imageUrl, loginUserId, taskId, baseFlowWork);
            return imageUrl;
        } catch (InterruptedException e) {
            log.error("图生图任务执行被中断，userId: {}, taskId: {}", loginUserId, taskId, e);
            throw new DrawException(ResultCodeEnum.SERVICE_ERROR);
        } catch (Exception e) {
            log.error("图生图失败，userId: {}, taskId: {}", loginUserId, taskId, e);
            throw new DrawException(ResultCodeEnum.SERVICE_ERROR);
        }
    }

    /**
     * 验证文生图请求参数
     *
     * @param drawDto
     */
    private void validateDrawRequest(DrawDto drawDto) {
        if (!StringUtils.hasText(drawDto.getPrompt())) {
            throw new DrawException(ResultCodeEnum.PARAM_ERROR);
        }
    }

    /**
     * 校验图生图请求参数
     *
     * @param drawDto
     * @param uploadImage
     */
    private void validateImg2ImgRequest(DrawDto drawDto, MultipartFile uploadImage) {
        if (!StringUtils.hasText(drawDto.getPrompt())) {
            throw new DrawException(ResultCodeEnum.PARAM_ERROR);
        }
        if (uploadImage == null || uploadImage.isEmpty()) {
            throw new DrawException(ResultCodeEnum.PARAM_ERROR);
        }
    }

    /**
     * 生成任务ID
     *
     * @return
     */
    private String generateTaskId() {
        if (StpUtil.getLoginId() == null) {
            throw new DrawException(ResultCodeEnum.APP_LOGIN_AUTH);
        }
        return String.valueOf(StpUtil.getLoginIdAsInt() + DEFAULT_TASK_ID);
    }

    /**
     * 预处理文生图工作流
     *
     * @param baseFlowWork
     * @param prompt
     * @return
     */
    private ComfyWorkFlow prepareWorkFlow(BaseFlowWork baseFlowWork, String prompt) {
        // 获取工作流
        ComfyWorkFlow flow = getFlow(baseFlowWork.toString());
        if (flow == null) {
            throw new DrawException(ResultCodeEnum.SERVICE_ERROR);
        }
        // 替换工作流里面的默认提示词
        ComfyWorkFlowNode promptNode = flow.getNode("10");
        if (promptNode != null) {
            promptNode.getInputs().put("text", prompt);
        } else {
            log.warn("工作流中未找到promptNode的节点");
        }

        // 配置随机种子
        configureRandomSeed(flow);
        log.info("获取修改后的文生图工作流: {}", flow);
        return flow;
    }

    /**
     * 预处理图生图工作流
     *
     * @param baseFlowWork
     * @param uploadedImagePath
     * @param prompt
     * @return
     */
    private ComfyWorkFlow prepareImg2ImgWorkFlow(BaseFlowWork baseFlowWork, String uploadedImagePath, String prompt) {
        ComfyWorkFlow flow = getFlow(baseFlowWork.toString());
        log.info("获取图生图工作流: {}", flow);
        if (flow == null) {
            throw new DrawException(ResultCodeEnum.SERVICE_ERROR);
        }

        // 更新工作流中的图片加载节点
        ComfyWorkFlowNode imageNode = flow.getNode("10");
        if (imageNode != null) {
            imageNode.getInputs().put("image", uploadedImagePath);
        } else {
            log.warn("工作流中未找到imageNode的图片加载节点");
        }

        // 配置随机种子
        configureRandomSeed(flow);
        // 替换工作流里面的默认提示词
        ComfyWorkFlowNode promptNode = flow.getNode(5);
        promptNode.getInputs().put("text", prompt);
        return flow;
    }

    /**
     * 配置随机种子
     *
     * @param flow
     */
    private void configureRandomSeed(ComfyWorkFlow flow) {
        ComfyWorkFlowNode seedNode = flow.getNode("3");
        if (seedNode != null) {
            seedNode.getInputs().put("seed", Math.abs(new Random().nextInt()));
        } else {
            log.warn("工作流中seedNode的节点");
        }
    }

    /**
     * 处理上传的图片
     *
     * @param uploadImage
     * @param taskId
     * @return
     */
    private String handleUploadedImage(MultipartFile uploadImage, String taskId) {
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

    /**
     * 获取反向提示词
     *
     * @param flow
     * @return
     */
    private static String getNegativePrompt(ComfyWorkFlow flow) {
        ComfyWorkFlowNode negativePromptNode = flow.getNode("7");
        return (String) negativePromptNode.getInputs().get("text");
    }

    /**
     * 获取negativePrompt信息，并提交任务
     *
     * @param flow
     * @param taskId
     * @return
     * @throws InterruptedException
     */
    private void submitDrawingTask(ComfyWorkFlow flow, String taskId) throws InterruptedException {
        DrawingTaskInfo drawingTaskInfo = new DrawingTaskInfo(taskId, flow, TASK_TIMEOUT, TimeUnit.MINUTES);
        taskSubmit.submit(drawingTaskInfo);
        Thread.sleep(TASK_WAIT_TIME);
    }

    /**
     * 处理任务结果，并返回图片URL
     *
     * @param taskId
     * @return
     */
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

    /**
     * 构建文件访问URL
     *
     * @param fileName
     * @return
     */
    private String buildFileUrl(String fileName) {
        return String.format("http://%s:%s/view?filename=%s&type=output&preview=WEBP",
                comfyUiProperties.getHost(),
                comfyUiProperties.getPort(),
                fileName);
    }

    /**
     * 保存绘图记录
     *
     * @param drawDto
     * @param negativePrompt
     * @param imageUrl
     * @param userId
     * @param taskId
     * @param baseFlowWork
     */
    private void saveDrawRecord(DrawDto drawDto, String negativePrompt, String imageUrl, int userId, String taskId, BaseFlowWork baseFlowWork) {
        try {
            DrawRecord drawRecord = new DrawRecord();
            drawRecord.setUserId(userId);
            drawRecord.setTaskId(taskId);
            drawRecord.setPrompt(drawDto.getPrompt());
            drawRecord.setNegativePrompt(negativePrompt);

            // 根据 baseFlowWork 设置生成类型
            String generationType;
            if (baseFlowWork == BaseFlowWork.TEXT2IMG) {
                generationType = "TEXT2IMG";
            } else if (baseFlowWork == BaseFlowWork.IMG2IMG) {
                generationType = "IMG2IMG";
            } else {
                generationType = "DEFAULT";
            }
            drawRecord.setGenerationType(generationType);
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
