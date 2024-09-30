package com.mashiro.exception;

import com.mashiro.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName: GlobalExceptionHandler
 * @Author: Mashiro
 * @CreateDate: 2024/09/30
 * @CreateTime: 下午14:38
 * @Description: 全局异常处理
 **/

@ControllerAdvice       //用于声明处理全局Controller方法异常的类
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)      //用于声明处理异常的方法，`value`属性用于声明该方法处理的异常类型
    @ResponseBody       //表示将方法的返回值作为HTTP的响应体
    public Result error(Exception e) {
        e.printStackTrace();
        return Result.error();
    }

    @ExceptionHandler(DrawException.class)
    @ResponseBody
    public Result error(DrawException e) {
        e.printStackTrace();
        return Result.error(e.getCode(), e.getMessage());
    }
}
