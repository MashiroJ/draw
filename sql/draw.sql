-- MySQL dump for the integrated draw database

-- 删除已存在的表
DROP TABLE IF EXISTS `sys_user_role`;
DROP TABLE IF EXISTS `sys_role_menu`;
DROP TABLE IF EXISTS `sys_user`;
DROP TABLE IF EXISTS `sys_role`;
DROP TABLE IF EXISTS `sys_menu`;

-- 创建表
CREATE TABLE `sys_user`
(
    `id`          int                                     NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username`    varchar(50) COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '用户名',
    `password`    varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
    `phone`       varchar(20) COLLATE utf8mb4_unicode_ci  DEFAULT NULL COMMENT '手机号',
    `avatar_url`  varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头像URL',
    `status`      tinyint                                 DEFAULT '1' COMMENT '用户状态：1正常，0禁用',
    `create_time` datetime                                DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime                                DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`  tinyint                                 DEFAULT '0' COMMENT '是否删除：0未删除，1已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `username` (`username`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='系统用户表';

CREATE TABLE `sys_role`
(
    `id`          int                                    NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    `name`        varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色名称',
    `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '角色描述',
    `status`      tinyint                                 DEFAULT '1' COMMENT '角色状态：1正常，0禁用',
    `create_time` datetime                                DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime                                DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`  tinyint                                 DEFAULT '0' COMMENT '是否删除：0未删除，1已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `name` (`name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='系统角色表';

CREATE TABLE `sys_menu`
(
    `id`          int                                    NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
    `name`        varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '菜单名称',
    `path`        varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '菜单路径',
    `parent_id`   int                                     DEFAULT NULL COMMENT '父菜单ID，根菜单为NULL',
    `icon`        varchar(50) COLLATE utf8mb4_unicode_ci  DEFAULT NULL COMMENT '菜单图标',
    `sort_order`  int                                     DEFAULT '0' COMMENT '排序号',
    `permission`  varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '权限标识',
    `status`      tinyint                                 DEFAULT '1' COMMENT '菜单状态：1正常，0禁用',
    `is_visible`  tinyint                                 DEFAULT '1' COMMENT '是否可见：1可见，0不可见',
    `create_time` datetime                                DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime                                DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`  tinyint                                 DEFAULT '0' COMMENT '是否删除：0未删除，1已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='系统菜单表';

CREATE TABLE `sys_user_role`
(
    `id`          int NOT NULL AUTO_INCREMENT COMMENT '关联ID',
    `user_id`     int NOT NULL COMMENT '用户ID',
    `role_id`     int NOT NULL COMMENT '角色ID',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`  tinyint  DEFAULT '0' COMMENT '是否删除：0未删除，1已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='用户角色关联表';

CREATE TABLE `sys_role_menu`
(
    `id`          int NOT NULL AUTO_INCREMENT COMMENT '关联ID',
    `role_id`     int NOT NULL COMMENT '角色ID',
    `menu_id`     int NOT NULL COMMENT '菜单ID',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`  tinyint  DEFAULT '0' COMMENT '是否删除：0未删除，1已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='角色菜单关联表';

-- 插入数据
INSERT INTO `sys_user`
VALUES (1, 'admin', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '15205986026',
        'https://avatars.githubusercontent.com/u/46013989?v=4&size=64', 1, '2024-10-31 13:40:49', '2024-10-31 13:40:49',
        0),
       (2, 'user', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '13800000001',
        'https://avatars.githubusercontent.com/u/46013989?v=4&size=64', 1, '2024-10-31 13:40:49', '2024-10-31 13:40:49',
        0),
       (3, 'member', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '15759698816',
        'https://avatars.githubusercontent.com/u/46013989?v=4&size=64', 1, '2024-10-31 13:40:49', '2024-10-31 13:40:49',
        0),
       (4, 'alice', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '13900000001',
        'https://avatars.githubusercontent.com/u/46013989?v=4&size=64', 1, '2024-10-31 17:15:00', '2024-10-31 17:15:00',
        0),
       (5, 'bob', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '14000000001',
        'https://avatars.githubusercontent.com/u/46013989?v=4&size=64', 1, '2024-10-31 17:15:00', '2024-10-31 17:15:00',
        0),
       (6, 'charlie', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '14100000001',
        'https://avatars.githubusercontent.com/u/46013989?v=4&size=64', 1, '2024-10-31 17:15:00', '2024-10-31 17:15:00',
        0),
       (7, 'gj504b', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '15205986026',
        'https://avatars.githubusercontent.com/u/46013989?v=4&size=64', 1, '2024-10-31 17:15:00', '2024-10-31 17:15:00',
        0),
       (8, 'david', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '14200000001',
        'https://avatars.githubusercontent.com/u/46013989?v=4&size=64', 1, '2024-10-31 17:15:00', '2024-10-31 17:15:00',
        0),
       (9, 'eve', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '14300000001',
        'https://avatars.githubusercontent.com/u/46013989?v=4&size=64', 1, '2024-10-31 17:15:00', '2024-10-31 17:15:00',
        0);

INSERT INTO `sys_role`
VALUES (1, '管理员', '拥有所有权限的角色', 1, '2024-10-31 13:40:49', '2024-10-31 13:40:49', 0),
       (2, '普通用户', '具有普通用户权限的角色', 1, '2024-10-31 13:40:49', '2024-10-31 13:40:49', 0),
       (3, '会员用户', '具有会员权限的角色', 1, '2024-10-31 13:40:49', '2024-10-31 13:40:49', 0);

INSERT INTO `sys_menu`
VALUES (1, '主页', '/home', 0, 'HomeFilled', 1, 'dashboard', 1, 1, '2024-10-31 13:40:49', '2024-10-31 13:40:49', 0),
       (2, '系统管理', '/system', 0, 'Setting', 2, 'system:manage', 1, 1, '2024-10-31 13:40:49', '2024-10-31 13:40:49',
        0),
       (3, '绘画梦工厂', '/draw', 0, 'Brush', 3, 'draw:manage', 1, 1, '2024-10-31 13:40:49', '2024-10-31 13:40:49', 0),
       (4, '用户管理', '/user', 2, 'User', 4, 'user:manage', 1, 1, '2024-10-31 13:40:49', '2024-10-31 13:40:49', 0),
       (5, '角色管理', '/role', 2, 'User', 5, 'role:manage', 1, 1, '2024-10-31 13:40:49', '2024-10-31 13:40:49', 0),
       (6, '菜单管理', '/menu', 2, 'Menu', 6, 'menu:manage', 1, 1, '2024-10-31 13:40:49', '2024-10-31 13:40:49', 0),
       (7, '画廊', '/gallery', 0, 'Gallery', 7, 'gallery:view', 1, 1, '2024-10-31 13:40:49', '2024-10-31 13:40:49', 0),
       (8, '文本转图片', '/text2img', 0, 'Text', 8, 'text2img:convert', 1, 1, '2024-10-31 13:40:49',
        '2024-10-31 13:40:49', 0),
       (9, '图片转图片', '/img2img', 0, 'Image', 9, 'img2img:convert', 1, 1, '2024-10-31 13:40:49',
        '2024-10-31 13:40:49', 0),
       (10, '404', '/404', 0, 'Error', 10, 'error:view', 1, 1, '2024-10-31 13:40:49', '2024-10-31 13:40:49', 0);

INSERT INTO `sys_user_role`
VALUES (1, 1, 1, NULL, NULL, 0),
       (2, 2, 2, NULL, NULL, 0),
       (3, 3, 3, NULL, NULL, 0),
       (4, 4, 2, NULL, NULL, 0),
       (5, 5, 2, NULL, NULL, 0),
       (6, 6, 3, NULL, NULL, 0),
       (7, 7, 3, NULL, NULL, 0);

INSERT INTO `sys_role_menu`
VALUES (1, 1, 1, NULL, NULL, 0),
       (2, 1, 2, NULL, NULL, 0),
       (3, 1, 3, NULL, NULL, 0),
       (4, 1, 4, NULL, NULL, 0),
       (5, 1, 5, NULL, NULL, 0),
       (6, 1, 6, NULL, NULL, 0),
       (7, 1, 7, NULL, NULL, 0),
       (8, 1, 8, NULL, NULL, 0),
       (9, 1, 9, NULL, NULL, 0),
       (10, 1, 10, NULL, NULL, 0),
       (11, 2, 4, NULL, NULL, 0),
       (12, 2, 5, NULL, NULL, 0),
       (13, 3, 6, NULL, NULL, 0);
