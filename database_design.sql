-- 绥E站校园生活服务项目数据库设计
-- 数据库：MySQL 8.x
-- 字符集：utf8mb4
-- 排序规则：utf8mb4_unicode_ci

-- 创建数据库
CREATE DATABASE IF NOT EXISTS `suistation` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `suistation`;

-- 1. 用户模块表

-- 用户表
CREATE TABLE `user` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username` VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    `password` VARCHAR(255) NOT NULL COMMENT '密码（加密存储）',
    `nickname` VARCHAR(50) NOT NULL COMMENT '昵称',
    `gender` tinyint NOT NULL COMMENT '性别',
    `phone` VARCHAR(20) COMMENT '手机号',
    `avatar` VARCHAR(500) COMMENT '头像URL',
    `bio` VARCHAR(512) COMMENT '用户介绍',
    `role` ENUM('USER', 'ADMIN', 'SUPER_ADMIN') NOT NULL DEFAULT 'USER' COMMENT '角色',
    `status` ENUM('ACTIVE', 'INACTIVE', 'BANNED') NOT NULL DEFAULT 'ACTIVE' COMMENT '状态',
    `last_login_time` DATETIME COMMENT '最后登录时间',
    `login_count` INT DEFAULT 0 COMMENT '登录次数',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 2. 校园论坛/社交圈子模块

-- 论坛版块表
CREATE TABLE `forum_section` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '版块ID',
    `name` VARCHAR(50) NOT NULL COMMENT '版块名称',
    `description` VARCHAR(200) COMMENT '版块描述',
    `icon` VARCHAR(500) COMMENT '版块图标',
    `sort_order` INT DEFAULT 0 COMMENT '排序',
    `status` ENUM('ACTIVE', 'INACTIVE') NOT NULL DEFAULT 'ACTIVE' COMMENT '状态',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='论坛版块表';

-- 帖子表
CREATE TABLE `forum_post` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '帖子ID',
    `section_id` BIGINT NOT NULL COMMENT '版块ID',
    `user_id` BIGINT NOT NULL COMMENT '发帖用户ID',
    `title` VARCHAR(200) NOT NULL COMMENT '帖子标题',
    `content` TEXT NOT NULL COMMENT '帖子内容',
    `images` JSON COMMENT '图片列表（JSON数组）',
    `view_count` INT DEFAULT 0 COMMENT '浏览数',
    `reply_count` INT DEFAULT 0 COMMENT '回复数',
    `like_count` INT DEFAULT 0 COMMENT '点赞数',
    `collect_count` INT DEFAULT 0 COMMENT '收藏数',
    `is_top` TINYINT(1) DEFAULT 0 COMMENT '是否置顶',
    `is_essence` TINYINT(1) DEFAULT 0 COMMENT '是否精华',
    `status` ENUM('PUBLISHED', 'DRAFT', 'DELETED', 'AUDITING') NOT NULL DEFAULT 'PUBLISHED' COMMENT '状态',
    `last_reply_time` DATETIME COMMENT '最后回复时间',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    FOREIGN KEY (`section_id`) REFERENCES `forum_section`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='帖子表';

-- 帖子回复表
CREATE TABLE `forum_reply` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '回复ID',
    `post_id` BIGINT NOT NULL COMMENT '帖子ID',
    `user_id` BIGINT NOT NULL COMMENT '回复用户ID',
    `parent_id` BIGINT DEFAULT NULL COMMENT '父回复ID（用于嵌套回复）',
    `content` TEXT NOT NULL COMMENT '回复内容',
    `like_count` INT DEFAULT 0 COMMENT '点赞数',
    `status` ENUM('PUBLISHED', 'DELETED') NOT NULL DEFAULT 'PUBLISHED' COMMENT '状态',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    FOREIGN KEY (`post_id`) REFERENCES `forum_post`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`parent_id`) REFERENCES `forum_reply`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='帖子回复表';

-- 帖子点赞表
CREATE TABLE `forum_like` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '点赞ID',
    `post_id` BIGINT NOT NULL COMMENT '帖子ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    FOREIGN KEY (`post_id`) REFERENCES `forum_post`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE,
    UNIQUE KEY `uk_post_user` (`post_id`, `user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='帖子点赞表';

