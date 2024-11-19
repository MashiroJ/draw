package com.mashiro.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mashiro.enums.BaseFlowWork;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Data
@TableName(value = "draw_record")
public class DrawRecord extends BaseEntity {

    @Schema(description = "创建用户ID")
    @TableField("user_id")
    private Integer userId;

    @Schema(description = "任务ID")
    @TableField("task_id")
    private String taskId;

    @Schema(description = "绘画提示词")
    private String prompt;

    @Schema(description = "反向提示词")
    @TableField("negative_prompt")
    private String negativePrompt;

    @Schema(description = "生成图片URL")
    @TableField("image_url")
    private String imageUrl;

    @Schema(description = "生成类型：TEXT2IMG/IMG2IMG")
    @TableField("generation_type")
    private String generationType;

    @Schema(description = "工作流名称：TEXT2IMG/IMG2IMG")
    @TableField("work_flow_name")
    private BaseFlowWork workFlowName;

    @Schema(description = "点赞数")
    @TableField("like_count")
    private Integer likeCount;

    @Schema(description = "是否公开：1公开，0私有")
    @TableField("is_public")
    private Byte isPublic;
}
