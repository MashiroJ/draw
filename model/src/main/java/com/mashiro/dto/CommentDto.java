package com.mashiro.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CommentDto {

    @Schema
    private Long drawId;
    private String content;
    private Long parentId;
    private Integer replyUserId;
} 