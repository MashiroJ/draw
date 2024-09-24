package com.mashiro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashiro.entity.RoleMenu;
import com.mashiro.service.RoleMenuService;
import com.mashiro.mapper.RoleMenuMapper;
import org.springframework.stereotype.Service;

/**
* @author mashiro
* @description 针对表【sys_role_menu(角色菜单关联表)】的数据库操作Service实现
* @createDate 2024-09-24 22:13:09
*/
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu>
    implements RoleMenuService{

}




