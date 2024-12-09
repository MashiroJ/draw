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
    private String seedNodeId;

    public static WorkflowNodeConfig text2ImgConfig() {
        return WorkflowNodeConfig.builder()
                .kSamplerId("3")
                .checkPointId("4")
                .imageNodeId("5")
                .positiveId("10")
                .negativeId("7")
                .seedNodeId("3")
                .build();
    }

    public static WorkflowNodeConfig img2ImgConfig() {
        return WorkflowNodeConfig.builder()
                .kSamplerId("3")
                .checkPointId("4")
                .positiveId("5")
                .negativeId("7")
                .inputImageId("10")
                .seedNodeId("3")
                .build();
    }

    public static WorkflowNodeConfig superText2ImgConfig() {
        return WorkflowNodeConfig.builder()
                .kSamplerId("3")
                .checkPointId("4")
                .imageNodeId("5")
                .positiveId("6")
                .negativeId("7")
                .seedNodeId("3")
                .build();
    }

    public static WorkflowNodeConfig superImg2ImgConfig() {
        return WorkflowNodeConfig.builder()
                .kSamplerId("3")
                .checkPointId("14")
                .positiveId("6")
                .negativeId("7")
                .inputImageId("10")
                .seedNodeId("3")
                .build();
    }
} 