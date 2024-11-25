package com.mashiro.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "AI绘画参数配置类")
public class SuperDrawDto {
    @Schema(description = "绘画提示词", example = "1girl, Loli, cute, solo, beautiful and delicate eyes, exquisite face, sweater, pink hair, side bun, violet eyes, high gloss dyeing, shirt, micro skirt, black pantyhose, perspective, street, bell tower, depth of field, gray background coloring.")
    private String prompt;

    @Schema(description = "反向绘画提示词", example = "badhandv4, EasyNegative, verybadimagenegative_v1.3,illustration, 3d, sepia, painting, cartoons, sketch, (worst quality:1.74), (low quality:1.74), (normal quality:1.44), lowres, bad anatomy, normal quality, ((monochrome)), ((grayscale)), ((letters)), ((english)), capital.")
    private String negativePrompt;

    @Schema(description = "采样步数", example = "20")
    private Integer steps;

    @Schema(description = "cfg", example = "7")
    private Integer cfg;

    @Schema(description = "去噪程度", example = "0.3")
    private double denoise;

    @Schema(description = "是否公开：1公开，0私有")
    private Byte isPublic;
}