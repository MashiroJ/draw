package com.mashiro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mashiro.dto.RegisterDto;
import com.mashiro.entity.User;
import com.mashiro.enums.BaseRole;

import java.util.List;
import java.util.Map;

/**
* @author mashiro
* @description 针对表【sys_user(系统用户表)】的数据库操作Service
* @createDate 2024-09-24 22:13:09
*/
public interface UserService extends IService<User> {
    User register(RegisterDto registerDto);


    void removeRole(Long userId, BaseRole roleId);

    List<Long> getRoleIdsByUserId(Long userId);

    Map<String, Object> getMenuIdsByUserId(Long userId);


    void grantRole(long userId, BaseRole roleId);

    List<String> getPermissionsByUserId(Long userId);
}
