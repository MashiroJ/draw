package com.mashiro.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.mashiro.constant.RedisConstant;
import com.mashiro.dto.LoginDto;
import com.mashiro.entity.User;
import com.mashiro.enums.BaseStatus;
import com.mashiro.exception.DrawException;
import com.mashiro.mapper.UserMapper;
import com.mashiro.result.Result;
import com.mashiro.result.ResultCodeEnum;
import com.mashiro.service.LoginService;
import com.mashiro.vo.CaptchaVo;
import com.wf.captcha.SpecCaptcha;
import jakarta.annotation.Resource;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    
    @Resource
    private UserMapper userMapper;

    @Override
    public CaptchaVo getCaptcha() {
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 4);
        String value = specCaptcha.text().toLowerCase(); // 获取验证码文本内容
        String key = RedisConstant.SYSTEM_LOGIN_PREFIX + UUID.randomUUID();   // 生成随机key
        stringRedisTemplate.opsForValue().set(key, value, RedisConstant.SYSTEM_LOGIN_CAPTCHA_TTL_SEC, TimeUnit.SECONDS);
        return new CaptchaVo(specCaptcha.toBase64(), key);
    }

    @Override
    public void login(LoginDto loginDto) {
        // 校验验证码是否存在
        if (loginDto.getCaptchaCode()==null){
            throw new DrawException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_NOT_FOUND);
        }
        // 校验验证码是否过期
        String value = stringRedisTemplate.opsForValue().get(loginDto.getCaptchaKey());
        if (value==null){
            throw new DrawException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_EXPIRED);
        }
        // 校验验证码是否正确
        if (!value.equals(loginDto.getCaptchaCode().toLowerCase())){
            throw new DrawException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_ERROR);
        }

        // 校验账号是否存在
        User user  = userMapper.selectOneByUsername(loginDto.getUsername());
        if (user ==null){
            throw new DrawException(ResultCodeEnum.ADMIN_ACCOUNT_NOT_EXIST_ERROR);
        }
        // 校验账号状态
        if ((user.getStatus() == BaseStatus.DISABLE)){
            throw new DrawException(ResultCodeEnum.ADMIN_ACCOUNT_DISABLED_ERROR);
        }
        // 校验密码是否正确
        if(!user.getPassword().equals(SaSecureUtil.sha256(loginDto.getPassword()))){
            throw new DrawException(ResultCodeEnum.ADMIN_ACCOUNT_ERROR);
        }
        StpUtil.login(user.getId());
    }
}