-- 帖子收藏表
CREATE TABLE `forum_collect` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '收藏ID',
    `post_id` BIGINT NOT NULL COMMENT '帖子ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    FOREIGN KEY (`post_id`) REFERENCES `forum_post`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE,
    UNIQUE KEY `uk_post_user` (`post_id`, `user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='帖子收藏表';

-- 3. 二手交易市场模块

-- 商品分类表
CREATE TABLE `product_category` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '分类ID',
    `name` VARCHAR(50) NOT NULL COMMENT '分类名称',
    `icon` VARCHAR(500) COMMENT '分类图标',
    `sort_order` INT DEFAULT 0 COMMENT '排序',
    `status` ENUM('ACTIVE', 'INACTIVE') NOT NULL DEFAULT 'ACTIVE' COMMENT '状态',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品分类表';

-- 商品表
CREATE TABLE `product` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '商品ID',
    `category_id` BIGINT NOT NULL COMMENT '分类ID',
    `user_id` BIGINT NOT NULL COMMENT '发布用户ID',
    `title` VARCHAR(200) NOT NULL COMMENT '商品标题',
    `description` TEXT NOT NULL COMMENT '商品描述',
    `images` JSON NOT NULL COMMENT '商品图片（JSON数组）',
    `price` DECIMAL(10,2) NOT NULL COMMENT '价格',
    `original_price` DECIMAL(10,2) COMMENT '原价',
    `contact_phone` VARCHAR(20) COMMENT '联系电话',
    `contact_wechat` VARCHAR(100) COMMENT '联系微信',
    `location` VARCHAR(200) COMMENT '交易地点',
    `view_count` INT DEFAULT 0 COMMENT '浏览数',
    `collect_count` INT DEFAULT 0 COMMENT '收藏数',
    `status` ENUM('AUDITING', 'ON_SALE', 'SOLD', 'OFF_SHELF') NOT NULL DEFAULT 'AUDITING' COMMENT '状态',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    FOREIGN KEY (`category_id`) REFERENCES `product_category`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品表';

-- 商品收藏表
CREATE TABLE `product_collect` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '收藏ID',
    `product_id` BIGINT NOT NULL COMMENT '商品ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    FOREIGN KEY (`product_id`) REFERENCES `product`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE,
    UNIQUE KEY `uk_product_user` (`product_id`, `user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品收藏表';

-- 商品评论表
CREATE TABLE `product_comment` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '评论ID',
    `product_id` BIGINT NOT NULL COMMENT '商品ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `content` TEXT NOT NULL COMMENT '评论内容',
    `parent_id` BIGINT DEFAULT NULL COMMENT '父评论ID',
    `status` ENUM('PUBLISHED', 'DELETED') NOT NULL DEFAULT 'PUBLISHED' COMMENT '状态',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    FOREIGN KEY (`product_id`) REFERENCES `product`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`parent_id`) REFERENCES `product_comment`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品评论表';

-- 4. 订单模块

