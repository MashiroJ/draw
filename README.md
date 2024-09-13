- ### 基于Springboot的Stable Diffusion图像生成系统

  该系统描述了图像生成和管理系统的功能需求，涵盖各个模块的核心功能。系统的目标是提供一个集成用户管理、图像生成与调整、风格选择、多语言支持、结果反馈和画廊系统的平台。

  #### 1. 用户系统

  - **用户注册和登录**：用户可以通过注册页面注册新账户，并通过登录页面登录系统。
  - **用户认证和授权**：系统采用rbac权限模型，不同角色（管理员、普通用户）具有不同的权限。使用Sa-Token作为权限框架。
  - **用户管理**：管理员可以添加、删除和修改用户信息，分配用户角色以及。

  #### 2. 图像生成模块

  - **文生图接口**：用户可以通过输入详细的文本描述，生成相应的图像内容。
  - **图生图接口**：除了文本描述，用户上传一个图像，通过图像→图像生成功能来微调现有的图片。

  #### 3. 画廊系统模块

  - **画廊系统**：创建画廊功能，允许用户在平台上分享他们生成的图像。

  #### 4. 存储系统模块

  - **存储系统**：允许用户保存生成的图像，支持将生成的图像存储到云存储（minio）。

  ### 功能需求总结表格

  | 模块         | 功能描述                             | 子功能                                   | 角色/用户 |
  | ------------ | ------------------------------------ | ---------------------------------------- | --------- |
  | 用户系统     | 实现用户认证与授权，提供个人账户系统 | 用户注册和登录、用户认证和授权、用户管理 | 管理员    |
  | 图像生成模块 | 通过文本和图像生成新的图像           | 文生图接口，图生图接口                   | 所有用户  |
  | 画廊系统模块 | 创建和管理图像画廊，分享生成的图像   | 画廊创建与管理                           | 所有用户  |
  | 存储系统模块 | 允许用户保存生成的图像，支持存储到云 | 云存储（minio）                          | 所有用户  |

### 总结

通过上述功能需求和总结表格，可以清晰了解图像生成和管理系统的核心模块和功能。每个模块都有其独立的功能目标和用户角色定义，确保系统的完整性和可用性。这些功能需求将为系统开发和测试提供明确的指导方向，确保最终产品能够满足用户的需求。

### 二、数据库设计

# 

#### 1、用户表 (users)

存储系统用户信息

```sql
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID，主键',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名，唯一',
    email VARCHAR(100) NOT NULL UNIQUE COMMENT '用户邮箱，唯一',
    password VARCHAR(255) NOT NULL COMMENT '用户密码，加密存储',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '用户创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '用户信息更新时间'
);
```

#### 2、角色表 (roles)

定义系统中的用户角色

```sql
CREATE TABLE roles (
    role_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '角色ID，主键',
    role_name VARCHAR(50) NOT NULL UNIQUE COMMENT '角色名称，唯一',
    description VARCHAR(255) COMMENT '角色描述'
);
```

#### 3、权限表 (permissions)

定义系统中的权限

```sql
CREATE TABLE permissions (
    permission_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '权限ID，主键',
    permission_name VARCHAR(50) NOT NULL UNIQUE COMMENT '权限名称，唯一',
    description VARCHAR(255) COMMENT '权限描述'
);
```

#### 4、用户-角色关联表 (user_roles)

用户和角色的多对多关联表

```sql
CREATE TABLE user_roles (
    user_id INT COMMENT '用户ID，外键关联users表',
    role_id INT COMMENT '角色ID，外键关联roles表',
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(role_id) ON DELETE CASCADE
);
```

#### 5、角色-权限关联表 (role_permissions)

角色和权限的多对多关联表

```sql
CREATE TABLE role_permissions (
    role_id INT COMMENT '角色ID，外键关联roles表',
    permission_id INT COMMENT '权限ID，外键关联permissions表',
    PRIMARY KEY (role_id, permission_id),
    FOREIGN KEY (role_id) REFERENCES roles(role_id) ON DELETE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES permissions(permission_id) ON DELETE CASCADE
);
```

#### 6. 画廊表 (gallery)

```sql
CREATE TABLE gallery (
    gallery_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '画廊记录唯一标识符',
    user_id INT COMMENT '用户ID',
    title VARCHAR(100) NOT NULL COMMENT '画廊名称',
    description TEXT COMMENT '画廊描述',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) COMMENT='画廊表';
```

#### 7. 存储系统表 (storage)

```sql
CREATE TABLE storage (
    storage_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '存储信息唯一标识符',
    user_id INT COMMENT '用户ID',
    image_url VARCHAR(255) COMMENT '存储的图像路径',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '保存时间'
) COMMENT='存储系统表';
```

## 总结

以上数据库设计支持用户管理及RBAC权限控制模型，能够满足Stable Diffusion图像生成系统的核心需求。