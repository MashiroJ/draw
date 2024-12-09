package com.mashiro.utils.TaskProcessMonitor;

import com.comfyui.common.process.*;
import com.comfyui.monitor.message.receiver.ITaskProcessReceiver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class TaskProcessMonitor implements ITaskProcessReceiver {

    // 用于存储任务ID和输出文件名的映射关系
    private final Map<String, String> taskOutputMap = new ConcurrentHashMap<>();
    // 添加任务状态映射
    private final Map<String, TaskStatus> taskStatusMap = new ConcurrentHashMap<>();
    
    // 添加任务状态枚举
    public enum TaskStatus {
        PENDING,    // 等待中
        RUNNING,    // 运行中
        COMPLETED,  // 已完成
        ERROR      // 错误
    }

    /**
     * 任务开始
     *
     * @param start 任务开始信息
     */
    @Override
    public void taskStart(ComfyTaskStart start) {
        log.info("任务开始, 任务id:{},ComfyUi任务ID：{}", start.getTaskId(), start.getComfyTaskId());
        taskStatusMap.put(start.getTaskId(), TaskStatus.RUNNING);
    }

    /**
     * 当前执行的节点和节点执行进度
     *
     * @param progress 进度信息
     */
    @Override
    public void taskNodeProgress(ComfyTaskNodeProgress progress) {
        log.info("任务进度更新, 任务id: {}, 内部任务id: {}, 当前节点名: {}, 当前进度:{}%", progress.getTaskId(), progress.getComfyTaskId(), progress.getNode().getTitle(), progress.getPercent());
    }

    /**
     * 任务进度预览效果图
     *
     * @param preview 预览图信息
     */
    @Override
    public void taskProgressPreview(ComfyTaskProgressPreview preview) {
        log.info("任务进度预览, 任务id: {}, 内部任务id: {}, 预览: <图片>", preview.getTaskId(), preview.getComfyTaskId());
    }

    /**
     * 任务输出的图片
     *
     * @param output 输出信息
     */
    @Override
    public void taskOutput(ComfyTaskOutput output) {
        log.info("任务输出结果, 任务id: {}, 节点名: {}, 内部任务id: {}", output.getTaskId(), output.getNode().getTitle(), output.getComfyTaskId());
    }

    /**
     * 任务完成
     *
     * @param complete 任务完成信息
     */
    @Override
    public void taskComplete(ComfyTaskComplete complete) {
        log.info("任务完成, 任务id: {}, 内部任务id: {},图片预览：{}",
                complete.getTaskId(),
                complete.getComfyTaskId(),
                complete.getImages());
        // 将任务id和对应的文件名存储到taskOutputMap中
        if (complete.getImages() != null && !complete.getImages().isEmpty()) {
            String outputFileName = complete.getImages().get(0).getFileName();
            taskOutputMap.put(complete.getTaskId(), outputFileName);
        }
        taskStatusMap.put(complete.getTaskId(), TaskStatus.COMPLETED);
    }

    /**
     * 任务失败
     *
     * @param error 任务错误信息
     */
    @Override
    public void taskError(ComfyTaskError error) {
        log.error("任务错误， 任务id:{}, 内部任务id: {}, 错误信息: {}", error.getTaskId(), error.getComfyTaskId(), error.getErrorInfo());
        taskStatusMap.put(error.getTaskId(), TaskStatus.ERROR);
    }

    /**
     * 绘图队列任务个数更新
     *
     * @param taskNumber 队列任务信息
     */
    @Override
    public void taskNumberUpdate(ComfyTaskNumber taskNumber) {
    }

    /**
     * 当前系统负载
     *
     * @param performance 系统状态
     */
    @Override
    public void systemPerformance(ComfySystemPerformance performance) {
    }

    /**
     * 获取任务输出���件名
     *
     * @param taskId 任务ID
     * @return 文件名
     */
    public String getTaskOutputFileName(String taskId) {
        return taskOutputMap.get(taskId);
    }

    // 添加获取任务状态的方法
    public TaskStatus getTaskStatus(String taskId) {
        return taskStatusMap.getOrDefault(taskId, TaskStatus.PENDING);
    }

    // 添加等待任务完成的方法
    public boolean waitForCompletion(String taskId, long timeout, TimeUnit unit) throws InterruptedException {
        long endTime = System.currentTimeMillis() + unit.toMillis(timeout);
        while (System.currentTimeMillis() < endTime) {
            TaskStatus status = getTaskStatus(taskId);
            switch (status) {
                case COMPLETED:
                    return true;
                case ERROR:
                    return false;
                default:
                    Thread.sleep(1000); // 每秒检查一次状态
            }
        }
        return false; // 超时返回false
    }

    /**
     * 清除任务状态
     *
     * @param taskId 任务ID
     */
    public void clearTaskStatus(String taskId) {
        taskStatusMap.remove(taskId);
        taskOutputMap.remove(taskId);
    }
}