package com.mashiro.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashiro.constant.UserConstant;
import com.mashiro.dto.RegisterDto;
import com.mashiro.entity.User;
import com.mashiro.enums.BaseStatus;
import com.mashiro.service.UserService;
import com.mashiro.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;

/**
* @author mashiro
* @description 针对表【sys_user(系统用户表)】的数据库操作Service实现
* @createDate 2024-09-24 22:13:09
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Resource
    private UserMapper userMapper;
    @Override
    public void register(RegisterDto registerDto) {
        // 获取注册的用户名、密码
        String username = registerDto.getUsername();
        String password = registerDto.getPassword();
        String sha256 = SaSecureUtil.sha256(password);
        // 插入用户
        User user = new User();
        user.setAvatarUrl(UserConstant.DEFAULT_AVATAR_URL);
        user.setUsername(username);
        user.setPassword(sha256);
        user.setStatus(BaseStatus.ENABLE);
        userMapper.registerUser(user);
    }
}




