package com.mashiro.dto;

import com.mashiro.enums.BaseFlowWork;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DrawDto {

    @NotBlank(message = "绘画提示词不能为空")
    @Schema(description = "绘画提示词")
    private String prompt;

    @Schema(description = "反向提示词")
    private String negativePrompt;

    @Schema(description = "是否公开：1公开，0私有")
    private Byte isPublic;
}

