package com.mashiro.config.comfyUi;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WorkflowNodeConfig {
    private String kSamplerId;
    private String checkPointId;
    private String imageNodeId;
    private String positiveId;
    private String negativeId;
    private String inputImageId;

    public static WorkflowNodeConfig superText2ImgConfig() {
        return WorkflowNodeConfig.builder()
                .kSamplerId("3")
                .checkPointId("4")
                .imageNodeId("5")
                .positiveId("6")
                .negativeId("7")
                .build();
    }

    public static WorkflowNodeConfig superImg2ImgConfig() {
        return WorkflowNodeConfig.builder()
                .kSamplerId("3")
                .checkPointId("14")
                .positiveId("6")
                .negativeId("7")
                .inputImageId("10")
                .build();
    }
} 