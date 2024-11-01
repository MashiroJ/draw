package com.mashiro.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashiro.result.Result;
import com.mashiro.service.ImageService;
import com.mashiro.utils.WebSocketImageClient;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/system/image")
public class ImageController {

    @Resource
    private ImageService imageService;
    @Resource
    private WebSocketImageClient webSocketImageClient;

    @GetMapping("/test")
    public void testWEbsockets() throws Exception {
        webSocketImageClient.connect();
    }

    @PostMapping("/generate")
    public Result<Map<String, Map<String, Object>>> generateImages(@RequestParam String prompt) {
        try {
            // 解析JSON字符串
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> promptMap = objectMapper.readValue(prompt, Map.class);

            // 这里假设你还会做一些修改
            Map<String, Object> node6Inputs = (Map<String, Object>) ((Map<String, Object>) promptMap.get("6")).get("inputs");
            node6Inputs.put("text", "masterpiece best quality man");

            Map<String, Object> node3Inputs = (Map<String, Object>) ((Map<String, Object>) promptMap.get("3")).get("inputs");
            node3Inputs.put("seed", 5);

            // 调用服务类方法时传递这个Map
            Map<String, Map<String, Object>> images = imageService.generateImages(promptMap);
            return Result.ok(images);
        } catch (Exception e) {
            return Result.error("Error generating images: " + e.getMessage());
        }
    }
}