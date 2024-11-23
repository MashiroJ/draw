package com.mashiro.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.mashiro.exception.DrawException;
import com.mashiro.result.ResultCodeEnum;

public enum BaseFlowWork implements BaseEnum {
    DEFAULT(1, "default"),
    TEXT2IMG(2, "text2img"),
    IMG2IMG(3, "img2img"),
    SUPERTEXT2IMG(4, "superText2Img"),
    SUPERIMG2IMG(5, "superImg2Img");

    @EnumValue
    private Integer code;
    @JsonValue
    private String name;

    BaseFlowWork(Integer code, String name) {
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

    /**
     * 根据工作流代码返回对应的BaseFlowWork枚举实例
     * 如果找不到匹配的工作流代码，则抛出参数错误的异常
     *
     * @param code 工作流代码，用于识别特定的工作流
     * @return 对应工作流代码的BaseFlowWork枚举实例
     * @throws DrawException 如果工作流代码不存在，则抛出此异常
     */
    public static BaseFlowWork fromCode(Integer code) {
        // 遍历枚举中的所有工作流
        for (BaseFlowWork flowWork : values()) {
            // 检查当前工作流的代码是否与传入的代码匹配
            if (flowWork.getCode().equals(code)) {
                // 如果匹配，则返回当前工作流实例
                return flowWork;
            }
        }
        // 如果没有找到匹配的工作流代码，抛出异常
        throw new DrawException(ResultCodeEnum.PARAM_ERROR);
    }
}