-- 订单表
CREATE TABLE `order` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '订单ID',
    `order_no` VARCHAR(32) NOT NULL UNIQUE COMMENT '订单编号',
    `product_id` BIGINT NOT NULL COMMENT '商品ID',
    `buyer_id` BIGINT NOT NULL COMMENT '买家ID',
    `seller_id` BIGINT NOT NULL COMMENT '卖家ID',
    `price` DECIMAL(10,2) NOT NULL COMMENT '订单价格',
    `quantity` INT DEFAULT 1 COMMENT '数量',
    `total_amount` DECIMAL(10,2) NOT NULL COMMENT '总金额',
    `receiver_name` VARCHAR(50) NOT NULL COMMENT '收货人姓名',
    `receiver_phone` VARCHAR(20) NOT NULL COMMENT '收货人电话',
    `receiver_address` VARCHAR(500) NOT NULL COMMENT '收货地址',
    `payment_no` VARCHAR(64) COMMENT '支付凭证编号',
    `payment_time` DATETIME COMMENT '支付时间',
    `status` ENUM('PENDING_PAYMENT', 'PENDING_CONFIRM', 'CONFIRMED', 'DELIVERING', 'COMPLETED', 'CANCELLED', 'TIMEOUT') NOT NULL DEFAULT 'PENDING_PAYMENT' COMMENT '订单状态',
    `expire_time` DATETIME NOT NULL COMMENT '订单过期时间',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    FOREIGN KEY (`product_id`) REFERENCES `product`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`buyer_id`) REFERENCES `user`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`seller_id`) REFERENCES `user`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单表';

-- 5. 失物招领模块

-- 失物招领表
CREATE TABLE `lost_and_found` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id` BIGINT NOT NULL COMMENT '发布用户ID',
    `type` ENUM('LOST', 'FOUND') NOT NULL COMMENT '类型（丢失/拾到）',
    `title` VARCHAR(200) NOT NULL COMMENT '标题',
    `description` TEXT NOT NULL COMMENT '详细描述',
    `item_type` VARCHAR(50) NOT NULL COMMENT '物品类型',
    `lost_found_time` DATETIME NOT NULL COMMENT '丢失/拾到时间',
    `location` VARCHAR(200) NOT NULL COMMENT '地点',
    `images` JSON COMMENT '图片列表',
    `urgency` ENUM('NORMAL', 'URGENT') NOT NULL DEFAULT 'NORMAL' COMMENT '紧急程度',
    `contact_info` VARCHAR(200) NOT NULL COMMENT '联系方式',
    `status` ENUM('PENDING', 'RESOLVED', 'CLOSED') NOT NULL DEFAULT 'PENDING' COMMENT '状态',
    `resolve_time` DATETIME COMMENT '解决时间',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='失物招领表';

-- 感谢信表
CREATE TABLE `thank_you_note` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '感谢信ID',
    `lost_found_id` BIGINT NOT NULL COMMENT '失物招领ID',
    `user_id` BIGINT NOT NULL COMMENT '发布用户ID',
    `title` VARCHAR(200) NOT NULL COMMENT '标题',
    `content` TEXT NOT NULL COMMENT '内容',
    `images` JSON COMMENT '图片列表',
    `is_public` TINYINT(1) DEFAULT 1 COMMENT '是否公开',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    FOREIGN KEY (`lost_found_id`) REFERENCES `lost_and_found`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='感谢信表';

-- 6. 校园跑腿/代办服务模块

-- 跑腿任务表
CREATE TABLE `errand_task` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '任务ID',
    `user_id` BIGINT NOT NULL COMMENT '发布用户ID',
    `type` ENUM('PARCEL', 'FOOD', 'PRINT', 'OTHER') NOT NULL COMMENT '任务类型',
    `title` VARCHAR(200) NOT NULL COMMENT '任务标题',
    `description` TEXT NOT NULL COMMENT '任务描述',
    `reward` DECIMAL(8,2) NOT NULL COMMENT '酬金（必须≥3元）',
    `location_from` VARCHAR(200) NOT NULL COMMENT '起始地点',
    `location_to` VARCHAR(200) NOT NULL COMMENT '目的地',
    `deadline` DATETIME NOT NULL COMMENT '截止时间',
    `contact_info` VARCHAR(200) NOT NULL COMMENT '联系方式',
    `status` ENUM('PENDING', 'ACCEPTED', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED', 'PAID') NOT NULL DEFAULT 'PENDING' COMMENT '状态',
    `acceptor_id` BIGINT COMMENT '接单用户ID',
    `accept_time` DATETIME COMMENT '接单时间',
    `complete_time` DATETIME COMMENT '完成时间',
    `proof_images` JSON COMMENT '完成证明图片',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`acceptor_id`) REFERENCES `user`(`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='跑腿任务表';

