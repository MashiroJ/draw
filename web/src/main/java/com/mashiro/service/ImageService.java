package com.mashiro.service;

import java.util.Map;

public interface ImageService {
    Map<String, Map<String, Object>> generateImages(Map<String, Object> prompt) throws Exception;
}