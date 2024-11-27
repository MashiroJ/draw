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
VALUES (1, '主页', '/', 'HomeFilled', 1, 'dashboard', 1, 1, '2024-10-31 13:40:49', '2024-11-20 15:53:50', 0),
       (2, '用户管理', '/user', 'User', 2, 'user:manage', 1, 1, '2024-10-31 13:40:49', '2024-11-20 15:32:54', 0),
       (3, '角色管理', '/role', 'User', 3, 'role:manage', 1, 1, '2024-10-31 13:40:49', '2024-11-20 15:32:54', 0),
       (4, '菜单管理', '/menu', 'Menu', 4, 'menu:manage', 1, 1, '2024-10-31 13:40:49', '2024-11-20 15:33:04', 0),
       (5, '创艺馆', '/gallery', 'Gallery', 5, 'gallery:view', 1, 1, '2024-10-31 13:40:49', '2024-11-25 19:18:33', 0),
       (6, '文本转图片', '/text2img', 'Text', 6, 'text2img:convert', 1, 1, '2024-10-31 13:40:49', '2024-11-20 15:33:04',
        0),
       (7, '图片转图片', '/img2img', 'Image', 7, 'img2img:convert', 1, 1, '2024-10-31 13:40:49', '2024-11-20 15:33:11',
        0),
       (8, '超级文生图', '/superText2Img', 'Text', 8, 'supertext2img:convert', 1, 1, '2024-11-23 14:01:32',
        '2024-11-23 17:15:16', 0),
       (9, '超级图生图', '/superImg2Img', 'Image', 9, 'superimg2img:convert', 1, 1, '2024-11-23 17:16:00',
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
(201, 2, 34, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0), -- 文生图基础
(202, 2, 35, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0), -- 图生图基础

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
(301, 3, 34, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0), -- 文生图基础
(302, 3, 35, '2024-11-26 10:00:00', '2024-11-26 10:00:00', 0), -- 图生图基础

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
        '2', 4, 1, '2024-11-19 21:20:19', '2024-11-25 21:23:13', 0),
       (2, 2, '1723799431',
        '杰作，质量最好，1个男孩,美丽细致的眼睛，精致的脸，大衣，白色的头发，侧髻，紫罗兰色的眼睛，高光染色，衬衫，微裙，白色连裤袜，透视，街道，景深，白色背景',
        'badhandv4, EasyNegative, verybadimagenegative_v1.3,illustration, 3d, sepia, painting, cartoons, sketch, (worst quality:1.74), (low quality:1.74), (normal quality:1.44), lowres, bad anatomy, normal quality, ((monochrome)), ((grayscale)), ((letters)), ((english)), capital.',
        'http://106.55.168.194:9000/draw/user/2/69533d33-6e37-4d77-abb4-31ec0ae685ab-image_from_url.png', 'TEXT2IMG',
        '2', 0, 1, '2024-11-19 21:22:47', '2024-11-21 20:35:26', 0),
       (3, 7, '1723799436', '杰作，质量最好,一个男孩，白色头发，抱着狗',
        'badhandv4, EasyNegative, verybadimagenegative_v1.3,illustration, 3d, sepia, painting, cartoons, sketch, (worst quality:1.74), (low quality:1.74), (normal quality:1.44), lowres, bad anatomy, normal quality, ((monochrome)), ((grayscale)), ((letters)), ((english)), capital.',
        'http://106.55.168.194:9000/draw/user/7/6f3de578-a8b2-4ff3-9688-dcc6e634db26-image_from_url.png', 'IMG2IMG',
        '3', 0, 1, '2024-11-19 21:24:58', '2024-11-21 20:35:26', 0),
       (4, 4, '1723799433', '杰作，质量最好,一个男孩，长头发，街景，精致的脸庞',
        'badhandv4, EasyNegative, verybadimagenegative_v1.3,illustration, 3d, sepia, painting, cartoons, sketch, (worst quality:1.74), (low quality:1.74), (normal quality:1.44), lowres, bad anatomy, normal quality, ((monochrome)), ((grayscale)), ((letters)), ((english)), capital.',
        'http://106.55.168.194:9000/draw/user/4/05bb76f8-c470-4707-a7fa-b21cc1af6093-image_from_url.png', 'IMG2IMG',
        '3', 1, 1, '2024-11-19 21:27:22', '2024-11-25 21:22:33', 0),
       (5, 1, '1723799430',
        '1女孩，独唱，连裤袜，发髻，看观众，围裙，破连裤袜、内裤，内衣，白色连裤袜。发饰，双髻，刘海，手腕后袖，腮红，夹克，女仆头饰，白色背景，简单背景，撕裂的衣服，白色围裙，褶边，手指后袖，长袖，连裤管下内裤，缎带，发带，白发，蓝眼睛，白色内裤，紫色眼睛，褶边围裙，唇裂，发夹，开口，中发，拉链，云米风格',
        'badhandv4, EasyNegative, verybadimagenegative_v1.3,illustration, 3d, sepia, painting, cartoons, sketch, (worst quality:1.74), (low quality:1.74), (normal quality:1.44), lowres, bad anatomy, normal quality, ((monochrome)), ((grayscale)), ((letters)), ((english)), capital.',
        'http://106.55.168.194:9000/draw/user/1/a47cfa31-35a1-4631-a8ae-64478efd21d0-image_from_url.png', 'TEXT2IMG',
        '2', 0, 1, '2024-11-19 22:04:39', '2024-11-24 20:19:01', 0),
       (6, 1, '1723799430',
        '（杰作），最好的质量，富有表现力的眼睛，完美的脸，1个女孩，美丽，华丽，动漫，女孩，传说，ph bronya，1个女儿，独奏，耳环，长发，白发，钻发，灰色眼睛，连裤袜，坐着，坐在地上，腿在地上、手在大腿之间、胳膊在腿之间、胳膊放在腿之间，',
        'badhandv4, EasyNegative, verybadimagenegative_v1.3,illustration, 3d, sepia, painting, cartoons, sketch, (worst quality:1.74), (low quality:1.74), (normal quality:1.44), lowres, bad anatomy, normal quality, ((monochrome)), ((grayscale)), ((letters)), ((english)), capital.',
        'http://106.55.168.194:9000/draw/user/1/93f6e999-a253-45c6-a563-45f740bc955c-image_from_url.png', 'TEXT2IMG',
        '2', 1, 1, '2024-11-19 22:06:21', '2024-11-25 21:22:37', 0),
       (7, 1, '1723799430',
        '1女孩，夸张的视角，超广角镜头，伸手，缩短，在东京街头，现实主义，高分辨率，女性焦点，单人，雪天，围巾，帽子，飞雪，鱼眼镜头，鱼眼角度',
        'badhandv4, EasyNegative, verybadimagenegative_v1.3,illustration, 3d, sepia, painting, cartoons, sketch, (worst quality:1.74), (low quality:1.74), (normal quality:1.44), lowres, bad anatomy, normal quality, ((monochrome)), ((grayscale)), ((letters)), ((english)), capital.',
        'http://106.55.168.194:9000/draw/user/1/c7588ae5-65d5-4d29-a1d7-3c11fe55d9f5-image_from_url.png', 'TEXT2IMG',
        '2', 0, 1, '2024-11-19 22:06:56', '2024-11-22 18:11:29', 0),
       (8, 1, '1723799430',
        '代表作，对着镜头，质量最好，很有美感，1个女孩，极其精致美丽，噪音和颗粒，简单背景，暗淡背景，模糊，巨大文件大小，阴影（纹理），单色，{黄色和白色单色}，黄色背景，眼睛关闭，站着，乱头发，长发，害羞，窄眼睛，鼻塞，蓝色眼影，困惑，',
        'badhandv4, EasyNegative, verybadimagenegative_v1.3,illustration, 3d, sepia, painting, cartoons, sketch, (worst quality:1.74), (low quality:1.74), (normal quality:1.44), lowres, bad anatomy, normal quality, ((monochrome)), ((grayscale)), ((letters)), ((english)), capital.',
        'http://106.55.168.194:9000/draw/user/1/97eb61c7-a42a-426c-9923-ed99c29713fe-image_from_url.png', 'TEXT2IMG',
        '2', 0, 1, '2024-11-19 22:07:59', '2024-11-21 19:51:26', 0),
       (9, 1, '1723799430', '杰作，最佳品质，超细节，插图，美丽的细节设计，1个女孩，短黑发，充满春天氛围的花朵，美丽的',
        'badhandv4, EasyNegative, verybadimagenegative_v1.3,illustration, 3d, sepia, painting, cartoons, sketch, (worst quality:1.74), (low quality:1.74), (normal quality:1.44), lowres, bad anatomy, normal quality, ((monochrome)), ((grayscale)), ((letters)), ((english)), capital.',
        'http://106.55.168.194:9000/draw/user/1/7f892ceb-7c43-43c0-99a3-a5f0c7c7f1de-image_from_url.png', 'TEXT2IMG',
        '2', 0, 1, '2024-11-21 13:21:16', '2024-11-21 13:21:17', 0),
       (10, 4, '1723799433', '杰作，最佳品质，1个女孩，抱着白猫，在院子里',
        'badhandv4, EasyNegative, verybadimagenegative_v1.3,illustration, 3d, sepia, painting, cartoons, sketch, (worst quality:1.74), (low quality:1.74), (normal quality:1.44), lowres, bad anatomy, normal quality, ((monochrome)), ((grayscale)), ((letters)), ((english)), capital.',
        'http://106.55.168.194:9000/draw/user/4/a0313992-99ad-47a6-943b-d2d23639d205-image_from_url.png', 'TEXT2IMG',
        '2', 0, 1, '2024-11-22 14:17:43', '2024-11-24 20:20:48', 0),
       (11, 4, '1723799433', '杰作，最佳品质，2个女孩，抱着白猫，在院子里',
        'badhandv4, EasyNegative, verybadimagenegative_v1.3,illustration, 3d, sepia, painting, cartoons, sketch, (worst quality:1.74), (low quality:1.74), (normal quality:1.44), lowres, bad anatomy, normal quality, ((monochrome)), ((grayscale)), ((letters)), ((english)), capital.',
        'http://106.55.168.194:9000/draw/user/4/b9b348d0-3165-4512-b843-f281bdca0b6c-image_from_url.png', 'TEXT2IMG',
        '2', 0, 1, '2024-11-22 14:17:19', '2024-11-22 14:17:19', 0),
       (12, 4, '1723799433', '杰作，最佳品质，1个女孩，黑色的长头发，抱着白猫，坐在院子里',
        'badhandv4, EasyNegative, verybadimagenegative_v1.3,illustration, 3d, sepia, painting, cartoons, sketch, (worst quality:1.74), (low quality:1.74), (normal quality:1.44), lowres, bad anatomy, normal quality, ((monochrome)), ((grayscale)), ((letters)), ((english)), capital.',
        'http://106.55.168.194:9000/draw/user/4/b0be8227-397d-4c28-b417-e1cdc3e8133c-image_from_url.png', 'IMG2IMG',
        '3', 0, 1, '2024-11-22 14:19:24', '2024-11-25 21:23:31', 0),
       (13, 4, '1723799433', '杰作，最佳品质，2个女孩，抱着白猫，在院子里',
        'badhandv4, EasyNegative, verybadimagenegative_v1.3,illustration, 3d, sepia, painting, cartoons, sketch, (worst quality:1.74), (low quality:1.74), (normal quality:1.44), lowres, bad anatomy, normal quality, ((monochrome)), ((grayscale)), ((letters)), ((english)), capital.',
        'http://106.55.168.194:9000/draw/user/4/650aad4b-8f82-4b32-9be9-fa2de7223dd9-image_from_url.png', 'TEXT2IMG',
        '2', 1, 1, '2024-11-22 14:38:02', '2024-11-22 18:04:26', 0),
       (14, 4, '1723799433', '杰作，最佳品质，1个女孩，白色的长发，抱着白猫，坐在院子里',
        'badhandv4, EasyNegative, verybadimagenegative_v1.3,illustration, 3d, sepia, painting, cartoons, sketch, (worst quality:1.74), (low quality:1.74), (normal quality:1.44), lowres, bad anatomy, normal quality, ((monochrome)), ((grayscale)), ((letters)), ((english)), capital.',
        'http://106.55.168.194:9000/draw/user/4/1826362a-427e-42aa-b3a4-353a07b680de-image_from_url.png', 'IMG2IMG',
        '3', 0, 1, '2024-11-22 14:39:04', '2024-11-22 14:39:03', 0),
       (15, 4, '1723799433', '杰作，最佳品质，1个女孩，抱着白猫，在院子里',
        'badhandv4, EasyNegative, verybadimagenegative_v1.3,illustration, 3d, sepia, painting, cartoons, sketch, (worst quality:1.74), (low quality:1.74), (normal quality:1.44), lowres, bad anatomy, normal quality, ((monochrome)), ((grayscale)), ((letters)), ((english)), capital.',
        'http://106.55.168.194:9000/draw/user/4/97ff12be-726e-432a-b596-9f579265b239-image_from_url.png', 'TEXT2IMG',
        '2', 1, 1, '2024-11-22 15:05:28', '2024-11-25 21:22:31', 0),
       (16, 4, '1723799433', '杰作，最佳品质，1个女孩，白色长裙，精致的脸颊，白色背景，街道',
        'lowres, bad anatomy, bad hands, text, error, missing fingers, extra digit, fewer digits, cropped, worst quality, low quality, normal quality, jpeg artifacts, signature, watermark, username, blurry',
        'http://106.55.168.194:9000/draw/user/4/3bb69333-edfe-49c0-a4f0-4f7d00550c74-image_from_url.png', 'IMG2IMG',
        '3', 1, 1, '2024-11-22 15:06:52', '2024-11-25 10:29:29', 0),
       (17, 1, '1723799430',
        '杰作，质量最好，1个女孩，洛丽，可爱，独唱，美丽细致的眼睛，精致的脸，毛衣，粉红色的头发，侧髻，紫罗兰色的眼睛，高光染色，衬衫，微裙，黑色连裤袜，透视，街道，钟楼，景深，灰色背景着色。',
        'badhandv4, EasyNegative, verybadimagenegative_v1.3,illustration, 3d, sepia, painting, cartoons, sketch, (worst quality:1.74), (low quality:1.74), (normal quality:1.44), lowres, bad anatomy, normal quality, ((monochrome)), ((grayscale)), ((letters)), ((english)), capital.',
        'http://106.55.168.194:9000/draw/user/1/d203270b-8bfb-431d-a249-d84855be8151-image_from_url.png', 'TEXT2IMG',
        '2', 1, 1, '2024-11-22 16:26:22', '2024-11-25 21:22:36', 0),
       (18, 1, '1723799430',
        '杰作，质量最好，1个女孩，洛丽，可爱，独唱，美丽细致的眼睛，精致的脸，毛衣，粉红色的头发，侧髻，紫罗兰色的眼睛，高光染色，衬衫，微裙，黑色连裤袜，透视，街道，钟楼，景深，灰色背景着色。',
        'badhandv4, EasyNegative, verybadimagenegative_v1.3,illustration, 3d, sepia, painting, cartoons, sketch, (worst quality:1.74), (low quality:1.74), (normal quality:1.44), lowres, bad anatomy, normal quality, ((monochrome)), ((grayscale)), ((letters)), ((english)), capital.',
        'http://106.55.168.194:9000/draw/user/1/7d952dde-0714-4831-857e-e80379467643-image_from_url.png', 'TEXT2IMG',
        '2', 1, 1, '2024-11-22 16:27:04', '2024-11-22 17:52:54', 0),
       (19, 1, '1723799430', '杰作，最佳品质，1个女孩，黑色的长头发，抱着白猫，坐在院子里',
        'badhandv4, EasyNegative, verybadimagenegative_v1.3,illustration, 3d, sepia, painting, cartoons, sketch, (worst quality:1.74), (low quality:1.74), (normal quality:1.44), lowres, bad anatomy, normal quality, ((monochrome)), ((grayscale)), ((letters)), ((english)), capital.',
        'http://106.55.168.194:9000/draw/user/1/559aa63d-0411-443b-8cbb-a03cccb6aa03-image_from_url.png', 'TEXT2IMG',
        '2', 1, 1, '2024-11-22 16:30:40', '2024-11-22 18:11:18', 0),
       (20, 1, '1723799430', '杰作，最佳品质，1个女孩，抱着白猫，在院子里',
        'badhandv4, EasyNegative, verybadimagenegative_v1.3,illustration, 3d, sepia, painting, cartoons, sketch, (worst quality:1.74), (low quality:1.74), (normal quality:1.44), lowres, bad anatomy, normal quality, ((monochrome)), ((grayscale)), ((letters)), ((english)), capital.',
        'http://106.55.168.194:9000/draw/user/1/d6ae03b3-6bf9-41f8-9d32-396cb355644a-image_from_url.png', 'TEXT2IMG',
        '2', 0, 1, '2024-11-22 16:33:50', '2024-11-22 16:33:50', 0),
       (21, 1, '1723799430',
        '1女孩，夸张的视角，超广角镜头，伸手，缩短，在东京街头，现实主义，高分辨率，女性焦点，单人，雪天，围巾，帽子，飞雪，鱼眼镜头，鱼眼角度',
        'badhandv4, EasyNegative, verybadimagenegative_v1.3,illustration, 3d, sepia, painting, cartoons, sketch, (worst quality:1.74), (low quality:1.74), (normal quality:1.44), lowres, bad anatomy, normal quality, ((monochrome)), ((grayscale)), ((letters)), ((english)), capital.',
        'http://106.55.168.194:9000/draw/user/1/341ac9e5-102e-471f-a2f3-9aaa79bac5b3-image_from_url.png', 'TEXT2IMG',
        '2', 1, 0, '2024-11-22 16:41:22', '2024-11-22 17:52:51', 0),
       (22, 1, '1723799430',
        '杰作，质量最好，1个女孩，洛丽，可爱，独唱，美丽细致的眼睛，精致的脸，毛衣，粉红色的头发，侧髻，紫罗兰色的眼睛，高光染色，衬衫，微裙，黑色连裤袜，透视，街道，钟楼，景深，灰色背景着色。',
        'lowres, bad anatomy, bad hands, text, error, missing fingers, extra digit, fewer digits, cropped, worst quality, low quality, normal quality, jpeg artifacts, signature, watermark, username, blurry',
        'http://106.55.168.194:9000/draw/user/1/2b949f6f-6a4b-4419-a492-26882db52824-image_from_url.png', 'IMG2IMG',
        '3', 0, 1, '2024-11-22 17:14:13', '2024-11-22 17:14:13', 0),
       (23, 1, '1723799430',
        '1女孩，超广角镜头，伸手，缩短，��东京街头，��实主义，高分辨率，女��焦点，单人��雪天，围巾，帽子，飞雪，鱼���镜头，鱼眼���度',
        'badhandv4, EasyNegative, verybadimagenegative_v1.3,illustration, 3d, sepia, painting, cartoons, sketch, (worst quality:1.74), (low quality:1.74), (normal quality:1.44), lowres, bad anatomy, normal quality, ((monochrome)), ((grayscale)), ((letters)), ((english)), capital.',
        'http://106.55.168.194:9000/draw/user/1/e81cf514-7ea9-4ffe-a79e-e1b81870b823-image_from_url.png', 'TEXT2IMG',
        '2', 0, 1, '2024-11-22 18:37:59', '2024-11-22 18:37:58', 0),
       (24, 4, '1723799433',
        '1girl, cute, solo, beautiful and delicate eyes, exquisite face, sweater, pink hair, side bun, violet eyes, high gloss dyeing, shirt, micro skirt, black pantyhose, perspective, street, bell tower, depth of field, gray background coloring.',
        'badhandv4, EasyNegative, verybadimagenegative_v1.3,illustration, 3d, sepia, painting, cartoons, sketch, (worst quality:1.74), (low quality:1.74), (normal quality:1.44), lowres, bad anatomy, normal quality, ((monochrome)), ((grayscale)), ((letters)), ((english)), capital.',
        'http://106.55.168.194:9000/draw/user/4/4c1082b4-1563-480b-a17a-6cd386a0b650-image_from_url.png',
        'SUPERTEXT2IMG', '4', 1, 1, '2024-11-23 11:34:42', '2024-11-25 22:56:07', 0),
       (25, 4, '1723799433',
        '1girl, cute, solo, beautiful and delicate eyes, exquisite face, sweater, pink hair, side bun, violet eyes, high gloss dyeing, shirt, micro skirt, black pantyhose, perspective, street, bell tower, depth of field, gray background coloring.',
        'badhandv4, EasyNegative, verybadimagenegative_v1.3,illustration, 3d, sepia, painting, cartoons, sketch, (worst quality:1.74), (low quality:1.74), (normal quality:1.44), lowres, bad anatomy, normal quality, ((monochrome)), ((grayscale)), ((letters)), ((english)), capital.',
        'http://106.55.168.194:9000/draw/user/4/b3be2190-224a-4f8c-b510-e7d6c16e8a16-image_from_url.png',
        'SUPERTEXT2IMG', '4', 0, 1, '2024-11-23 12:05:24', '2024-11-25 22:56:09', 0),
       (26, 4, '1723799433', '1girl, cute',
        'badhandv4, EasyNegative, verybadimagenegative_v1.3,illustration, 3d, sepia, painting, cartoons, sketch, (worst quality:1.74), (low quality:1.74), (normal quality:1.44), lowres, bad anatomy, normal quality, ((monochrome)), ((grayscale)), ((letters)), ((english)), capital.',
        'http://106.55.168.194:9000/draw/user/4/d9035b38-546c-41da-96e5-b98424f877d9-image_from_url.png',
        'SUPERTEXT2IMG', '4', 0, 1, '2024-11-23 12:06:22', '2024-11-24 08:50:01', 0),
       (27, 4, '1723799433',
        '1girl, cute, solo, beautiful and delicate eyes, exquisite face, sweater, pink hair, side bun, violet eyes, high gloss dyeing, shirt, micro skirt, black pantyhose, perspective, street, bell tower, depth of field, gray background coloring.',
        'badhandv4, EasyNegative, verybadimagenegative_v1.3,illustration, 3d, sepia, painting, cartoons, sketch, (worst quality:1.74), (low quality:1.74), (normal quality:1.44), lowres, bad anatomy, normal quality, ((monochrome)), ((grayscale)), ((letters)), ((english)), capital.',
        'http://106.55.168.194:9000/draw/user/4/69988ab1-6fd3-4457-afb9-ea2e13385479-image_from_url.png',
        'SUPERTEXT2IMG', '4', 0, 1, '2024-11-23 13:18:54', '2024-11-24 08:50:01', 0),
       (28, 1, '1723799430', '1 girl',
        'badhandv4, EasyNegative, verybadimagenegative_v1.3,illustration, 3d, sepia, painting, cartoons, sketch, (worst quality:1.74), (low quality:1.74), (normal quality:1.44), lowres, bad anatomy, normal quality, ((monochrome)), ((grayscale)), ((letters)), ((english)), capital.',
        'http://106.55.168.194:9000/draw/user/1/e07119e9-06af-4b7a-b365-3ed0b724a1bd-image_from_url.png',
        'SUPERTEXT2IMG', '4', 2, 1, '2024-11-23 13:58:45', '2024-11-25 22:57:50', 0),
       (29, 1, '1723799430', '1 girl',
        'badhandv4, EasyNegative, verybadimagenegative_v1.3,illustration, 3d, sepia, painting, cartoons, sketch, (worst quality:1.74), (low quality:1.74), (normal quality:1.44), lowres, bad anatomy, normal quality, ((monochrome)), ((grayscale)), ((letters)), ((english)), capital.',
        'http://106.55.168.194:9000/draw/user/1/d3578d28-3a2b-4918-ad21-df634c992148-image_from_url.png',
        'SUPERTEXT2IMG', '4', 3, 1, '2024-11-23 14:07:59', '2024-11-25 21:23:26', 0),
       (30, 4, '1723799433', '1girl, cute',
        'badhandv4, EasyNegative, verybadimagenegative_v1.3,illustration, 3d, sepia, painting, cartoons, sketch, (worst quality:1.74), (low quality:1.74), (normal quality:1.44), lowres, bad anatomy, normal quality, ((monochrome)), ((grayscale)), ((letters)), ((english)), capital.',
        'http://106.55.168.194:9000/draw/user/4/cece8ed5-5922-4029-bd73-25b5dc9c5b3a-image_from_url.png',
        'SUPERIMG2IMG', '5', 0, 1, '2024-11-23 17:08:29', '2024-11-24 08:50:02', 0),
       (31, 1, '1723799430', '1 boys',
        'badhandv4, EasyNegative, verybadimagenegative_v1.3, illustration, 3d, sepia, painting, cartoons, sketch, (worst quality:1.74), (low quality:1.74), (normal quality:1.44), lowres, bad anatomy, normal quality, ((monochrome)), ((grayscale)), ((letters)), ((english)), capital.',
        'http://106.55.168.194:9000/draw/user/1/f09c9b56-426b-4fa9-9951-269285d9b150-image_from_url.png',
        'SUPERIMG2IMG', '5', 0, 1, '2024-11-23 17:39:54', '2024-11-23 17:39:53', 0),
       (32, 1, '1723799430', '1 boys',
        'badhandv4, EasyNegative, verybadimagenegative_v1.3, illustration, 3d, sepia, painting, cartoons, sketch, (worst quality:1.74), (low quality:1.74), (normal quality:1.44), lowres, bad anatomy, normal quality, ((monochrome)), ((grayscale)), ((letters)), ((english)), capital.',
        'http://106.55.168.194:9000/draw/user/1/0ce4571f-1120-43cc-8f97-d68d422a6f64-image_from_url.png',
        'SUPERIMG2IMG', '5', 0, 1, '2024-11-23 17:41:38', '2024-11-23 17:41:37', 0),
       (33, 1, '1723799430',
        'Realistic style mountains and lakes, soft sunlight reflecting on the serene lake surface, snow-capped peaks in the distance, morning dew sparkling',
        'badhandv4, EasyNegative, verybadimagenegative_v1.3,illustration, 3d, sepia, painting, cartoons, sketch, (worst quality:1.74), (low quality:1.74), (normal quality:1.44), lowres, bad anatomy, normal quality, ((monochrome)), ((grayscale)), ((letters)), ((english)), capital.',
        'http://106.55.168.194:9000/draw/user/1/5bb7ff02-5b7e-40f3-b2ab-3979aae6b705-image_from_url.png',
        'SUPERTEXT2IMG', '4', 0, 1, '2024-11-23 18:06:00', '2024-11-23 18:05:59', 0),
       (34, 1, '1723799430', 'The Great Wall',
        'badhandv4, EasyNegative, verybadimagenegative_v1.3,illustration, 3d, sepia, painting, cartoons, sketch, (worst quality:1.74), (low quality:1.74), (normal quality:1.44), lowres, bad anatomy, normal quality, ((monochrome)), ((grayscale)), ((letters)), ((english)), capital.',
        'http://106.55.168.194:9000/draw/user/1/6bc53e8f-ad20-439d-91f1-19adc78216cd-image_from_url.png',
        'SUPERTEXT2IMG', '4', 0, 1, '2024-11-23 18:07:09', '2024-11-23 18:07:09', 0),
       (35, 1, '1723799430', '1 boys，',
        'badhandv4, EasyNegative, verybadimagenegative_v1.3, illustration, 3d, sepia, painting, cartoons, sketch, (worst quality:1.74), (low quality:1.74), (normal quality:1.44), lowres, bad anatomy, normal quality, ((monochrome)), ((grayscale)), ((letters)), ((english)), capital.',
        'http://106.55.168.194:9000/draw/user/1/c328d956-b1e5-4eb8-bfe1-47e24c2e1fe3-image_from_url.png',
        'SUPERIMG2IMG', '5', 0, 1, '2024-11-23 18:08:50', '2024-11-23 18:08:50', 0),
       (36, 1, '1723799430', '1个女孩',
        'badhandv4, EasyNegative, verybadimagenegative_v1.3,illustration, 3d, sepia, painting, cartoons, sketch, (worst quality:1.74), (low quality:1.74), (normal quality:1.44), lowres, bad anatomy, normal quality, ((monochrome)), ((grayscale)), ((letters)), ((english)), capital.',
        'http://106.55.168.194:9000/draw/user/1/8a6b2c69-49ca-413a-a394-97f28f8296aa-image_from_url.png', 'TEXT2IMG',
        '2', 0, 1, '2024-11-23 22:16:12', '2024-11-23 22:16:12', 0),
       (37, 2, '1723799431', '1个女孩，在街道',
        'badhandv4, EasyNegative, verybadimagenegative_v1.3,illustration, 3d, sepia, painting, cartoons, sketch, (worst quality:1.74), (low quality:1.74), (normal quality:1.44), lowres, bad anatomy, normal quality, ((monochrome)), ((grayscale)), ((letters)), ((english)), capital.',
        'http://106.55.168.194:9000/draw/user/2/53e65d07-8adc-45af-bc34-09ad0d765e3b-image_from_url.png', 'TEXT2IMG',
        '2', 0, 1, '2024-11-23 22:17:17', '2024-11-23 22:17:16', 0),
       (38, 2, '1723799431', '1个蓝色头发的男孩',
        'lowres, bad anatomy, bad hands, text, error, missing fingers, extra digit, fewer digits, cropped, worst quality, low quality, normal quality, jpeg artifacts, signature, watermark, username, blurry',
        'http://106.55.168.194:9000/draw/user/2/c4ff9b2c-49a6-4aef-abf7-58e7ef45b064-image_from_url.png', 'IMG2IMG',
        '3', 0, 1, '2024-11-23 22:18:06', '2024-11-25 21:22:52', 0),
       (39, 2, '1723799431', '1个女孩，白发',
        'lowres, bad anatomy, bad hands, text, error, missing fingers, extra digit, fewer digits, cropped, worst quality, low quality, normal quality, jpeg artifacts, signature, watermark, username, blurry',
        'http://106.55.168.194:9000/draw/user/2/fcc4f8c6-dae8-4233-a4db-77639c9a8680-image_from_url.png', 'IMG2IMG',
        '3', 1, 1, '2024-11-23 22:23:26', '2024-11-25 21:22:29', 0),
       (40, 1, '1723799430', 'lineart,monochrome,white background,(best quality:0.5),lineart,1girl,beautiful,happy,',
        'badhandv4, EasyNegative, verybadimagenegative_v1.3,illustration, 3d, sepia, painting, cartoons, sketch, (worst quality:1.74), (low quality:1.74), (normal quality:1.44), lowres, bad anatomy, normal quality, ((monochrome)), ((grayscale)), ((letters)), ((english)), capital.',
        'http://106.55.168.194:9000/draw/user/1/c7e1d6e8-daa1-4391-9966-e73fc58519f7-image_from_url.png',
        'SUPERTEXT2IMG', '4', 0, 1, '2024-11-25 10:50:40', '2024-11-25 10:50:40', 0),
       (41, 1, '1723799430', '1 girl',
        'badhandv4, EasyNegative, verybadimagenegative_v1.3, illustration, 3d, sepia, painting, cartoons, sketch, (worst quality:1.74), (low quality:1.74), (normal quality:1.44), lowres, bad anatomy, normal quality, ((monochrome)), ((grayscale)), ((letters)), ((english)), capital.',
        'http://106.55.168.194:9000/draw/user/1/0171a605-f726-4f3b-bffe-5ddf25d40038-image_from_url.png',
        'SUPERIMG2IMG', '5', 0, 1, '2024-11-25 10:56:46', '2024-11-25 10:56:46', 0),
       (42, 1, '1723799430', '1 girl',
        'badhandv4, EasyNegative, verybadimagenegative_v1.3, illustration, 3d, sepia, painting, cartoons, sketch, (worst quality:1.74), (low quality:1.74), (normal quality:1.44), lowres, bad anatomy, normal quality, ((monochrome)), ((grayscale)), ((letters)), ((english)), capital.',
        'http://106.55.168.194:9000/draw/user/1/888720af-a97d-4cf6-90c6-aa55f41ced40-image_from_url.png',
        'SUPERIMG2IMG', '5', 0, 1, '2024-11-25 10:57:25', '2024-11-25 10:57:24', 0),
       (43, 1, '1723799430', '1 girl',
        'badhandv4, EasyNegative, verybadimagenegative_v1.3, illustration, 3d, sepia, painting, cartoons, sketch, (worst quality:1.74), (low quality:1.74), (normal quality:1.44), lowres, bad anatomy, normal quality, ((monochrome)), ((grayscale)), ((letters)), ((english)), capital.',
        'http://106.55.168.194:9000/draw/user/1/085c244c-ad3d-4559-a67d-e8131ab74b97-image_from_url.png',
        'SUPERIMG2IMG', '5', 0, 1, '2024-11-25 10:58:13', '2024-11-25 10:58:13', 0),
       (44, 1, '1723799430', '1个女孩，抱着猫',
        'badhandv4, EasyNegative, verybadimagenegative_v1.3,illustration, 3d, sepia, painting, cartoons, sketch, (worst quality:1.74), (low quality:1.74), (normal quality:1.44), lowres, bad anatomy, normal quality, ((monochrome)), ((grayscale)), ((letters)), ((english)), capital.',
        'http://106.55.168.194:9000/draw/user/1/4a5a236a-0452-4d2f-937d-2031eee0cef2-image_from_url.png', 'TEXT2IMG',
        '2', 0, 1, '2024-11-25 10:59:53', '2024-11-25 10:59:53', 0),
       (45, 1, '1723799430', '1 girls',
        'lowres, bad anatomy, bad hands, text, error, missing fingers, extra digit, fewer digits, cropped, worst quality, low quality, normal quality, jpeg artifacts, signature, watermark, username, blurry',
        'http://106.55.168.194:9000/draw/user/1/302a7a13-973a-45a0-afc4-7b8cc42af3e0-image_from_url.png', 'IMG2IMG',
        '3', 0, 1, '2024-11-25 11:00:58', '2024-11-25 11:00:58', 0),
       (46, 7, '1723799436', '1个男孩',
        'lowres, bad anatomy, bad hands, text, error, missing fingers, extra digit, fewer digits, cropped, worst quality, low quality, normal quality, jpeg artifacts, signature, watermark, username, blurry',
        'http://106.55.168.194:9000/draw/user/7/216de466-d9e0-4fd8-bcca-5cfbd93baed4-image_from_url.png', 'IMG2IMG',
        '3', 0, 1, '2024-11-25 23:00:57', '2024-11-25 23:00:56', 0),
       (47, 7, '1723799436', '1个男孩',
        'lowres, bad anatomy, bad hands, text, error, missing fingers, extra digit, fewer digits, cropped, worst quality, low quality, normal quality, jpeg artifacts, signature, watermark, username, blurry',
        'http://106.55.168.194:9000/draw/user/7/e75b5a68-ed4d-4e73-9aea-41d83ccf9ca9-image_from_url.png', 'IMG2IMG',
        '3', 0, 1, '2024-11-25 23:02:44', '2024-11-25 23:02:44', 0);


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
       (2, 2, 1, '2024-11-19 22:55:14', '2024-11-19 23:13:58', 0),
       (3, 3, 1, '2024-11-19 23:14:21', '2024-11-19 23:14:32', 0),
       (4, 4, 1, '2024-11-21 20:37:20', '2024-11-21 20:37:20', 0),
       (5, 1, 2, '2024-11-21 20:51:49', '2024-11-25 21:22:19', 0),
       (6, 1, 7, '2024-11-21 20:53:19', '2024-11-21 22:27:00', 0),
       (7, 1, 4, '2024-11-22 00:37:24', '2024-11-22 00:39:26', 0),
       (8, 5, 1, '2024-11-22 17:52:18', '2024-11-24 20:19:01', 1),
       (9, 21, 1, '2024-11-22 17:52:51', '2024-11-22 17:52:51', 0),
       (10, 18, 1, '2024-11-22 17:52:54', '2024-11-22 17:52:54', 0),
       (11, 7, 1, '2024-11-22 18:01:11', '2024-11-22 18:11:29', 1),
       (12, 13, 1, '2024-11-22 18:04:27', '2024-11-22 18:04:26', 0),
       (13, 10, 1, '2024-11-22 18:04:32', '2024-11-24 20:20:48', 1),
       (14, 19, 1, '2024-11-22 18:11:18', '2024-11-22 18:11:18', 0),
       (15, 25, 1, '2024-11-23 14:10:11', '2024-11-25 22:56:09', 1),
       (16, 31, 1, '2024-11-23 14:10:33', '2024-11-23 14:10:33', 0),
       (17, 16, 1, '2024-11-25 10:29:30', '2024-11-25 10:29:29', 0),
       (18, 12, 1, '2024-11-25 21:19:30', '2024-11-25 21:20:53', 1),
       (19, 24, 1, '2024-11-25 21:19:35', '2024-11-25 22:56:07', 1),
       (20, 12, 2, '2024-11-25 21:22:14', '2024-11-25 21:22:20', 1),
       (21, 41, 2, '2024-11-25 21:22:29', '2024-11-25 21:22:29', 0),
       (22, 30, 2, '2024-11-25 21:22:31', '2024-11-25 21:22:30', 0),
       (23, 15, 2, '2024-11-25 21:22:32', '2024-11-25 21:22:31', 0),
       (24, 4, 2, '2024-11-25 21:22:33', '2024-11-25 21:22:32', 0),
       (25, 31, 2, '2024-11-25 21:22:35', '2024-11-25 21:22:35', 0),
       (26, 17, 2, '2024-11-25 21:22:36', '2024-11-25 21:22:36', 0),
       (27, 6, 2, '2024-11-25 21:22:38', '2024-11-25 21:22:37', 0),
       (28, 40, 2, '2024-11-25 21:22:50', '2024-11-25 21:22:52', 0),
       (29, 1, 3, '2024-11-25 21:23:13', '2024-11-25 21:23:13', 0),
       (30, 12, 3, '2024-11-25 21:23:17', '2024-11-25 21:23:30', 0),
       (31, 24, 3, '2024-11-25 21:23:25', '2024-11-25 21:23:24', 0),
       (32, 31, 3, '2024-11-25 21:23:27', '2024-11-25 21:23:26', 0),
       (33, 30, 7, '2024-11-25 22:57:50', '2024-11-25 22:57:50', 0);

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