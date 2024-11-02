package com.mashiro.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdatePasswordDto {
    @NotBlank(message = "请输入旧密码")
    private String oldPassword;
    @NotBlank(message = "请输入新密码")
    @Size(min = 5, max = 16, message = "新密码长度至少为5位")
    private String newPassword;
    @NotBlank(message = "请再次输入新密码")
    @Size(min = 5, max = 16, message = "新密码长度至少为5位")
    private String confirmPassword;
}
