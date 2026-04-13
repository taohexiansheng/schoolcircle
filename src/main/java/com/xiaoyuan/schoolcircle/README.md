markdown
# 校圈 - 校园闲置交易平台

## 项目简介
面向高校学生的校园闲置物品交易平台，实现用户注册登录、JWT 认证、商品发布、搜索、详情等功能。

## 技术栈
- Spring Boot 4.0.4
- MyBatis 4.0.1
- MySQL 8.0
- JWT 0.12.6
- BCrypt 6.5.0
- 原生 HTML/CSS/JS

## 主要功能
- 用户注册、登录（密码 BCrypt 加密）
- JWT 认证，token 保存在 localStorage
- 商品发布（自动关联当前登录用户）
- 商品列表展示（按发布时间倒序）
- 商品搜索（标题/描述模糊匹配）
- 商品详情查看
- 拦截器统一验证 token，保护业务接口

## 快速运行

### 1. 克隆项目
```bash
git clone https://github.com/你的用户名/schoolcircle.git
cd schoolcircle
```
### 2. 创建数据库
在 MySQL 中执行：

sql
CREATE DATABASE IF NOT EXISTS schoolcircle CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
### 3. 执行建表脚本
使用 src/main/resources/schema.sql 中的 SQL 语句创建 user 和 product 表。

### 4. 配置文件
复制 application-template.properties 为 application.properties，修改数据库密码和 JWT 密钥（密钥至少32字符）。

### 5. 运行项目
运行 SchoolcircleApplication.java，访问 http://localhost:8080。

### 开发环境
JDK 17

IntelliJ IDEA

MySQL 8.0

### 作者
李江涛（长春工程学院 计算2542班）