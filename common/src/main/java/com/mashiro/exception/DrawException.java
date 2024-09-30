package com.mashiro.exception;

import com.mashiro.result.ResultCodeEnum;
import com.mashiro.result.ResultCodeEnum;
import lombok.Data;

/**
 * @ClassName: LeaseException
 * @Author: Mashiro
 * @CreateDate: 2024/9/30
 * @CreateTime: 下午2:40
 * @Description: 定义异常
 **/
@Data
public class DrawException extends RuntimeException{
    private Integer code;

    public DrawException(ResultCodeEnum resultCodeEnum){
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }
}
