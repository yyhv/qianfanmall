# 自动部署配置指南

## 概述

本项目使用 GitHub Actions + Docker 实现自动部署。支持多环境（staging/production）部署。

## 一、GitHub Secrets 配置

### 1. Repository Secrets（所有环境共用）

进入 GitHub 仓库 → Settings → Secrets and variables → Actions → Repository secrets

| Secret 名称 | 说明 | 示例 |
|------------|------|------|
| `DOCKER_USERNAME` | Docker Hub 用户名 | `myusername` |
| `DOCKER_PASSWORD` | Docker Hub Access Token | `dckr_pat_xxxx` |

### 2. Environment Secrets（每个环境独立）

首先创建环境：Settings → Environments → New environment

创建三个环境：
- `prod`（生产环境）
- `test`（测试环境）
- `dev`（开发环境）

然后在每个环境中添加：

| Secret 名称 | 说明 |
|------------|------|
| `SERVER_HOST` | 该环境的服务器 IP |
| `SERVER_USER` | SSH 登录用户名 |
| `SSH_PRIVATE_KEY` | SSH 私钥内容 |

**配置示例：**

```
prod 环境:
  SERVER_HOST = 123.45.67.89
  SERVER_USER = deploy
  SSH_PRIVATE_KEY = -----BEGIN RSA PRIVATE KEY-----...

test 环境:
  SERVER_HOST = 98.76.54.32
  SERVER_USER = deploy
  SSH_PRIVATE_KEY = -----BEGIN RSA PRIVATE KEY-----...

dev 环境:
  SERVER_HOST = 192.168.1.100
  SERVER_USER = deploy
  SSH_PRIVATE_KEY = -----BEGIN RSA PRIVATE KEY-----...
```

### 获取 Docker Hub Access Token

