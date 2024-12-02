package com.mashiro.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashiro.constant.UserConstant;
import com.mashiro.dto.RegisterDto;
import com.mashiro.entity.User;
import com.mashiro.enums.BaseRole;
import com.mashiro.enums.BaseStatus;
import com.mashiro.exception.DrawException;
import com.mashiro.mapper.MenuMapper;
import com.mashiro.mapper.RoleMapper;
import com.mashiro.mapper.RoleMenuMapper;
import com.mashiro.mapper.UserMapper;
import com.mashiro.result.ResultCodeEnum;
import com.mashiro.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mashiro.constant.UserConstant.DEFAULT_MUSER_POINTS;
import static com.mashiro.constant.UserConstant.DEFAULT_USER_POINTS;

/**
 * @author mashiro
 * @description 针对表【sys_user(系统用户表)】的数据库操作Service实现
 * @createDate 2024-09-24 22:13:09
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RoleMenuMapper roleMenuMapper;
    @Resource
    private MenuMapper menuMapper;

    @Override
    public User register(RegisterDto registerDto) {
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
        return user;
    }

    @Override
    public void removeRole(Long userId, BaseRole roleId) {
        // 取消用户授权角色，为用户授权默认角色
        roleMapper.removeRole(userId, roleId);
        roleMapper.grantRoleByid(userId, BaseRole.USER);
    }

    /**
     * 通过用户ID获取角色ID
     *
     * @param userId
     * @return
     */
    @Override
    public List<Long> getRoleIdsByUserId(Long userId) {
        return roleMapper.getRoleByUserId(userId);
    }

    /**
     * 通过用户ID获取菜单ID
     *
     * @param userId
     * @return
     */

    @Override
    @Transactional
    public Map<String, Object> getMenuIdsByUserId(Long userId) {
        // 通过用户ID获取用户相关联的角色ID
        List<Long> roleByUserId = roleMapper.getRoleByUserId(userId);
        log.info("通过用户ID获取用户相关联的角色ID：" + roleByUserId);

        // 初始化结果Map
        Map<String, Object> result = new HashMap<>();
        List<Long> allRoleMenuIds = new ArrayList<>();

        // 通过角色ID获取相关联的菜单
        for (Long roleId : roleByUserId) {
            if (roleId == 1) {
                List<Long> roleMenuIds = roleMenuMapper.findSysRoleMenuByRoleId(roleId);
                List<Long> MenuIds = roleMenuIds.stream().limit(9).toList();
                allRoleMenuIds.addAll(MenuIds);
            } else if (roleId == 2) {
                List<Long> roleMenuIds = roleMenuMapper.findSysRoleMenuByRoleId(roleId);
                List<Long> MenuIds = roleMenuIds.stream().limit(4).toList();
                allRoleMenuIds.addAll(MenuIds);
            } else if (roleId == 3) {
                List<Long> roleMenuIds = roleMenuMapper.findSysRoleMenuByRoleId(roleId);
                List<Long> MenuIds = roleMenuIds.stream().limit(6).toList();
                allRoleMenuIds.addAll(MenuIds);
            }
        }
        // 将所有菜单ID存储到Map中进行返回
        result.put("roleMenuIds", allRoleMenuIds);
        return result;
    }

    /**
     * 为用户分配角色
     *
     * @param userId
     * @param roleId
     */

    @Override
    @Transactional
    public void grantRole(long userId, BaseRole roleId) {
        // 删除之前用户所对应的角色数据
        roleMapper.deleteByUserId(userId);
        // 查询当前用户的积分
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new DrawException(ResultCodeEnum.USER_NOT_FOUND);
        }
        Integer points = user.getPoints();
        Integer code = roleId.getCode();
        if (code == 1) {
            return;
        } else if (code == 2) {
            Integer addPoint = points + DEFAULT_USER_POINTS;
            userMapper.addPoint(userId, addPoint);
        } else if (code == 3) {
            Integer addPoint = points + DEFAULT_MUSER_POINTS;
            userMapper.addPoint(userId, addPoint);
        }
        // 分配新的角色数据
        roleMapper.grantRoleByid(userId, roleId);
    }

    /**
     * 通过用户ID获取权限标识
     *
     * @param userId
     * @return
     */
    @Override
    public List<String> getPermissionsByUserId(Long userId) {
        try {
            // 获取用户关联的角色ID列表
            List<Long> roleIds = roleMapper.getRoleByUserId(userId);
            log.debug("获取到的用户角色ID: " + roleIds);

            // 检查角色ID列表是否为空
            if (roleIds.isEmpty()) {
                log.warn("用户ID: " + userId + " 关联的角色列表为空");
                return new ArrayList<>();
            }

            // 初始化菜单ID列表
            List<Long> menuIds = new ArrayList<>();

            // 通过角色ID获取菜单ID列表
            for (Long roleId : roleIds) {
                List<Long> roleMenuIds = roleMenuMapper.findSysRoleMenuByRoleId(roleId);
                log.debug("角色ID " + roleId + " 关联的菜单ID: " + roleMenuIds);
                menuIds.addAll(roleMenuIds);
            }

            // 检查菜单ID列表是否为空
            if (menuIds.isEmpty()) {
                log.warn("用户ID: " + userId + " 关联的菜单列表为空");
                return new ArrayList<>();
            }

            // 通过菜单ID列表获取权限标识
            List<String> permissions = menuMapper.getPermissionsByMenuIds(menuIds);
            log.debug("获取到的权限标识: " + permissions);

            return permissions;
        } catch (Exception e) {
            log.error("获取用户权限时发生错误，用户ID: " + userId, e);
            throw e; // 或者根据需要处理异常
        }
    }
}





