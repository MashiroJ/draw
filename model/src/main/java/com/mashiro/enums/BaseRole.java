package com.mashiro.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.mashiro.exception.DrawException;
import com.mashiro.result.ResultCodeEnum;


public enum BaseRole implements BaseEnum {

    ADMIN(1, "管理员"),
    USER(2, "普通用户"),
    MUSER(3, "会员用户");


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

    /**
     * 根据角色代码返回对应的BaseRole枚举实例
     * 如果找不到匹配的角色代码，则抛出参数错误的异常
     *
     * @param code 角色代码，用于识别特定的角色
     * @return 对应角色代码的BaseRole枚举实例
     * @throws DrawException 如果角色代码不存在，则抛出此异常
     */
    public static BaseRole fromCode(Integer code) {
        // 遍历枚举中的所有角色
        for (BaseRole role : values()) {
            // 检查当前角色的代码是否与传入的代码匹配
            if (role.getCode().equals(code)) {
                // 如果匹配，则返回当前角色实例
                return role;
            }
        }
        // 如果没有找到匹配的角色代码，抛出异常
        throw new DrawException(ResultCodeEnum.PARAM_ERROR);
    }
}
