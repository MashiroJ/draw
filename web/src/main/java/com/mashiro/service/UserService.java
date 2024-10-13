package com.mashiro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mashiro.dto.GrantRoleDto;
import com.mashiro.dto.RegisterDto;
import com.mashiro.entity.User;
import com.mashiro.enums.BaseRole;

/**
* @author mashiro
* @description 针对表【sys_user(系统用户表)】的数据库操作Service
* @createDate 2024-09-24 22:13:09
*/
public interface UserService extends IService<User> {
    void register(RegisterDto registerDto);

    void grantRole(GrantRoleDto grantRoleDto);

    void removeRole(Long userId, Long roleId);
}
