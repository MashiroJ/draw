package com.mashiro.enums.ComfyUi;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.mashiro.enums.BaseEnum;
import com.mashiro.exception.DrawException;
import com.mashiro.result.ResultCodeEnum;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ComfyUI图像尺寸枚举  
 */  
public enum ImageSize implements BaseEnum {
    // 方形尺寸  
    SQUARE_256(1, "256x256"),  
    SQUARE_384(2, "384x384"),  
    SQUARE_512(3, "512x512"),  
    
    // 横版尺寸 3:2  
    LANDSCAPE_384_256(11, "384x256"),  
    LANDSCAPE_512_384(12, "512x384"),  
    
    // 横版尺寸 16:9  
    LANDSCAPE_448_256(21, "448x256"),  
    LANDSCAPE_512_288(22, "512x288"),  
    
    // 竖版尺寸 2:3  
    PORTRAIT_256_384(31, "256x384"),  
    PORTRAIT_384_512(32, "384x512"),  
    
    // 竖版尺寸 9:16  
    PORTRAIT_256_448(41, "256x448"),  
    PORTRAIT_288_512(42, "288x512");  

    @EnumValue
    private Integer code;  
    @JsonValue
    private String name;  

    ImageSize(Integer code, String name) {  
        this.code = code;  
        this.name = name;  
    }  

    @Override  
    public Integer getCode() {  
        return this.code;  
    }  

    @Override  
    public String getName() {  
        return this.name;  
    }  

    public int getWidth() {  
        return Integer.parseInt(this.name.split("x")[0]);  
    }  

    public int getHeight() {  
        return Integer.parseInt(this.name.split("x")[1]);  
    }  

    public static ImageSize fromCode(Integer code) {  
        for (ImageSize size : values()) {  
            if (size.getCode().equals(code)) {  
                return size;  
            }  
        }  
        throw new DrawException(ResultCodeEnum.PARAM_ERROR);
    }  

    /**  
     * 根据宽高获取对应的尺寸枚举  
     */  
    public static ImageSize fromDimensions(int width, int height) {  
        String dimensions = width + "x" + height;  
        for (ImageSize size : values()) {  
            if (size.getName().equals(dimensions)) {  
                return size;  
            }  
        }  
        throw new DrawException(ResultCodeEnum.PARAM_ERROR);  
    }  

    /**  
     * 获取指定宽度的所有尺寸  
     */  
    public static List<ImageSize> getByWidth(int width) {
        return Arrays.stream(values())
                .filter(size -> size.getWidth() == width)  
                .collect(Collectors.toList());
    }  

    /**  
     * 获取指定高度的所有尺寸  
     */  
    public static List<ImageSize> getByHeight(int height) {  
        return Arrays.stream(values())  
                .filter(size -> size.getHeight() == height)  
                .collect(Collectors.toList());  
    }  

    /**  
     * 获取所有可用的宽度列表  
     */  
    public static List<Integer> getAllWidths() {  
        return Arrays.stream(values())  
                .map(ImageSize::getWidth)  
                .distinct()  
                .sorted()  
                .collect(Collectors.toList());  
    }  

    /**  
     * 获取所有可用的高度列表  
     */  
    public static List<Integer> getAllHeights() {  
        return Arrays.stream(values())  
                .map(ImageSize::getHeight)  
                .distinct()  
                .sorted()  
                .collect(Collectors.toList());  
    }  
}