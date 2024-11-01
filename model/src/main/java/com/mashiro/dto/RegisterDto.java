package com.mashiro.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "后台管理系统登录信息")
public class RegisterDto {

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "密码")
    private String password;
}
