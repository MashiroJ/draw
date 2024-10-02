package com.mashiro.utils.Knife4jConfiguration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Knife4jConfiguration {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Draw项目API")
                        .version("1.0")
                        .description("Draw项目的接口文档"));
    }
    
    @Bean
    public GroupedOpenApi userAPI() {
        return GroupedOpenApi.builder().group("用户信息管理").
                pathsToMatch("/system/user/**").
                build();
    }

    @Bean
    public GroupedOpenApi roleAPI() {
        return GroupedOpenApi.builder().group("角色信息管理").
                pathsToMatch("/system/role/**").
                build();
    }
    @Bean
    public GroupedOpenApi loginApi() {
        return GroupedOpenApi.builder().group("登陆相关管理").
                pathsToMatch("/login/**").
                build();
    }
    @Bean
    public GroupedOpenApi fileApi() {
        return GroupedOpenApi.builder().group("文件管理").
                pathsToMatch("/file/**").
                build();
    }
}