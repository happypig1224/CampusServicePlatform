# 环境变量配置说明

## 问题描述
由于GitHub推送保护机制检测到了敏感信息（腾讯云Secret ID），为了保护您的账户安全，我们已将敏感信息从代码中移除，改为使用环境变量配置。

## 配置步骤

### 1. 创建环境变量文件
复制 `.env.example` 文件为 `.env` 文件：
```bash
cp .env.example .env
```

### 2. 配置腾讯云COS信息
编辑 `.env` 文件，填入您的腾讯云COS Secret ID和Secret Key：
```env
# 腾讯云COS配置
TENCENT_COS_SECRET_ID=您的腾讯云Secret ID
TENCENT_COS_SECRET_KEY=您的腾讯云Secret Key
```

### 3. 运行应用程序
现在您可以正常启动应用程序，系统会自动读取环境变量中的配置。

## 开发环境设置（可选）

### Windows系统
1. 右键点击"此电脑" → "属性" → "高级系统设置"
2. 点击"环境变量"按钮
3. 在"用户变量"或"系统变量"中添加：
   - 变量名：`TENCENT_COS_SECRET_ID`
   - 变量值：您的Secret ID
   - 变量名：`TENCENT_COS_SECRET_KEY`
   - 变量值：您的Secret Key

### IDE设置（IntelliJ IDEA）
1. 打开 "Run/Debug Configurations"
2. 选择您的Spring Boot应用配置
3. 在 "Environment variables" 中添加：
   ```
   TENCENT_COS_SECRET_ID=您的Secret ID
   TENCENT_COS_SECRET_KEY=您的Secret Key
   ```

## 生产环境部署
在生产环境中，请通过以下方式设置环境变量：
- 云服务器：在服务器管理控制台设置
- Docker：使用 `-e` 参数或环境变量文件
- Kubernetes：在ConfigMap或Secret中配置

## 安全提醒
- 永远不要将包含敏感信息的文件提交到版本控制系统
- 定期更换密钥以提高安全性
- 使用最小权限原则，只为应用分配必要的权限