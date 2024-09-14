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
CREATE TABLE sys_user (  
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',  
    phone VARCHAR(20) UNIQUE NOT NULL COMMENT '手机号，用作登录名',  
    password VARCHAR(255) NOT NULL COMMENT '密码',  
    nickname VARCHAR(50) COMMENT '用户昵称',  
    avatar_url VARCHAR(255) COMMENT '头像URL',  
    status TINYINT DEFAULT 1 COMMENT '用户状态：1正常，0禁用',  
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',  
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',  
    is_deleted TINYINT DEFAULT 0 COMMENT '是否删除：0未删除，1已删除'  
) COMMENT '系统用户表';  

-- 角色表  
CREATE TABLE sys_role (  
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '角色ID',  
    name VARCHAR(50) NOT NULL UNIQUE COMMENT '角色名称',  
    description VARCHAR(255) COMMENT '角色描述',  
    status TINYINT DEFAULT 1 COMMENT '角色状态：1正常，0禁用',  
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',  
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'  
) COMMENT '系统角色表';  

-- 菜单表  
CREATE TABLE sys_menu (  
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '菜单ID',  
    name VARCHAR(50) NOT NULL COMMENT '菜单名称',  
    path VARCHAR(255) COMMENT '菜单路径',  
    component VARCHAR(255) COMMENT '组件',  
    parent_id INT COMMENT '父菜单ID',  
    menu_type TINYINT COMMENT '菜单类型：1目录，2菜单，3按钮',  
    icon VARCHAR(50) COMMENT '菜单图标',  
    sort_order INT DEFAULT 0 COMMENT '排序号',  
    permission VARCHAR(100) COMMENT '权限标识',  
    status TINYINT DEFAULT 1 COMMENT '菜单状态：1正常，0禁用',  
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',  
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'  
) COMMENT '系统菜单表';  

-- 用户角色关联表  
CREATE TABLE sys_user_role (  
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '关联ID',  
    user_id INT NOT NULL COMMENT '用户ID',  
    role_id INT NOT NULL COMMENT '角色ID'  
) COMMENT '用户角色关联表';  

-- 角色菜单关联表  
CREATE TABLE sys_role_menu (  
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '关联ID',  
    role_id INT NOT NULL COMMENT '角色ID',  
    menu_id INT NOT NULL COMMENT '菜单ID'  
) COMMENT '角色菜单关联表';  

-- 插入用户数据  
INSERT INTO sys_user (phone, password, nickname, avatar_url) VALUES  
('13800138000', 'password123', '超级管理员', 'https://avatars.githubusercontent.com/u/46013989?v=4&size=64'),  
('13900139000', 'password456', '管理员', 'https://avatars.githubusercontent.com/u/46013989?v=4&size=64'),  
('13700137000', 'password789', '普通用户', 'https://avatars.githubusercontent.com/u/46013989?v=4&size=64');  

-- 插入角色数据  
INSERT INTO sys_role (name, description) VALUES  
('超级管理员', '系统超级管理员，拥有所有权限'),  
('管理员', '系统管理员，拥有大部分权限'),  
('普通用户', '普通用户，拥有基本权限');  

-- 插入菜单数据  
INSERT INTO sys_menu (name, path, component, parent_id, menu_type, icon, sort_order, permission) VALUES  
('系统管理', '/system', 'Layout', 0, 1, 'setting', 1, 'system'),  
('用户管理', '/system/user', 'system/user/index', 1, 2, 'user', 1, 'system:user:list'),  
('角色管理', '/system/role', 'system/role/index', 1, 2, 'peoples', 2, 'system:role:list'),  
('菜单管理', '/system/menu', 'system/menu/index', 1, 2, 'tree-table', 3, 'system:menu:list'),  
('添加用户', '', '', 2, 3, '', 1, 'system:user:add'),  
('编辑用户', '', '', 2, 3, '', 2, 'system:user:edit'),  
('删除用户', '', '', 2, 3, '', 3, 'system:user:delete'),  
('数据统计', '/statistics', 'Layout', 0, 1, 'chart', 2, 'statistics'),  
('用户统计', '/statistics/user', 'statistics/user/index', 8, 2, 'user', 1, 'statistics:user'),  
('订单统计', '/statistics/order', 'statistics/order/index', 8, 2, 'shopping', 2, 'statistics:order');  

-- 插入用户角色关联数据  
INSERT INTO sys_user_role (user_id, role_id) VALUES  
(1, 1), -- 超级管理员用户 -> 超级管理员角色  
(2, 2), -- 管理员用户 -> 管理员角色  
(3, 3); -- 普通用户 -> 普通用户角色  

-- 插入角色菜单关联数据  
INSERT INTO sys_role_menu (role_id, menu_id) VALUES  
-- 超级管理员可以访问所有菜单  
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7), (1, 8), (1, 9), (1, 10),  
-- 管理员可以访问大部分菜单，但不能删除用户  
(2, 1), (2, 2), (2, 3), (2, 4), (2, 5), (2, 6), (2, 8), (2, 9), (2, 10),  
-- 普通用户只能访问数据统计  
(3, 8), (3, 9), (3, 10);