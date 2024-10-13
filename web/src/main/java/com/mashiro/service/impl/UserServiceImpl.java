package com.mashiro.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashiro.constant.UserConstant;
import com.mashiro.dto.GrantRoleDto;
import com.mashiro.dto.RegisterDto;
import com.mashiro.entity.User;
import com.mashiro.enums.BaseStatus;
import com.mashiro.mapper.RoleMapper;
import com.mashiro.mapper.RoleMenuMapper;
import com.mashiro.mapper.UserMapper;
import com.mashiro.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author mashiro
* @description 针对表【sys_user(系统用户表)】的数据库操作Service实现
* @createDate 2024-09-24 22:13:09
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Override
    public void register(RegisterDto registerDto) {
        // 获取注册的用户名、密码
        String username = registerDto.getUsername();
        String password = registerDto.getPassword();
        String sha256 = SaSecureUtil.sha256(password);
        // 插入用户
        User user = new User();
        user.setAvatarUrl(UserConstant.DEFAULT_AVATAR_URL);
        user.setUsername(username);
        user.setPassword(sha256);
        user.setStatus(BaseStatus.ENABLE);
        userMapper.registerUser(user);
    }

    @Override
    public void grantRole(GrantRoleDto grantRoleDto) {
        // 删除之前用户所对应的角色数据
        roleMapper.deleteByUserId(grantRoleDto.getUserId()) ;

        // 分配新的角色数据
        Long roleId = grantRoleDto.getRoleId();
        roleMapper.grantRole(grantRoleDto.getUserId(), roleId);
    }

    @Override
    public void removeRole(Long userId, Long roleId) {
        roleMapper.removeRole(userId, roleId);
    }

    /**
     * 通过用户ID获取角色ID
     *
     * @param userId
     * @return
     */
    @Override
    public Long getRoleIdsByUserId(Long userId) {
        return roleMapper.getRoleByUserId(userId);
    }

    /**
     * 通过用户ID获取菜单ID
     * @param userId
     * @return
     */

    @Override
    public Map<String, Object> getMenuIdsByUserId(Long userId) {
        // 通过用户ID获取用户相关联的角色ID
        Long roleByUserId = roleMapper.getRoleByUserId(userId);
        //通过角色ID获取相关联的菜单
        List<Long> roleMenuIds = roleMenuMapper.findSysRoleMenuByRoleId(roleByUserId);

        // 将数据存储到Map中进行返回
        Map<String , Object> result = new HashMap<>() ;
        result.put("roleMenuIds" , roleMenuIds) ;
        // 返回
        return result;
    }


}




