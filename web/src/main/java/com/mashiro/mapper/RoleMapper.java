package com.mashiro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mashiro.entity.Role;

/**
* @author mashiro
* @description 针对表【sys_role(系统角色表)】的数据库操作Mapper
* @createDate 2024-09-24 22:13:09
* @Entity com.mashiro.entity.Role
*/
public interface RoleMapper extends BaseMapper<Role> {

    void deleteByUserId(Long userId);

    void grantRole(Long userId, Long roleId);

    void removeRole(Long userId, Long roleId);

    Long getRoleByUserId(Long userId);
}




