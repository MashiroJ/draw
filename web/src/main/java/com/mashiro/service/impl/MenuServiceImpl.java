package com.mashiro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashiro.entity.Menu;
import com.mashiro.mapper.MenuMapper;
import com.mashiro.service.MenuService;
import org.springframework.stereotype.Service;

/**
* @author mashiro
* @description 针对表【sys_menu(系统菜单表)】的数据库操作Service实现
* @createDate 2024-09-29 11:38:42
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
    implements MenuService{

}




