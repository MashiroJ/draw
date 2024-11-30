# AI 图像生成系统

一个功能完整的 AI 图像生成平台，基于 Spring Boot + Vue.js 全栈开发。集成了 Stable Diffusion 模型和 ComfyUI 工作流，提供专业的 AI 图像生成服务。系统包含完整的用户体系、积分系统、社区互动等功能，支持作品分享、多级评论、画廊展示等特性。

## ✨ 系统特点

- 🎨 专业的 AI 图像生成能力
  - 支持文生图、图生图等多种生成模式
  - 集成 ComfyUI 工作流，支持自定义模型和参数
  - 灵活的提示词系统，支持正负面提示
  - 多种分辨率和采样方法可选

- 🎯 完善的用户体系
  - 基于 Sa-Token 的身份认证和权限控制
  - 积分账户系统
  - 个人主页和作品管理


- 🌈 丰富的社区功能
  - 作品画廊系统
  - 树形结构的多级评论系统
  - 评论点赞、作品分享和社交传播


## 🚀 核心功能

### 图像生成模块
- 文本生成图像 (Text-to-Image)
- 图像生成图像 (Image-to-Image)
- ComfyUI 工作流支持
- 自定义模型加载

### 社区互动模块
- 作品画廊展示
- 多级评论系统
- 评论点赞功能


### 用户中心模块
- 积分账户系统
- 个人作品管理
- 个人信息设置

## 🛠️ 技术架构

### 后端技术栈
- **核心框架：** Spring Boot 3.x
- **安全认证：** Sa-Token
- **数据库：** MySQL 8.0
- **对象存储：** MinIO
- **接口文档：** Knife4j
- **AI 引擎：** Stable Diffusion + ComfyUI
- **缓存：** Redis
- **数据库连接池：** Druid
- **API管理：** Swagger

### 前端技术栈
- **框架：** Vue 3.x
- **构建工具：** Vite
- **UI组件：** Element Plus
- **状态管理：** Pinia
- **路由：** Vue Router
- **请求处理：** Axios
- **样式：** Sass
- **代码规范：** ESLint + Prettier

## 💻 快速开始

### 环境要求
- JDK 17+
- MySQL 8.0+
- Redis 6.0+
- Maven 3.8+
- Node.js 16+
- ComfyUI
- MinIO

### 开发环境部署

1. 后端服务

```
克隆项目
git clone https://github.com/your-username/ai-image-generator.git
导入数据库
mysql -u root -p < sql/draw.sql
修改配置
cd web/src/main/resources
cp application.yml.example application.yml
启动服务
mvn spring-boot:run
```

2. 前端服务
```
进入前端目录
cd web-ui
安装依赖
npm install
启动开发服务器
npm run dev
```

## 📝 接口文档

- 接口文档地址：`http://localhost:8080/doc.html`
- 测试账号：admin/admin


## 📄 许可证

本项目采用 MIT 许可证 

## 👥 联系方式

- 作者：[Mashiro]
- 邮箱：[ashirom39@gmail.com]