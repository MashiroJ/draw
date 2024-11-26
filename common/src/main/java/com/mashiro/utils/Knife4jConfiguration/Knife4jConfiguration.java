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
                pathsToMatch("/system/login/**").
                build();
    }

    @Bean
    public GroupedOpenApi fileApi() {
        return GroupedOpenApi.builder().group("文件相关管理").
                pathsToMatch("/system/file/**").
                build();
    }

    @Bean
    public GroupedOpenApi menuApi() {
        return GroupedOpenApi.builder().group("菜单相关管理").
                pathsToMatch("/system/menu/**").
                build();
    }

    @Bean
    public GroupedOpenApi drawApi() {
        return GroupedOpenApi.builder().group("绘画创作管理").
                pathsToMatch("/system/draw/**").
                build();
    }

    @Bean
    public GroupedOpenApi drawRecordAPI() {
        return GroupedOpenApi.builder().group("绘画记录管理").
                pathsToMatch("/system/draw/record/**").
                build();
    }

    @Bean
    public GroupedOpenApi drawLikeAPI() {
        return GroupedOpenApi.builder().group("绘画点赞管理").
                pathsToMatch("/system/draw/like/**").
                build();
    }

    @Bean
    public GroupedOpenApi superDrawLikeAPI() {
        return GroupedOpenApi.builder().group("超级绘画管理").
                pathsToMatch("/system/super/draw/**").
                build();
    }

    @Bean
    public GroupedOpenApi pointsAPI() {
        return GroupedOpenApi.builder().group("积分管理").
                pathsToMatch("/system/points/**").
                build();
    }

    @Bean
    public GroupedOpenApi commentAPI() {
        return GroupedOpenApi.builder().group("绘画评论管理").
                pathsToMatch("/system/comment/**").
                build();
    }
}