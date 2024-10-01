
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
CREATE TABLE `sys_user`
(
    id          INT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    username    VARCHAR(50) UNIQUE NOT NULL COMMENT '用户名',
    password    VARCHAR(255)       NOT NULL COMMENT '密码',
    phone       VARCHAR(20) COMMENT '手机号',
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
INSERT INTO sys_user (username, password, phone, avatar_url, status)
VALUES ('admin', 'hashed_password_admin', '13800000001', 'https://avatars.githubusercontent.com/u/46013989?v=4&size=64', 1),
       ('user1', 'hashed_password_user1', '13800000002', 'https://avatars.githubusercontent.com/u/46013989?v=4&size=64', 1),
       ('member1', 'hashed_password_member1', '13800000003', 'https://avatars.githubusercontent.com/u/46013989?v=4&size=64', 1);

-- 插入测试数据到角色表
INSERT INTO sys_role (name, description, status)
VALUES ('管理员', '拥有所有权限的角色', 1),
       ('普通用户', '具有普通用户权限的角色', 1),
       ('会员用户', '具有会员权限的角色', 1);

-- 插入测试数据到用户角色关联表
INSERT INTO sys_user_role (user_id, role_id)
VALUES (1, 1), -- 用户1(admin) 是 管理员
       (2, 2), -- 用户2(user1) 是 普通用户
       (3, 3); -- 用户3(member1) 是 会员

-- 插入测试数据到菜单表
INSERT INTO sys_menu (id, name, path, parent_id, icon, sort_order, permission, status, is_visible)
VALUES
    (1, '主页', '/home', NULL, 'dashboard', 1, 'view_dashboard', 1, 1),
    (2, '系统管理', '/system', NULL, 'settings', 2, NULL, 1, 1),
    (3, '绘画管理', '/draw', NULL, 'brush', 3, NULL, 1, 1),
    (4, '用户管理', '/user', 2, 'users', 1, 'manage_users', 1, 1),
    (5, '角色管理', '/role', 2, 'roles', 2, 'manage_roles', 1, 1),
    (6, '菜单管理', '/menu', 2, 'menu', 3, 'manage_menu', 1, 1),
    (7, '绘画生成', '/generate', 3, 'brush', 1, 'generate_drawing', 1, 1),
    (8, '画廊展示', '/gallery', 3, 'image', 2, 'view_gallery', 1, 1);

-- 插入测试数据到角色菜单关联表
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (1, 1), -- 管理员角色 可以访问所有菜单
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (1, 6),
       (1, 7),
       (1, 8),
       (2, 1), -- 普通用户角色 可以访问主页和绘画管理
       (2, 3),
       (2, 7),
       (2, 8),
       (3, 1), -- 会员角色 可以访问主页和绘画管理
       (3, 3),
       (3, 7),
       (3, 8);
