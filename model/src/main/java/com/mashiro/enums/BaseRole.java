package com.mashiro.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;


public enum BaseRole implements BaseEnum {

    ADMIN(1,"管理员"),
    USER(2, "普通用户"),
    MUSER(3,"会员用户");



    @EnumValue
    private Integer code;
    @JsonValue
    private String name;

    BaseRole(Integer code, String name) {
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
