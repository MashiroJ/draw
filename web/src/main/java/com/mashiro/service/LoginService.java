package com.mashiro.service;

import cn.dev33.satoken.util.SaResult;
import com.mashiro.dto.LoginDto;
import com.mashiro.vo.CaptchaVo;

public interface LoginService {

    CaptchaVo getCaptcha();


    SaResult login(LoginDto loginDto);
}
