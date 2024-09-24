package com.mashiro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashiro.entity.Role;
import com.mashiro.service.RoleService;
import com.mashiro.mapper.RoleMapper;
import org.springframework.stereotype.Service;

/**
* @author mashiro
* @description 针对表【sys_role(系统角色表)】的数据库操作Service实现
* @createDate 2024-09-24 22:13:09
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

}




