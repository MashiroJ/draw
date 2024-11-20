package com.mashiro.vo;

import lombok.Data;

@Data
public class DrawRecordVO {
    private Long id;
    private Integer userId;
    private String imageUrl;
    private String prompt;
    private Integer likeCount;
    private String generationType;
}  