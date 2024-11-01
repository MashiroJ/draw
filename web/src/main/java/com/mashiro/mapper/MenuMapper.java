package com.mashiro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mashiro.entity.Menu;

import java.util.List;

/**
 * @author mashiro
 * @description 针对表【sys_menu(系统菜单表)】的数据库操作Mapper
 * @createDate 2024-09-29 11:38:42
 * @Entity com.mashiro.entity.Menu
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> getPermissionsByMenuIds(List<Long> roleByUserId);
}




