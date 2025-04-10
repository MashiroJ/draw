package com.mashiro.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;


public enum BaseStatus implements BaseEnum {


    ENABLE(1, "正常"),

    DISABLE(0, "禁用");


    @EnumValue
    private Integer code;
    @JsonValue
    private String name;

    BaseStatus(Integer code, String name) {
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
}
