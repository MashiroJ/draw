package com.mashiro.utils.ComfyUi;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spring.comfyui")
public class ComfyUiProperties {
    private String host;
    private String port;
    private String clientId;
}
