package com.mashiro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mashiro.entity.Menu;

import java.util.List;

/**
 * @author mashiro
 * @description 针对表【sys_menu(系统菜单表)】的数据库操作Service
 * @createDate 2024-09-29 11:38:42
 */
public interface MenuService extends IService<Menu> {

    List<Menu> queryListMenu();
}
