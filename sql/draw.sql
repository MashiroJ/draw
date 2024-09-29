-- 删除数据库
DROP DATABASE IF EXISTS draw;

-- 创建数据库
CREATE DATABASE IF NOT EXISTS draw CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 使用数据库
USE draw;

-- 删除表（如果存在）
DROP TABLE IF EXISTS sys_role_menu;
DROP TABLE IF EXISTS sys_user_role;
DROP TABLE IF EXISTS sys_menu;
DROP TABLE IF EXISTS sys_role;
DROP TABLE IF EXISTS sys_user;

-- 用户表
CREATE TABLE sys_user
(
    id          INT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    phone       VARCHAR(20) UNIQUE NOT NULL COMMENT '手机号，用作登录名',
    password    VARCHAR(255)       NOT NULL COMMENT '密码',
    nickname    VARCHAR(50) COMMENT '用户昵称',
    avatar_url  VARCHAR(255) COMMENT '头像URL',
    status      TINYINT  DEFAULT 1 COMMENT '用户状态：1正常，0禁用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted  TINYINT  DEFAULT 0 COMMENT '是否删除：0未删除，1已删除'
) COMMENT '系统用户表';

-- 角色表
CREATE TABLE sys_role
(
    id          INT PRIMARY KEY AUTO_INCREMENT COMMENT '角色ID',
    name        VARCHAR(50) NOT NULL UNIQUE COMMENT '角色名称',
    description VARCHAR(255) COMMENT '角色描述',
    status      TINYINT  DEFAULT 1 COMMENT '角色状态：1正常，0禁用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted  TINYINT  DEFAULT 0 COMMENT '是否删除：0未删除，1已删除'
) COMMENT '系统角色表';

-- 菜单表
CREATE TABLE sys_menu
(
    id          INT PRIMARY KEY AUTO_INCREMENT COMMENT '菜单ID',
    name        VARCHAR(50) NOT NULL COMMENT '菜单名称',
    path        VARCHAR(255) COMMENT '菜单路径',
    parent_id   INT DEFAULT NULL COMMENT '父菜单ID，根菜单为NULL',
    icon        VARCHAR(50) COMMENT '菜单图标',
    sort_order  INT DEFAULT 0 COMMENT '排序号',
    permission  VARCHAR(100) COMMENT '权限标识',
    status      TINYINT DEFAULT 1 COMMENT '菜单状态：1正常，0禁用',
    is_visible   TINYINT DEFAULT 1 COMMENT '是否可见：1可见，0不可见',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted  TINYINT DEFAULT 0 COMMENT '是否删除：0未删除，1已删除'
) COMMENT '系统菜单表';

-- 用户角色关联表
CREATE TABLE sys_user_role
(
    id          INT PRIMARY KEY AUTO_INCREMENT COMMENT '关联ID',
    user_id     INT NOT NULL COMMENT '用户ID',
    role_id     INT NOT NULL COMMENT '角色ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted  TINYINT  DEFAULT 0 COMMENT '是否删除：0未删除，1已删除'
) COMMENT '用户角色关联表';

-- 角色菜单关联表
CREATE TABLE sys_role_menu
(
    id          INT PRIMARY KEY AUTO_INCREMENT COMMENT '关联ID',
    role_id     INT NOT NULL COMMENT '角色ID',
    menu_id     INT NOT NULL COMMENT '菜单ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted  TINYINT  DEFAULT 0 COMMENT '是否删除：0未删除，1已删除'
) COMMENT '角色菜单关联表';

-- 插入测试数据到用户表
INSERT INTO sys_user (phone, password, nickname, avatar_url, status)
VALUES ('13800000001', 'hashed_password_1', '用户1', 'https://avatars.githubusercontent.com/u/46013989?v=4&size=64', 1),
       ('13800000002', 'hashed_password_2', '用户2', 'https://avatars.githubusercontent.com/u/46013989?v=4&size=64', 1),
       ('13800000003', 'hashed_password_3', '用户3', 'https://avatars.githubusercontent.com/u/46013989?v=4&size=64', 0),
       ('13800000004', 'hashed_password_4', '用户4', 'https://avatars.githubusercontent.com/u/46013989?v=4&size=64', 1);

-- 插入测试数据到角色表
INSERT INTO sys_role (name, description, status)
VALUES ('管理员', '拥有所有权限的角色', 1),
       ('编辑', '具有编辑权限的角色', 1),
       ('访客', '仅限查看的角色', 1),
       ('超级管理员', '具备最高权限的管理员角色', 1);

-- 插入测试数据到菜单表
INSERT INTO sys_menu (name, path, parent_id, icon, sort_order, permission, status, is_visible)
VALUES
    ('仪表盘', '/dashboard', NULL, 'dashboard', 1, 'view_dashboard', 1, 1),
    ('用户管理', '/user', NULL, 'users', 2, 'manage_users', 1, 1),
    ('角色管理', '/role', NULL, 'roles', 3, 'manage_roles', 1, 1),
    ('设置', '/settings', NULL, 'settings', 4, 'manage_settings', 1, 1),
    ('日志', '/logs', NULL, 'logs', 5, 'view_logs', 1, 1);

-- 插入测试数据到用户角色关联表
INSERT INTO sys_user_role (user_id, role_id)
VALUES (1, 1), -- 用户1 是 管理员
       (1, 2), -- 用户1 也是 编辑
       (2, 2), -- 用户2 是 编辑
       (3, 3);  -- 用户3 是 访客

-- 插入测试数据到角色菜单关联表
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (1, 1), -- 管理员 可以访问仪表盘
       (1, 2), -- 管理员 可以访问用户管理
       (1, 3), -- 管理员 可以访问角色管理
       (2, 2), -- 编辑角色 可以访问用户管理
       (3, 1); -- 访客角色 可以访问仪表盘