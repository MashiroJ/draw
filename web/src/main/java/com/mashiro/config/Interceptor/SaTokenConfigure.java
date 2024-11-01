package com.mashiro.config.Interceptor;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import com.mashiro.exception.DrawException;
import com.mashiro.result.ResultCodeEnum;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaInterceptor(handle -> {
                    if (!StpUtil.isLogin()) {
                        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                        if (attributes != null) {
                            HttpServletResponse response = attributes.getResponse();
                            if (response != null) {
                                // 未登录，设置状态码为401
                                response.setStatus(401);
                            }
                        }
                        // 抛出异常以中断请求
                        throw new DrawException(ResultCodeEnum.ADMIN_LOGIN_AUTH);
                    }
                }))
                .addPathPatterns("/**")
                .excludePathPatterns("/system/login/**", "/swagger-ui.html", "/swagger-resources/**", "/webjars/**", "/swagger-ui/**", "/v3/api-docs/**", "/doc.html");
    }
}