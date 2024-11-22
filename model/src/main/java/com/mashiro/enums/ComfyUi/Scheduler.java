package com.mashiro.enums.ComfyUi;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.mashiro.enums.BaseEnum;
import com.mashiro.exception.DrawException;
import com.mashiro.result.ResultCodeEnum;

public enum Scheduler implements BaseEnum {
    NORMAL(1, "normal"),
    KARRAS(2, "karras"),
    EXPONENTIAL(3, "exponential"),
    SGM_UNIFORM(4, "sgm_uniform"),
    SIMPLE(5, "simple"),
    DDIM_UNIFORM(6, "ddim_uniform"),
    BETA(7, "beta"),
    LINEAR_QUADRATIC(8, "linear_quadratic");

    @EnumValue
    private Integer code;
    @JsonValue
    private String name;

    Scheduler(Integer code, String name) {
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
     * 根据调度器代码返回对应的Scheduler枚举实例
     * 如果找不到匹配的调度器代码，则抛出参数错误的异常
     *
     * @param code 调度器代码，用于识别特定的调度器
     * @return 对应调度器代码的Scheduler枚举实例
     * @throws DrawException 如果调度器代码不存在，则抛出此异常
     */
    public static Scheduler fromCode(Integer code) {
        for (Scheduler scheduler : values()) {
            if (scheduler.getCode().equals(code)) {
                return scheduler;
            }
        }
        throw new DrawException(ResultCodeEnum.PARAM_ERROR);
    }
}