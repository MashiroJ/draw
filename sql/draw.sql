-- MySQL dump 10.13  Distrib 9.0.1, for Linux (x86_64)
--
-- Host: localhost    Database: draw
-- ------------------------------------------------------
-- Server version	9.0.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Table structure for table `draw_like`
--

DROP TABLE IF EXISTS `draw_like`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `draw_like`
(
    `id`          bigint NOT NULL AUTO_INCREMENT COMMENT '点赞ID',
    `draw_id`     bigint NOT NULL COMMENT '绘画记录ID',
    `user_id`     int    NOT NULL COMMENT '用户ID',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`  tinyint  DEFAULT '0' COMMENT '是否删除：0未删除，1已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_draw_user` (`draw_id`, `user_id`),
    UNIQUE KEY `unique_user_draw` (`user_id`, `draw_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_create_time` (`create_time`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='绘画点赞表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `draw_like`
--

LOCK TABLES `draw_like` WRITE;
/*!40000 ALTER TABLE `draw_like`
    DISABLE KEYS */;
INSERT INTO `draw_like`
VALUES (1, 1, 1, '2024-11-19 22:36:52', '2024-11-19 23:13:26', 0),
       (2, 2, 1, '2024-11-19 22:55:14', '2024-11-19 23:13:58', 0),
       (3, 3, 1, '2024-11-19 23:14:21', '2024-11-19 23:14:32', 0);
/*!40000 ALTER TABLE `draw_like`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `draw_record`
--

DROP TABLE IF EXISTS `draw_record`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `draw_record`
(
    `id`              bigint                                  NOT NULL AUTO_INCREMENT COMMENT '绘画记录ID',
    `user_id`         int                                     NOT NULL COMMENT '创建用户ID',
    `task_id`         varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '任务ID',
    `prompt`          text COLLATE utf8mb4_unicode_ci         NOT NULL COMMENT '绘画提示词',
    `negative_prompt` text COLLATE utf8mb4_unicode_ci COMMENT '反向提示词',
    `image_url`       varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '生成图片URL',
    `generation_type` varchar(20) COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '生成类型：TEXT2IMG/IMG2IMG',
    `work_flow_name`  varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '工作流名称',
    `like_count`      int                                     DEFAULT '0' COMMENT '点赞数',
    `is_public`       tinyint                                 DEFAULT '1' COMMENT '是否公开：1公开，0私有',
    `create_time`     datetime                                DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     datetime                                DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`      tinyint                                 DEFAULT '0' COMMENT '是否删除：0未删除，1已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 10
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='绘画记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `draw_record`
--

LOCK TABLES `draw_record` WRITE;
/*!40000 ALTER TABLE `draw_record`
    DISABLE KEYS */;
INSERT INTO `draw_record`
VALUES (1, 1, '1723799430',
        '杰作，质量最好，1个女孩，洛丽，可爱，独唱，美丽细致的眼睛，精致的脸，毛衣，粉红色的头发，侧髻，紫罗兰色的眼睛，高光染色，衬衫，微裙，黑色连裤袜，透视，街道，钟楼，景深，灰色背景着色。',
        'badhandv4, EasyNegative, verybadimagenegative_v1.3,illustration, 3d, sepia, painting, cartoons, sketch, (worst quality:1.74), (low quality:1.74), (normal quality:1.44), lowres, bad anatomy, normal quality, ((monochrome)), ((grayscale)), ((letters)), ((english)), capital.',
        'http://106.55.168.194:9000/draw/user/1/51bbab2d-d9fe-4402-bf58-90290ac1ada3-image_from_url.png', 'TEXT2IMG',
        '2', 1, 1, '2024-11-19 21:20:19', '2024-11-21 19:51:26', 0),
       (2, 2, '1723799431',
        '杰作，质量最好，1个男孩,美丽细致的眼睛，精致的脸，大衣，白色的头发，侧髻，紫罗兰色的眼睛，高光染色，衬衫，微裙，白色连裤袜，透视，街道，景深，白色背景',
        'badhandv4, EasyNegative, verybadimagenegative_v1.3,illustration, 3d, sepia, painting, cartoons, sketch, (worst quality:1.74), (low quality:1.74), (normal quality:1.44), lowres, bad anatomy, normal quality, ((monochrome)), ((grayscale)), ((letters)), ((english)), capital.',
        'http://106.55.168.194:9000/draw/user/2/69533d33-6e37-4d77-abb4-31ec0ae685ab-image_from_url.png', 'TEXT2IMG',
        '2', 1, 1, '2024-11-19 21:22:47', '2024-11-21 19:51:26', 0),
       (3, 7, '1723799436', '杰作，质量最好,一个男孩，白色头发，抱着狗',
        'badhandv4, EasyNegative, verybadimagenegative_v1.3,illustration, 3d, sepia, painting, cartoons, sketch, (worst quality:1.74), (low quality:1.74), (normal quality:1.44), lowres, bad anatomy, normal quality, ((monochrome)), ((grayscale)), ((letters)), ((english)), capital.',
        'http://106.55.168.194:9000/draw/user/7/6f3de578-a8b2-4ff3-9688-dcc6e634db26-image_from_url.png', 'IMG2IMG',
        '3', 1, 1, '2024-11-19 21:24:58', '2024-11-20 22:09:52', 0),
       (4, 4, '1723799433', '杰作，质量最好,一个男孩，长头发，街景，精致的脸庞',
        'badhandv4, EasyNegative, verybadimagenegative_v1.3,illustration, 3d, sepia, painting, cartoons, sketch, (worst quality:1.74), (low quality:1.74), (normal quality:1.44), lowres, bad anatomy, normal quality, ((monochrome)), ((grayscale)), ((letters)), ((english)), capital.',
        'http://106.55.168.194:9000/draw/user/4/05bb76f8-c470-4707-a7fa-b21cc1af6093-image_from_url.png', 'IMG2IMG',
        '3', 0, 1, '2024-11-19 21:27:22', '2024-11-21 19:50:54', 0),
       (5, 1, '1723799430',
        '1女孩，独唱，连裤袜，发髻，看观众，围裙，破连裤袜、内裤，内衣，白色连裤袜。发饰，双髻，刘海，手腕后袖，腮红，夹克，女仆头饰，白色背景，简单背景，撕裂的衣服，白色围裙，褶边，手指后袖，长袖，连裤管下内裤，缎带，发带，白发，蓝眼睛，白色内裤，紫色眼睛，褶边围裙，唇裂，发夹，开口，中发，拉链，云米风格',
        'badhandv4, EasyNegative, verybadimagenegative_v1.3,illustration, 3d, sepia, painting, cartoons, sketch, (worst quality:1.74), (low quality:1.74), (normal quality:1.44), lowres, bad anatomy, normal quality, ((monochrome)), ((grayscale)), ((letters)), ((english)), capital.',
        'http://106.55.168.194:9000/draw/user/1/a47cfa31-35a1-4631-a8ae-64478efd21d0-image_from_url.png', 'TEXT2IMG',
        '2', 0, 1, '2024-11-19 22:04:39', '2024-11-21 19:51:26', 0),
       (6, 1, '1723799430',
        '（杰作），最好的质量，富有表现力的眼睛，完美的脸，1个女孩，美丽，华丽，动漫，女孩，传说，ph bronya，1个女儿，独奏，耳环，长发，白发，钻发，灰色眼睛，连裤袜，坐着，坐在地上，腿在地上、手在大腿之间、胳膊在腿之间、胳膊放在腿之间，',
        'badhandv4, EasyNegative, verybadimagenegative_v1.3,illustration, 3d, sepia, painting, cartoons, sketch, (worst quality:1.74), (low quality:1.74), (normal quality:1.44), lowres, bad anatomy, normal quality, ((monochrome)), ((grayscale)), ((letters)), ((english)), capital.',
        'http://106.55.168.194:9000/draw/user/1/93f6e999-a253-45c6-a563-45f740bc955c-image_from_url.png', 'TEXT2IMG',
        '2', 0, 1, '2024-11-19 22:06:21', '2024-11-21 19:51:26', 0),
       (7, 1, '1723799430',
        '1女孩，夸张的视角，超广角镜头，伸手，缩短，在东京街头，现实主义，高分辨率，女性焦点，单人，雪天，围巾，帽子，飞雪，鱼眼镜头，鱼眼角度',
        'badhandv4, EasyNegative, verybadimagenegative_v1.3,illustration, 3d, sepia, painting, cartoons, sketch, (worst quality:1.74), (low quality:1.74), (normal quality:1.44), lowres, bad anatomy, normal quality, ((monochrome)), ((grayscale)), ((letters)), ((english)), capital.',
        'http://106.55.168.194:9000/draw/user/1/c7588ae5-65d5-4d29-a1d7-3c11fe55d9f5-image_from_url.png', 'TEXT2IMG',
        '2', 0, 1, '2024-11-19 22:06:56', '2024-11-21 19:51:26', 0),
       (8, 1, '1723799430',
        '代表作，对着镜头，质量最好，很有美感，1个女孩，极其精致美丽，噪音和颗粒，简单背景，暗淡背景，模糊，巨大文件大小，阴影（纹理），单色，{黄色和白色单色}，黄色背景，眼睛关闭，站着，乱头发，长发，害羞，窄眼睛，鼻塞，蓝色眼影，困惑，',
        'badhandv4, EasyNegative, verybadimagenegative_v1.3,illustration, 3d, sepia, painting, cartoons, sketch, (worst quality:1.74), (low quality:1.74), (normal quality:1.44), lowres, bad anatomy, normal quality, ((monochrome)), ((grayscale)), ((letters)), ((english)), capital.',
        'http://106.55.168.194:9000/draw/user/1/97eb61c7-a42a-426c-9923-ed99c29713fe-image_from_url.png', 'TEXT2IMG',
        '2', 0, 1, '2024-11-19 22:07:59', '2024-11-21 19:51:26', 0),
       (9, 1, '1723799430', '杰作，最佳品质，超细节，插图，美丽的细节设计，1个女孩，短黑发，充满春天氛围的花朵，美丽的',
        'badhandv4, EasyNegative, verybadimagenegative_v1.3,illustration, 3d, sepia, painting, cartoons, sketch, (worst quality:1.74), (low quality:1.74), (normal quality:1.44), lowres, bad anatomy, normal quality, ((monochrome)), ((grayscale)), ((letters)), ((english)), capital.',
        'http://106.55.168.194:9000/draw/user/1/7f892ceb-7c43-43c0-99a3-a5f0c7c7f1de-image_from_url.png', 'TEXT2IMG',
        '2', 0, 1, '2024-11-21 13:21:16', '2024-11-21 13:21:17', 0);
/*!40000 ALTER TABLE `draw_record`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
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
  AUTO_INCREMENT = 12
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='系统菜单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu`
    DISABLE KEYS */;
INSERT INTO `sys_menu`
VALUES (1, '主页', '/', 'HomeFilled', 1, 'dashboard', 1, 1, '2024-10-31 13:40:49', '2024-11-20 15:53:50', 0),
       (2, '用户管理', '/user', 'User', 2, 'user:manage', 1, 1, '2024-10-31 13:40:49', '2024-11-20 15:32:54', 0),
       (3, '角色管理', '/role', 'User', 3, 'role:manage', 1, 1, '2024-10-31 13:40:49', '2024-11-20 15:32:54', 0),
       (4, '菜单管理', '/menu', 'Menu', 4, 'menu:manage', 1, 1, '2024-10-31 13:40:49', '2024-11-20 15:33:04', 0),
       (5, '画廊', '/gallery', 'Gallery', 5, 'gallery:view', 1, 1, '2024-10-31 13:40:49', '2024-11-20 15:33:04', 0),
       (6, '文本转图片', '/text2img', 'Text', 6, 'text2img:convert', 1, 1, '2024-10-31 13:40:49', '2024-11-20 15:33:04',
        0),
       (7, '图片转图片', '/img2img', 'Image', 7, 'img2img:convert', 1, 1, '2024-10-31 13:40:49', '2024-11-20 15:33:11',
        0),
       (8, '404', '/404', 'Error', 8, 'error:view', 1, 1, '2024-10-31 13:40:49', '2024-11-20 15:33:16', 0);
/*!40000 ALTER TABLE `sys_menu`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role`
    DISABLE KEYS */;
INSERT INTO `sys_role`
VALUES (1, '管理员', '拥有所有权限的角色', 1, '2024-10-31 13:40:49', '2024-11-20 21:12:04', 0),
       (2, '普通用户', '具有普通用户权限的角色', 1, '2024-10-31 13:40:49', '2024-11-02 00:28:46', 0),
       (3, '会员用户', '具有会员权限的角色', 1, '2024-10-31 13:40:49', '2024-11-02 00:28:47', 0);
/*!40000 ALTER TABLE `sys_role`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_menu`
--

DROP TABLE IF EXISTS `sys_role_menu`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
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
  AUTO_INCREMENT = 26
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='角色菜单关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu`
--

LOCK TABLES `sys_role_menu` WRITE;
/*!40000 ALTER TABLE `sys_role_menu`
    DISABLE KEYS */;
INSERT INTO `sys_role_menu`
VALUES (1, 1, 1, '2024-11-20 15:45:34', '2024-11-20 15:45:34', 0),
       (2, 1, 2, '2024-11-20 15:45:34', '2024-11-20 15:45:34', 0),
       (3, 1, 3, '2024-11-20 15:45:34', '2024-11-20 15:45:34', 0),
       (4, 1, 4, '2024-11-20 15:45:34', '2024-11-20 15:45:34', 0),
       (5, 1, 5, '2024-11-20 15:45:34', '2024-11-20 15:45:34', 0),
       (6, 1, 6, '2024-11-20 15:45:34', '2024-11-20 15:45:34', 0),
       (7, 1, 7, '2024-11-20 15:45:34', '2024-11-20 15:45:34', 0),
       (8, 1, 8, '2024-11-20 15:45:34', '2024-11-20 15:45:34', 0),
       (16, 2, 1, '2024-11-20 15:46:06', '2024-11-20 15:46:06', 0),
       (17, 2, 5, '2024-11-20 15:46:06', '2024-11-20 15:46:06', 0),
       (18, 2, 6, '2024-11-20 15:46:06', '2024-11-20 15:46:06', 0),
       (19, 2, 7, '2024-11-20 15:46:06', '2024-11-20 15:46:06', 0),
       (20, 2, 8, '2024-11-20 15:46:06', '2024-11-20 15:46:06', 0),
       (21, 3, 1, '2024-11-20 15:46:06', '2024-11-20 15:46:06', 0),
       (22, 3, 5, '2024-11-20 15:46:06', '2024-11-20 15:46:06', 0),
       (23, 3, 6, '2024-11-20 15:46:06', '2024-11-20 15:46:06', 0),
       (24, 3, 7, '2024-11-20 15:46:06', '2024-11-20 15:46:06', 0),
       (25, 3, 8, '2024-11-20 15:46:06', '2024-11-20 15:46:06', 0);
/*!40000 ALTER TABLE `sys_role_menu`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
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
    PRIMARY KEY (`id`),
    UNIQUE KEY `username` (`username`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 11
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='系统用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user`
    DISABLE KEYS */;
INSERT INTO `sys_user`
VALUES (1, 'admin', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '15205986026',
        'http://106.55.168.194:9000/draw/common/603c96d5-902c-4d43-9d1b-ef48f1ef41ca-fhj.png', 1, '2024-10-31 13:40:49',
        '2024-11-19 21:17:47', 0),
       (2, 'user', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '13800000001',
        'http://106.55.168.194:9000/draw/common/41d44560-c015-4a1d-85a9-02758d04bbdb-少女头像.png', 1,
        '2024-10-31 13:40:49', '2024-11-20 16:06:10', 0),
       (3, 'member', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '15759698816',
        'https://avatars.githubusercontent.com/u/46013989?v=4&size=64', 1, '2024-10-31 13:40:49', '2024-10-31 19:18:28',
        0),
       (4, 'alice', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '13900000001',
        'https://avatars.githubusercontent.com/u/46013989?v=4&size=64', 1, '2024-10-31 17:15:00', '2024-10-31 19:18:29',
        0),
       (5, 'bob', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '14000000001',
        'https://avatars.githubusercontent.com/u/46013989?v=4&size=64', 1, '2024-10-31 17:15:00', '2024-10-31 17:15:00',
        0),
       (6, 'charlie', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '14100000001',
        'https://avatars.githubusercontent.com/u/46013989?v=4&size=64', 1, '2024-10-31 17:15:00', '2024-10-31 19:25:13',
        0),
       (7, 'gj504b', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '15205986026',
        'https://avatars.githubusercontent.com/u/46013989?v=4&size=64', 1, '2024-10-31 17:15:00', '2024-10-31 17:15:00',
        0),
       (8, 'david', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '14200000001',
        'https://avatars.githubusercontent.com/u/46013989?v=4&size=64', 1, '2024-10-31 17:15:00', '2024-10-31 17:15:00',
        0),
       (9, 'eve', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '14300000001',
        'https://avatars.githubusercontent.com/u/46013989?v=4&size=64', 1, '2024-10-31 17:15:00', '2024-11-21 19:49:48',
        0);
/*!40000 ALTER TABLE `sys_user`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
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
  AUTO_INCREMENT = 9
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='用户角色关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role`
    DISABLE KEYS */;
INSERT INTO `sys_user_role`
VALUES (1, 1, 1, NULL, NULL, 0),
       (2, 2, 2, NULL, NULL, 0),
       (3, 3, 3, NULL, NULL, 0),
       (4, 4, 2, NULL, NULL, 0),
       (5, 5, 2, NULL, NULL, 0),
       (6, 6, 3, NULL, NULL, 0),
       (7, 7, 3, NULL, NULL, 0),
       (8, 10, 2, '2024-10-31 19:25:48', '2024-10-31 19:25:48', 0);
/*!40000 ALTER TABLE `sys_user_role`
    ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2024-11-21 20:05:44