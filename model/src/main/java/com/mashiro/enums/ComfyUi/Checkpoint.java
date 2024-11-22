package com.mashiro.enums.ComfyUi;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.mashiro.enums.BaseEnum;
import com.mashiro.exception.DrawException;
import com.mashiro.result.ResultCodeEnum;

public enum Checkpoint implements BaseEnum {
    AOM3A1B(1, "AOM3A1B_orangemixs.safetensors"),
    COUNTERFEIT(2, "Counterfeit-V2.5_pruned.safetensors");

    @EnumValue
    private Integer code;
    @JsonValue
    private String name;

    Checkpoint(Integer code, String name) {
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
     * 根据检查点代码返回对应的Checkpoint枚举实例
     * 如果找不到匹配的检查点代码，则抛出参数错误的异常
     *
     * @param code 检查点代码，用于识别特定的检查点
     * @return 对应检查点代码的Checkpoint枚举实例
     * @throws DrawException 如果检查点代码不存在，则抛出此异常
     */
    public static Checkpoint fromCode(Integer code) {
        for (Checkpoint checkpoint : values()) {
            if (checkpoint.getCode().equals(code)) {
                return checkpoint;
            }
        }
        throw new DrawException(ResultCodeEnum.PARAM_ERROR);
    }
}