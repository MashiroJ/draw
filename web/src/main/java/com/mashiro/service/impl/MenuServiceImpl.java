package com.mashiro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashiro.entity.Menu;
import com.mashiro.service.MenuService;
import com.mashiro.mapper.MenuMapper;
import org.springframework.stereotype.Service;

/**
* @author mashiro
* @description 针对表【sys_menu(系统菜单表)】的数据库操作Service实现
* @createDate 2024-09-24 22:13:09
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
    implements MenuService{

}




