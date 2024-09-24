package com.mashiro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashiro.entity.User;
import com.mashiro.service.UserService;
import com.mashiro.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author mashiro
* @description 针对表【sys_user(系统用户表)】的数据库操作Service实现
* @createDate 2024-09-24 22:13:09
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




