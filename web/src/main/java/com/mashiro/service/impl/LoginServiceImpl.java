package com.mashiro.service.impl;

import com.mashiro.constant.RedisConstant;
import com.mashiro.dto.LoginDto;
import com.mashiro.service.LoginService;
import com.mashiro.vo.CaptchaVo;
import com.wf.captcha.SpecCaptcha;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public CaptchaVo getCaptcha() {
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 4);
        String value = specCaptcha.text().toLowerCase(); // 获取验证码文本内容
        String key = RedisConstant.SYSTEM_LOGIN_PREFIX + UUID.randomUUID();   // 生成随机key
        stringRedisTemplate.opsForValue().set(key, value, RedisConstant.SYSTEM_LOGIN_CAPTCHA_TTL_SEC, TimeUnit.SECONDS);
        return new CaptchaVo(specCaptcha.toBase64(), key);
    }
}
