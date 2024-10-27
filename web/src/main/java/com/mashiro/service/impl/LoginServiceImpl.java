package com.mashiro.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.mashiro.constant.RedisConstant;
import com.mashiro.dto.LoginDto;
import com.mashiro.entity.User;
import com.mashiro.enums.BaseStatus;
import com.mashiro.exception.DrawException;
import com.mashiro.mapper.UserMapper;
import com.mashiro.result.ResultCodeEnum;
import com.mashiro.service.LoginService;
import com.mashiro.vo.CaptchaVo;
import com.wf.captcha.ArithmeticCaptcha;
import jakarta.annotation.Resource;
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
        ArithmeticCaptcha specCaptcha = new ArithmeticCaptcha(130, 48);
        specCaptcha.setLen(2);  // 几位数运算，默认是两位
        specCaptcha.getArithmeticString();  // 获取运算的公式：3+2=?
        specCaptcha.text();  // 获取运算的结果：5
        String value = specCaptcha.text().toLowerCase();
        /*
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 4);
        String value = specCaptcha.text().toLowerCase(); // 获取验证码文本内容
         */
        String key = RedisConstant.SYSTEM_LOGIN_PREFIX + UUID.randomUUID();   // 生成随机key
        stringRedisTemplate.opsForValue().set(key, value, RedisConstant.SYSTEM_LOGIN_CAPTCHA_TTL_SEC, TimeUnit.SECONDS);
        return new CaptchaVo(specCaptcha.toBase64(), key);
    }

    @Override
    public SaResult login(LoginDto loginDto) {
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
        // 第1步，先登录上
        StpUtil.login(user.getId());
        // 第2步，获取 Token  相关参数
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        // 第3步，返回给前端
        return SaResult.data(tokenInfo);
    }
}
