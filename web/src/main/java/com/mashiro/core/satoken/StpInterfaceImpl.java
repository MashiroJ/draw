package com.mashiro.core.satoken;

import cn.dev33.satoken.stp.StpInterface;
import com.mashiro.enums.BaseRole;
import com.mashiro.service.RoleMenuService;
import com.mashiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

import static cn.dev33.satoken.SaManager.log;

@Component
public class StpInterfaceImpl implements StpInterface {

    @Autowired
    private UserService userService;


    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        Long userId = convertToLong(loginId);

        // 使用 userId 获取权限
        List<String> permissions = userService.getPermissionsByUserId(userId);


         log.debug("检索到 loginId 的权限 " + loginId + ": " + permissions);

        return permissions == null ? new ArrayList<>() : permissions;
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        Long userId = convertToLong(loginId);

        List<Long> roleIds = userService.getRoleIdsByUserId(userId);
        List<String> roles = new ArrayList<>();

        for (Long roleId : roleIds) {
            BaseRole role = BaseRole.fromCode(roleId.intValue());
            if (role != null) {
                roles.add(role.getName());
            }
        }

        return roles;
    }

    private Long convertToLong(Object loginId) {
        if (loginId instanceof Long) {
            return (Long) loginId;
        } else if (loginId instanceof String) {
            try {
                return Long.parseLong((String) loginId);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("登录 ID 格式无效：字符串无法解析为 Long", e);
            }
        } else if (loginId instanceof Integer) {
            return ((Integer) loginId).longValue();
        } else if (loginId instanceof Number) {
            return ((Number) loginId).longValue();
        } else {
            throw new IllegalArgumentException("无效的 loginId 类型：必须是 Long、String、Integer 或其他 Number 类型");
        }
    }
}