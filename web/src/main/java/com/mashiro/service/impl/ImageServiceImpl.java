package com.mashiro.service.impl;

import com.mashiro.service.ImageService;
import com.mashiro.utils.WebSocketImageClient;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ImageServiceImpl implements ImageService {


    @Resource
    private WebSocketImageClient webSocketImageClient;

    @Override
    public Map<String, Map<String, Object>> generateImages(Map<String, Object> prompt) throws Exception {
        webSocketImageClient.connect();
        return webSocketImageClient.getImages(prompt);
    }
}