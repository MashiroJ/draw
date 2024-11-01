package com.mashiro.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.util.SaResult;
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

    // 全局异常拦截（拦截项目中的NotLoginException异常）
    @ExceptionHandler(NotLoginException.class)
    public SaResult handlerNotLoginException(NotLoginException nle)
            throws Exception {
        // 打印堆栈，以供调试
        nle.printStackTrace();

        // 判断场景值，定制化异常信息
        String message = "";
        if (nle.getType().equals(NotLoginException.NOT_TOKEN)) {
            message = "未能读取到有效 token";
        } else if (nle.getType().equals(NotLoginException.INVALID_TOKEN)) {
            message = "token 无效";
        } else if (nle.getType().equals(NotLoginException.TOKEN_TIMEOUT)) {
            message = "token 已过期";
        } else if (nle.getType().equals(NotLoginException.BE_REPLACED)) {
            message = "token 已被顶下线";
        } else if (nle.getType().equals(NotLoginException.KICK_OUT)) {
            message = "token 已被踢下线";
        } else if (nle.getType().equals(NotLoginException.TOKEN_FREEZE)) {
            message = "token 已被冻结";
        } else if (nle.getType().equals(NotLoginException.NO_PREFIX)) {
            message = "未按照指定前缀提交 token";
        } else {
            message = "当前会话未登录";
        }

        // 返回给前端
        return SaResult.error(message);
    }

}
