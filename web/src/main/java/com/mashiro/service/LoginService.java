package com.mashiro.service;

import com.mashiro.dto.LoginDto;
import com.mashiro.vo.CaptchaVo;

public interface LoginService {

    CaptchaVo getCaptcha();

     void login(LoginDto loginDto);
}