-- 跑腿任务评价表
CREATE TABLE `errand_review` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '评价ID',
    `task_id` BIGINT NOT NULL COMMENT '任务ID',
    `reviewer_id` BIGINT NOT NULL COMMENT '评价用户ID',
    `reviewee_id` BIGINT NOT NULL COMMENT '被评价用户ID',
    `rating` TINYINT NOT NULL COMMENT '评分（1-5）',
    `content` TEXT COMMENT '评价内容',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    FOREIGN KEY (`task_id`) REFERENCES `errand_task`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`reviewer_id`) REFERENCES `user`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`reviewee_id`) REFERENCES `user`(`id`) ON DELETE CASCADE,
    UNIQUE KEY `uk_task_reviewer` (`task_id`, `reviewer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='跑腿任务评价表';

-- 7. 资源共享模块

-- 资源分类表
CREATE TABLE `resource_category` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '分类ID',
    `name` VARCHAR(50) NOT NULL COMMENT '分类名称',
    `type` ENUM('NOTE', 'HOMEWORK', 'EXAM', 'STUDY_GROUP') NOT NULL COMMENT '资源类型',
    `sort_order` INT DEFAULT 0 COMMENT '排序',
    `status` ENUM('ACTIVE', 'INACTIVE') NOT NULL DEFAULT 'ACTIVE' COMMENT '状态',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='资源分类表';

-- 资源表
CREATE TABLE `resource` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '资源ID',
    `category_id` BIGINT NOT NULL COMMENT '分类ID',
    `user_id` BIGINT NOT NULL COMMENT '发布用户ID',
    `title` VARCHAR(200) NOT NULL COMMENT '资源标题',
    `description` TEXT COMMENT '资源描述',
    `file_url` VARCHAR(500) COMMENT '文件URL',
    `file_size` BIGINT COMMENT '文件大小（字节）',
    `file_type` VARCHAR(50) COMMENT '文件类型',
    `download_count` INT DEFAULT 0 COMMENT '下载次数',
    `view_count` INT DEFAULT 0 COMMENT '浏览数',
    `course_name` VARCHAR(100) COMMENT '课程名称',
    `teacher_name` VARCHAR(50) COMMENT '教师姓名',
    `semester` VARCHAR(20) COMMENT '学期',
    `status` ENUM('PUBLISHED', 'DRAFT', 'DELETED') NOT NULL DEFAULT 'PUBLISHED' COMMENT '状态',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    FOREIGN KEY (`category_id`) REFERENCES `resource_category`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='资源表';

-- 学习小组表
CREATE TABLE `study_group` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '小组ID',
    `user_id` BIGINT NOT NULL COMMENT '创建用户ID',
    `title` VARCHAR(200) NOT NULL COMMENT '小组标题',
    `description` TEXT NOT NULL COMMENT '小组描述',
    `subject` VARCHAR(100) NOT NULL COMMENT '学习科目',
    `target` ENUM('SELF_STUDY', 'EXAM_PREP', 'PROJECT', 'OTHER') NOT NULL COMMENT '学习目标',
    `max_members` INT DEFAULT 10 COMMENT '最大成员数',
    `current_members` INT DEFAULT 1 COMMENT '当前成员数',
    `meeting_time` VARCHAR(200) COMMENT '活动时间',
    `meeting_location` VARCHAR(200) COMMENT '活动地点',
    `status` ENUM('RECRUITING', 'FULL', 'INACTIVE') NOT NULL DEFAULT 'RECRUITING' COMMENT '状态',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='学习小组表';

-- 小组成员表
CREATE TABLE `study_group_member` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '成员ID',
    `group_id` BIGINT NOT NULL COMMENT '小组ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `role` ENUM('CREATOR', 'MEMBER') NOT NULL DEFAULT 'MEMBER' COMMENT '角色',
    `join_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '加入时间',
    PRIMARY KEY (`id`),
    FOREIGN KEY (`group_id`) REFERENCES `study_group`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE,
    UNIQUE KEY `uk_group_user` (`group_id`, `user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='小组成员表';

-- 8. 聊天模块

-- 聊天会话表
CREATE TABLE `chat_session` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '会话ID',
    `type` ENUM('PRIVATE', 'GROUP') NOT NULL COMMENT '会话类型',
    `name` VARCHAR(100) COMMENT '会话名称（群聊时使用）',
    `avatar` VARCHAR(500) COMMENT '会话头像',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='聊天会话表';

-- 会话成员表
CREATE TABLE `chat_session_member` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '成员ID',
    `session_id` BIGINT NOT NULL COMMENT '会话ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `nickname` VARCHAR(50) COMMENT '在会话中的昵称',
    `role` ENUM('OWNER', 'ADMIN', 'MEMBER') NOT NULL DEFAULT 'MEMBER' COMMENT '角色',
    `join_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '加入时间',
    PRIMARY KEY (`id`),
    FOREIGN KEY (`session_id`) REFERENCES `chat_session`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE,
    UNIQUE KEY `uk_session_user` (`session_id`, `user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='会话成员表';

-- 聊天消息表
CREATE TABLE `chat_message` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '消息ID',
    `session_id` BIGINT NOT NULL COMMENT '会话ID',
    `sender_id` BIGINT NOT NULL COMMENT '发送者ID',
    `content` TEXT NOT NULL COMMENT '消息内容',
    `message_type` ENUM('TEXT', 'IMAGE', 'FILE', 'SYSTEM') NOT NULL DEFAULT 'TEXT' COMMENT '消息类型',
    `file_url` VARCHAR(500) COMMENT '文件URL',
    `file_name` VARCHAR(200) COMMENT '文件名',
    `file_size` BIGINT COMMENT '文件大小',
    `is_read` TINYINT(1) DEFAULT 0 COMMENT '是否已读',
    `send_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
    PRIMARY KEY (`id`),
    FOREIGN KEY (`session_id`) REFERENCES `chat_session`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`sender_id`) REFERENCES `user`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='聊天消息表';

-- 9. 系统管理模块

-- 系统配置表
CREATE TABLE `system_config` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '配置ID',
    `config_key` VARCHAR(100) NOT NULL UNIQUE COMMENT '配置键',
    `config_value` TEXT NOT NULL COMMENT '配置值',
    `description` VARCHAR(200) COMMENT '配置描述',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统配置表';

-- 操作日志表
CREATE TABLE `operation_log` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '日志ID',
    `user_id` BIGINT COMMENT '操作用户ID',
    `module` VARCHAR(50) NOT NULL COMMENT '操作模块',
    `operation` VARCHAR(100) NOT NULL COMMENT '操作内容',
    `ip` VARCHAR(50) COMMENT 'IP地址',
    `user_agent` VARCHAR(500) COMMENT '用户代理',
    `params` TEXT COMMENT '请求参数',
    `result` ENUM('SUCCESS', 'FAILED') NOT NULL COMMENT '操作结果',
    `error_message` TEXT COMMENT '错误信息',
    `execute_time` BIGINT COMMENT '执行时间（毫秒）',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='操作日志表';

-- 数据库使用说明：
-- 1. 执行此SQL文件创建数据库和表结构
-- 2. 修改application.properties中的数据库连接信息
-- 3. 使用MyBatis-Plus进行数据访问层开发
-- 4. 注意：密码字段需要在实际使用时进行加密处理
-- 5. JSON字段在MySQL 8.x中支持良好，可以直接存储数组数据

-- 索引优化建议：
-- 1. 根据实际查询模式调整索引
-- 2. 定期分析慢查询日志
-- 3. 考虑分区表处理大数据量
-- 4. 使用explain分析查询性能

-- 安全注意事项：
-- 1. 定期备份数据库
-- 2. 使用强密码策略
-- 3. 限制数据库用户权限
-- 4. 启用SQL注入防护
-- 5. 定期更新MySQL版本