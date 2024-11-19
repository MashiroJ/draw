package com.mashiro.utils.ComfyUi;

import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConditionalOnProperty(name = "spring.comfyui.host")
@EnableConfigurationProperties(ComfyUiProperties.class)
public class ComfyUiConfiguration {
    @Resource
    private ComfyUiProperties comfyUiProperties;

    public ComfyUiProperties getComfyUiProperties() {
        return comfyUiProperties;
    }
}
