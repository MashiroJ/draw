-- sys_user
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `id`          int                                                           NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '用户名',
    `password`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
    `phone`       varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  DEFAULT NULL COMMENT '手机号',
    `avatar_url`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头像URL',
    `status`      tinyint                                                       DEFAULT '1' COMMENT '用户状态：1正常，0禁用',
    `create_time` datetime                                                      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime                                                      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`  tinyint                                                       DEFAULT '0' COMMENT '是否删除：0未删除，1已删除',
    `points`      int                                                           DEFAULT NULL COMMENT '用户积分',
    PRIMARY KEY (`id`),
    UNIQUE KEY `username` (`username`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 11
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='系统用户表';

INSERT INTO `sys_user`
VALUES (1, 'admin', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '15759698816',
        'http://106.55.168.194:9000/draw/common/603c96d5-902c-4d43-9d1b-ef48f1ef41ca-fhj.png', 1, '2024-10-31 13:40:49',
        '2024-11-25 20:50:36', 0, NULL),
       (2, 'user', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '13800000001',
        'http://106.55.168.194:9000/draw/common/41d44560-c015-4a1d-85a9-02758d04bbdb-少女头像.png', 1,
        '2024-10-31 13:40:49', '2024-11-25 20:50:45', 0, 10),
       (3, 'member', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '13800000002',
        'http://106.55.168.194:9000/draw/common/69e113c0-1cc4-48e3-a942-add12d11700f-user.png', 1,
        '2024-10-31 13:40:49', '2024-11-25 20:50:57', 0, 100),
       (4, 'alice', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '13800000003',
        'https://avatars.githubusercontent.com/u/46013989?v=4&size=64', 1, '2024-10-31 17:15:00', '2024-11-25 20:30:27',
        0, 10),
       (5, 'bob', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '13800000004',
        'https://avatars.githubusercontent.com/u/46013989?v=4&size=64', 1, '2024-10-31 17:15:00', '2024-11-23 20:49:14',
        0, 10),
       (6, 'charlie', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '13800000005',
        'https://avatars.githubusercontent.com/u/46013989?v=4&size=64', 1, '2024-10-31 17:15:00', '2024-11-23 20:49:14',
        0, 10),
       (7, 'gj504b', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '15205986026',
        'http://106.55.168.194:9000/draw/common/f6dd168d-7d3f-4aa6-8619-799fd9a4d7ff-zl.png', 1, '2024-10-31 17:15:00',
        '2024-11-25 23:02:44', 0, 10);

-- sys_role
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `id`          int                                                          NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    `name`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色名称',
    `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '角色描述',
    `status`      tinyint                                                       DEFAULT '1' COMMENT '角色状态：1正常，0禁用',
    `create_time` datetime                                                      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime                                                      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`  tinyint                                                       DEFAULT '0' COMMENT '是否删除：0未删除，1已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `name` (`name`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 10
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='系统角色表';

INSERT INTO `sys_role`
VALUES (1, '管理员', '拥有所有权限的角色', 1, '2024-10-31 13:40:49', '2024-11-25 20:51:22', 0),
       (2, '普通用户', '具有普通用户权限的角色', 1, '2024-10-31 13:40:49', '2024-11-02 00:28:46', 0),
       (3, '会员用户', '具有会员权限的角色', 1, '2024-10-31 13:40:49', '2024-11-25 20:51:29', 0);

-- sys_menu
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`
(
    `id`          int                                                          NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
    `name`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '菜单名称',
    `path`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '菜单路径',
    `icon`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  DEFAULT NULL COMMENT '菜单图标',
    `sort_order`  int                                                           DEFAULT '0' COMMENT '排序号',
    `permission`  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '权限标识',
    `status`      tinyint                                                       DEFAULT '1' COMMENT '菜单状态：1正常，0禁用',
    `is_visible`  tinyint                                                       DEFAULT '1' COMMENT '是否可见：1可见，0不可见',
    `create_time` datetime                                                      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime                                                      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`  tinyint                                                       DEFAULT '0' COMMENT '是否删除：0未删除，1已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 13
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='系统菜单表';

INSERT INTO `sys_menu`
VALUES (1, '个人中心', '/', 'HomeFilled', 1, 'dashboard', 1, 1, '2024-10-31 13:40:49', '2024-11-20 15:53:50', 0),
       (2, '用户管理', '/user', 'User', 2, 'user:manage', 1, 1, '2024-10-31 13:40:49', '2024-11-20 15:32:54', 0),
       (3, '角色管理', '/role', 'Key', 3, 'role:manage', 1, 1, '2024-10-31 13:40:49', '2024-11-20 15:32:54', 0),
       (4, '菜单管理', '/menu', 'Menu', 4, 'menu:manage', 1, 1, '2024-10-31 13:40:49', '2024-11-20 15:33:04', 0),
       (5, '创艺馆', '/gallery', 'Picture', 5, 'gallery:view', 1, 1, '2024-10-31 13:40:49', '2024-11-25 19:18:33', 0),
       (6, '文本转图片', '/text2img', 'Postcard', 6, 'text2img:convert', 1, 1, '2024-10-31 13:40:49', '2024-11-20 15:33:04',
        0),
       (7, '图片转图片', '/img2img', 'Switch', 7, 'img2img:convert', 1, 1, '2024-10-31 13:40:49', '2024-11-20 15:33:11',
        0),
       (8, '超级文生图', '/superText2Img', 'ScaleToOriginal', 8, 'supertext2img:convert', 1, 1, '2024-11-23 14:01:32',
        '2024-11-23 17:15:16', 0),
       (9, '超级图生图', '/superImg2Img', 'MagicStick', 9, 'superimg2img:convert', 1, 1, '2024-11-23 17:16:00',
        '2024-11-23 17:20:01', 0);

-- sys_user_role
DROP TABLE IF EXISTS `sys_user_role`;
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
  AUTO_INCREMENT = 13
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='用户角色关联表';

INSERT INTO `sys_user_role`
VALUES (1, 1, 1, '2024-10-31 13:40:49', '2024-10-31 13:40:49', 0),
       (2, 2, 2, '2024-11-25 20:50:45', '2024-11-25 20:50:45', 0),
       (3, 3, 3, '2024-10-31 13:40:49', '2024-10-31 13:40:49', 0),
       (4, 4, 2, '2024-11-25 20:30:27', '2024-11-25 20:30:27', 0),
       (5, 5, 2, '2024-10-31 13:40:49', '2024-10-31 13:40:49', 0),
       (6, 6, 2, '2024-10-31 13:40:49', '2024-10-31 13:40:49', 0),
       (7, 7, 2, '2024-10-31 13:40:49', '2024-10-31 13:40:49', 0);

-- sys_role_menu
DROP TABLE IF EXISTS `sys_role_menu`;
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
  AUTO_INCREMENT = 46
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='角色菜单关联表';

-- 为管理员分配菜单
INSERT INTO `sys_role_menu`
VALUES (1, 1, 1, '2024-11-20 15:45:34', '2024-11-25 20:51:13', 0),
       (2, 1, 2, '2024-11-20 15:45:34', '2024-11-25 20:51:13', 0),
       (3, 1, 3, '2024-11-20 15:45:34', '2024-11-25 20:51:13', 0),
       (4, 1, 4, '2024-11-20 15:45:34', '2024-11-25 20:51:13', 0),
       (5, 1, 5, '2024-11-20 15:45:34', '2024-11-25 20:51:13', 0),
       (6, 1, 6, '2024-11-20 15:45:34', '2024-11-25 20:51:13', 0),
       (7, 1, 7, '2024-11-20 15:45:34', '2024-11-25 20:51:13', 0),
       (8, 1, 8, '2024-11-20 15:45:34', '2024-11-25 20:51:13', 0),
       (9, 1, 9, '2024-11-20 15:45:34', '2024-11-25 20:51:13', 0);

-- 为普通用户分配菜单
INSERT INTO `sys_role_menu`
VALUES (10, 2, 1, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0),
       (11, 2, 5, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0),
       (12, 2, 6, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0),
       (13, 2, 7, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0),
       (14, 2, 8, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0),
       (15, 2, 9, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0);

-- 为会员用户分配菜单
INSERT INTO `sys_role_menu`
VALUES (16, 3, 1, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0),
       (17, 3, 5, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0),
       (18, 3, 6, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0),
       (19, 3, 7, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0),
       (20, 3, 8, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0),
       (21, 3, 9, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0);

-- 系统管理权限
INSERT INTO `sys_menu`
VALUES
-- 用户管理权限
(20, '用户查看', '/user/view', 'User', 20, 'sysUser:view', 1, 1, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0),
(21, '用户添加', '/user/add', 'User', 21, 'sysUser:add', 1, 1, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0),
(22, '用户编辑', '/user/edit', 'User', 22, 'sysUser:edit', 1, 1, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0),
(23, '用户删除', '/user/delete', 'User', 23, 'sysUser:delete', 1, 1, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0),

-- 角色管理权限
(24, '角色查看', '/role/view', 'User', 24, 'sysRole:view', 1, 1, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0),
(25, '角色添加', '/role/add', 'User', 25, 'sysRole:add', 1, 1, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0),
(26, '角色编辑', '/role/edit', 'User', 26, 'sysRole:edit', 1, 1, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0),
(27, '角色删除', '/role/delete', 'User', 27, 'sysRole:delete', 1, 1, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0),

-- 菜单管理权限
(28, '菜单查看', '/menu/view', 'Menu', 28, 'sysMenu:view', 1, 1, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0),
(29, '菜单添加', '/menu/add', 'Menu', 29, 'sysMenu:add', 1, 1, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0),
(30, '菜单编辑', '/menu/edit', 'Menu', 30, 'sysMenu:edit', 1, 1, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0),
(31, '菜单删除', '/menu/delete', 'Menu', 31, 'sysMenu:delete', 1, 1, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0),

-- 绘画功能权限
(32, '创艺馆查看', '/gallery/view', 'Gallery', 32, 'gallery:view', 1, 1, '2024-11-26 10:00:00', '2024-11-26 10:00:00',
 0),
(33, '创艺馆删除', '/gallery/delete', 'Gallery', 33, 'gallery:delete', 1, 1, '2024-11-26 10:00:00',
 '2024-11-26 10:00:00', 0),

-- 基础绘图权限
(34, '文生图基础', '/text2img/basic', 'Text', 34, 'text2img:basic', 1, 1, '2024-11-26 10:00:00', '2024-11-26 10:00:00',
 0),
(35, '图生图基础', '/img2img/basic', 'Image', 35, 'img2img:basic', 1, 1, '2024-11-26 10:00:00', '2024-11-26 10:00:00',
 0),

-- 高级绘图权限
(36, '超级文生图', '/text2img/advanced', 'Text', 36, 'text2img:advanced', 1, 1, '2024-11-26 10:00:00',
 '2024-11-26 10:00:00', 0),
(37, '超级图生图', '/img2img/advanced', 'Image', 37, 'img2img:advanced', 1, 1, '2024-11-26 10:00:00',
 '2024-11-26 10:00:00', 0),

-- 社交功能权限
(38, '评论创建', '/comment/create', 'Comment', 38, 'comment:create', 1, 1, '2024-11-26 10:00:00', '2024-11-26 10:00:00',
 0),
(39, '评论删除', '/comment/delete', 'Comment', 39, 'comment:delete', 1, 1, '2024-11-26 10:00:00', '2024-11-26 10:00:00',
 0),
(40, '点赞操作', '/like/operate', 'Like', 40, 'like:operate', 1, 1, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0);

-- 为管理员分配所有权限
INSERT INTO `sys_role_menu`
VALUES
-- 用户管理权限
(100, 1, 20, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0), -- 用户查看
(101, 1, 21, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0), -- 用户添加
(102, 1, 22, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0), -- 用户编辑
(103, 1, 23, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0), -- 用户删除

-- 角色管理权限
(110, 1, 24, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0), -- 角色查看
(111, 1, 25, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0), -- 角色添加
(112, 1, 26, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0), -- 角色编辑
(113, 1, 27, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0), -- 角色删除

-- 菜单管理权限
(120, 1, 28, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0), -- 菜单查看
(121, 1, 29, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0), -- 菜单添加
(122, 1, 30, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0), -- 菜单编辑
(123, 1, 31, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0), -- 菜单删除

-- 绘画功能权限
(130, 1, 32, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0), -- 创艺馆查看
(131, 1, 33, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0), -- 创艺馆删除

-- 基础绘图权限
(140, 1, 34, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0), -- 文生图基础
(141, 1, 35, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0), -- 图生图基础

-- 高级绘图权限
(150, 1, 36, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0), -- 超级文生图
(151, 1, 37, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0), -- 超级图生图

-- 社交功能权限
(160, 1, 38, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0), -- 评论创建
(161, 1, 39, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0), -- 评论删除
(162, 1, 40, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0);
-- 点赞操作

-- 为普通用户分配基础权限
INSERT INTO `sys_role_menu`
VALUES
-- 基础功能权限
(200, 2, 32, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0), -- 创艺馆查看
(201, 2, 33, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0), -- 创艺馆删除
(202, 2, 34, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0), -- 文生图基础
(203, 2, 35, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0), -- 图生图基础

-- 高级功能权限
(210, 2, 36, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0), -- 超级文生图
(211, 2, 37, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0), -- 超级图生图

-- 社交功能权限
(220, 2, 38, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0), -- 评论创建
(221, 2, 40, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0);
-- 点赞操作

-- 为会员用户分配高级权限
INSERT INTO `sys_role_menu`
VALUES
-- 基础功能权限
(300, 3, 32, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0), -- 创艺馆查看
(301, 3, 33, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0), -- 创艺馆删除
(302, 3, 34, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0), -- 文生图基础
(303, 3, 35, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0), -- 图生图基础

-- 高级功能权限
(310, 3, 36, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0), -- 超级文生图
(311, 3, 37, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0), -- 超级图生图

-- 社交功能权限
(320, 3, 38, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0), -- 评论创建
(321, 3, 40, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0);
-- 点赞操作

-- 绘画记录表
DROP TABLE IF EXISTS `draw_record`;
CREATE TABLE `draw_record`
(
    `id`              bigint       NOT NULL AUTO_INCREMENT COMMENT '绘画记录ID',
    `user_id`         int          NOT NULL COMMENT '创建用户ID',
    `task_id`         varchar(255) DEFAULT NULL COMMENT '任务ID',
    `prompt`          text         NOT NULL COMMENT '绘画提示词',
    `negative_prompt` text COMMENT '反向提示词',
    `image_url`       varchar(255) NOT NULL COMMENT '生成图片URL',
    `generation_type` varchar(20)  NOT NULL COMMENT '生成类型：TEXT2IMG/IMG2IMG',
    `work_flow_name`  varchar(255) NOT NULL COMMENT '工作流名称',
    `like_count`      int          DEFAULT '0' COMMENT '点赞数',
    `is_public`       tinyint      DEFAULT '1' COMMENT '是否公开：1公开，0私有',
    `create_time`     datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`      tinyint      DEFAULT '0' COMMENT '是否删除：0未删除，1已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 50
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='绘画记录表';

INSERT INTO `draw_record`
VALUES (1, 1, '1723799430',
        '杰作，质量最好，1个女孩，洛丽，可爱，独唱，美丽细致的眼睛，精致的脸，毛衣，粉红色的头发，侧髻，紫罗兰色的眼睛，高光染色，衬衫，微裙，黑色连裤袜，透视，街道，钟楼，景深，灰色背景着色。',
        'badhandv4, EasyNegative, verybadimagenegative_v1.3,illustration, 3d, sepia, painting, cartoons, sketch, (worst quality:1.74), (low quality:1.74), (normal quality:1.44), lowres, bad anatomy, normal quality, ((monochrome)), ((grayscale)), ((letters)), ((english)), capital.',
        'http://106.55.168.194:9000/draw/user/1/51bbab2d-d9fe-4402-bf58-90290ac1ada3-image_from_url.png', 'TEXT2IMG',
        '2', 2, 1, '2024-11-19 21:20:19', '2024-11-25 21:23:13', 0),
       (2, 2, '1723799431',
        '杰作，质量最好，1个女孩,美丽细致的眼睛，精致的脸，大衣，白色的头发，侧髻，紫罗兰色的眼睛，高光染色，衬衫，微裙，白色连裤袜，透视，街道，景深，白色背景',
        'badhandv4, EasyNegative, verybadimagenegative_v1.3,illustration, 3d, sepia, painting, cartoons, sketch, (worst quality:1.74), (low quality:1.74), (normal quality:1.44), lowres, bad anatomy, normal quality, ((monochrome)), ((grayscale)), ((letters)), ((english)), capital.',
        'http://106.55.168.194:9000/draw/user/2/69533d33-6e37-4d77-abb4-31ec0ae685ab-image_from_url.png', 'TEXT2IMG',
        '2', 0, 1, '2024-11-19 21:22:47', '2024-11-21 20:35:26', 0);


-- 1. 绘画点赞表
DROP TABLE IF EXISTS `draw_like`;

CREATE TABLE `draw_like`
(
    `id`          bigint NOT NULL AUTO_INCREMENT COMMENT '点赞ID',
    `draw_id`     bigint NOT NULL COMMENT '绘画记录ID',
    `user_id`     int    NOT NULL COMMENT '用户ID',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`  tinyint  DEFAULT '0' COMMENT '是否删除：0未删除，1已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `unique_user_draw` (`user_id`, `draw_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_create_time` (`create_time`),
    KEY `idx_user_draw_deleted` (`user_id`, `draw_id`, `is_deleted`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 42
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='绘画点赞表';

INSERT INTO `draw_like`
VALUES (1, 1, 1, '2024-11-19 22:36:52', '2024-11-25 21:20:55', 0),
       (2, 1, 2, '2024-11-19 22:55:14', '2024-11-19 23:13:58', 0);

-- 绘画评论表
DROP TABLE IF EXISTS `draw_comment`;
CREATE TABLE `draw_comment`
(
    `id`            bigint NOT NULL AUTO_INCREMENT COMMENT '评论ID',
    `draw_id`       bigint NOT NULL COMMENT '绘画记录ID',
    `user_id`       int    NOT NULL COMMENT '评论用户ID',
    `content`       text   NOT NULL COMMENT '评论内容',
    `parent_id`     bigint   DEFAULT NULL COMMENT '父评论ID，用于回复功能',
    `reply_user_id` int      DEFAULT NULL COMMENT '被回复的用户ID',
    `like_count`    int      DEFAULT '0' COMMENT '点赞数',
    `create_time`   datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`    tinyint  DEFAULT '0' COMMENT '是否删除：0未删除，1已删除',
    PRIMARY KEY (`id`),
    KEY `idx_draw_id` (`draw_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_parent_id` (`parent_id`),
    KEY `idx_create_time` (`create_time`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='绘画评论表';

-- 评论点赞表
DROP TABLE IF EXISTS `comment_like`;
CREATE TABLE `comment_like`
(
    `id`          bigint NOT NULL AUTO_INCREMENT COMMENT '点赞ID',
    `comment_id`  bigint NOT NULL COMMENT '评论ID',
    `user_id`     int    NOT NULL COMMENT '用户ID',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`  tinyint  DEFAULT '0' COMMENT '是否删除：0未删除，1已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `unique_user_comment` (`user_id`, `comment_id`),
    KEY `idx_comment_id` (`comment_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_create_time` (`create_time`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='评论点赞表';