package com.mashiro.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@TableName(value = "draw_like")
@Data
public class DrawLike extends BaseEntity {

    @Schema(description = "绘画记录ID")
    @TableField("draw_id")
    private Long drawId;

    @Schema(description = "用户ID")
    @TableField("user_id")
    private Integer userId;

    @Schema(description = "是否点赞")
    @TableField("is_liked")
    private Boolean isLiked;
}