1. 登录 [Docker Hub](https://hub.docker.com)
2. 点击右上角头像 → Account Settings → Security
3. 点击 New Access Token
4. 勾选 Read, Write, Delete 权限
5. 复制生成的 Token（只显示一次）

### 生成 SSH 密钥

```bash
# 在本地生成密钥对
ssh-keygen -t rsa -b 4096 -C "github-actions" -f github-actions-key

# 将公钥添加到服务器
ssh-copy-id -i github-actions-key.pub user@your-server-ip

# 复制私钥内容到 GitHub Secrets
cat github-actions-key
```

## 二、服务器环境准备

### 1. 安装 Docker

```bash
# Ubuntu/Debian
curl -fsSL https://get.docker.com | sh
sudo usermod -aG docker $USER

# 重新登录使权限生效
exit
# 重新 SSH 登录
```

### 2. 安装 Docker Compose

```bash
sudo curl -L "https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
```

### 3. 创建部署目录

```bash
sudo mkdir -p /home/deploy/qianfanmall
sudo chown $USER:$USER /home/deploy/qianfanmall
cd /home/deploy/qianfanmall

# 创建必要目录
mkdir -p storage logs backup init-sql
```

### 4. 创建环境变量文件

```bash
cat > .env << 'EOF'
DOCKER_USERNAME=your-docker-hub-username
MYSQL_ROOT_PASSWORD=your-secure-password
MYSQL_USER=qianfanmall
MYSQL_PASSWORD=your-secure-password
EOF

chmod 600 .env
```

### 5. 复制 docker-compose.prod.yml 到服务器

```bash
# 在服务器上创建配置文件
cat > docker-compose.prod.yml << 'EOF'
version: '3.8'

services:
  mysql:
    image: mysql:8.0
    container_name: qianfanmall-mysql
    restart: always
    environment:
      MYSQL_ROOT_PASSWORD: ${MYSQL_ROOT_PASSWORD}
      MYSQL_DATABASE: qianfanmall
      MYSQL_USER: ${MYSQL_USER}
      MYSQL_PASSWORD: ${MYSQL_PASSWORD}
    command:
      --character-set-server=utf8mb4
      --collation-server=utf8mb4_general_ci
      --default-authentication-plugin=mysql_native_password
    volumes:
      - ./data/mysql:/var/lib/mysql
      - ./init-sql:/docker-entrypoint-initdb.d:ro
    ports:
      - "3306:3306"
    networks:
      - qianfanmall-network
    healthcheck:
      test: ["CMD", "mysqladmin", "ping", "-h", "localhost"]
      interval: 10s
      timeout: 5s
      retries: 5

  qianfanmall:
    image: ${DOCKER_USERNAME}/qianfanmall:latest
    container_name: qianfanmall
    restart: always
    ports:
      - "8080:8080"
    volumes:
      - ./storage:/storage
      - ./logs:/logs
      - ./backup:/backup
    environment:
      - TZ=Asia/Shanghai
      - SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/qianfanmall?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true&useSSL=false
      - SPRING_DATASOURCE_USERNAME=${MYSQL_USER}
      - SPRING_DATASOURCE_PASSWORD=${MYSQL_PASSWORD}
    depends_on:
      mysql:
        condition: service_healthy
    networks:
      - qianfanmall-network
    healthcheck:
      test: ["CMD", "curl", "-f", "http://localhost:8080/admin/index/index"]
      interval: 30s
      timeout: 10s
      retries: 3
      start_period: 60s

networks:
  qianfanmall-network:
    driver: bridge
EOF
```

### 6. 初始化数据库

```bash
# 将 SQL 文件放到 init-sql 目录
# 项目中的 SQL 文件位于: qianfanmall-db/sql/

# 首次启动时会自动执行 init-sql 目录下的 .sql 文件
# 或者手动导入：
# docker exec -i qianfanmall-mysql mysql -u root -p${MYSQL_ROOT_PASSWORD} qianfanmall < your-dump.sql
```

## 三、首次部署

### 自动部署（推送到 master）

推送到 master 分支会自动部署到 **prod** 环境：

```bash
git push origin master
```

### 手动选择环境部署

1. 进入 GitHub 仓库 → Actions → Build and Deploy
2. 点击 **Run workflow**
3. 选择要部署的环境（prod / test / dev）
4. 点击 **Run workflow**

### 或者推送代码触发

```bash
git add .
git commit -m "feat: 配置自动部署"
git push origin master
```

## 四、验证部署

### 1. 检查容器状态

```bash
docker-compose -f docker-compose.prod.yml ps
```

### 2. 查看应用日志

```bash
docker logs qianfanmall -f
```

### 3. 访问服务

- 商城首页: `http://服务器IP:8080/wx/index/index`
- 管理后台: `http://服务器IP:8080/admin/index/index`
- 管理界面: `http://服务器IP:8080/#/login`

## 五、常用命令

```bash
# 查看容器状态
docker-compose -f docker-compose.prod.yml ps

# 查看日志
docker logs qianfanmall -f --tail 100

# 重启服务
docker-compose -f docker-compose.prod.yml restart qianfanmall

# 停止所有服务
docker-compose -f docker-compose.prod.yml down

# 启动所有服务
docker-compose -f docker-compose.prod.yml up -d

# 进入容器
docker exec -it qianfanmall /bin/bash

# 清理旧镜像
docker image prune -af
```

## 六、故障排查

### 容器启动失败

```bash
# 查看详细日志
docker logs qianfanmall

# 检查数据库连接
docker exec qianfanmall-mysql mysql -u qianfanmall -p
```

### 数据库连接失败

1. 确认 MySQL 容器已启动并健康
2. 检查环境变量配置是否正确
3. 确认网络连接正常

### 前端资源 404

确认前端已正确构建并打包到 jar 中，检查 `qianfanmall-admin/dist` 目录内容。

## 七、安全建议

1. 使用强密码
2. 定期更新服务器系统
3. 配置防火墙，只开放必要端口（80, 443, 8080, 22）
4. 使用 HTTPS（可配置 nginx 反向代理 + Let's Encrypt）
5. 定期备份数据库
