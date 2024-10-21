package com.mashiro.result;

import lombok.Data;

/**
 * 全局统一返回结果类
 */
@Data
public class Result<T> {

    //返回码
    private Integer code;

    //返回消息
    private String message;

    //返回数据
    private T data;

    public Result() {
    }

    private static <T> Result<T> build(T data) {
        Result<T> result = new Result<>();
        if (data != null) {
            result.setData(data);
        }
        return result;
    }

    public static <T> Result<T> build(T body, ResultCodeEnum resultCodeEnum) {
        Result<T> result = build(body);
        result.setCode(resultCodeEnum.getCode());
        result.setMessage(resultCodeEnum.getMessage());
        return result;
    }

    public static <T> Result<T> ok() {
        return Result.ok(null);
    }

    public static <T> Result<T> ok(T data) {
        return build(data, ResultCodeEnum.SUCCESS);
    }



    public static <T> Result<T> error() {
        return build(null, ResultCodeEnum.FAIL);
    }

    public static <T> Result<T> error(Integer code,String message) {
        Result<T> result = build(null);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static <T> Result<T> error(String message) {
        Result<T> result = build(null);
        result.setCode(ResultCodeEnum.FAIL.getCode());
        result.setMessage(message);
        return result;
    }
}
