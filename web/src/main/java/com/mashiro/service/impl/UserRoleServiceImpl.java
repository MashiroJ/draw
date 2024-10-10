package com.mashiro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashiro.entity.UserRole;
import com.mashiro.mapper.UserRoleMapper;
import com.mashiro.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
* @author mashiro
* @description 针对表【sys_user_role(用户角色关联表)】的数据库操作Service实现
* @createDate 2024-09-24 22:13:09
*/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService{

}




