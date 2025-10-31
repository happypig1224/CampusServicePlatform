-- 绥E站校园生活服务项目测试数据
-- 此文件包含用于开发和测试的示例数据

USE `suistation`;

-- 1. 插入测试用户数据
INSERT INTO `user` (`username`, `password`, `nickname`, `email`, `phone`, `avatar`, `role`, `status`) VALUES 
('student001', '$2a$10$rOz0rXQ6qY6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6', '张三', 'zhangsan@suistation.com', '13800000001', 'avatar1.png', 'USER', 'ACTIVE'),
('student002', '$2a$10$rOz0rXQ6qY6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6', '李四', 'lisi@suistation.com', '13800000002', 'avatar2.png', 'USER', 'ACTIVE'),
('student003', '$2a$10$rOz0rXQ6qY6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6', '王五', 'wangwu@suistation.com', '13800000003', 'avatar3.png', 'USER', 'ACTIVE'),
('student004', '$2a$10$rOz0rXQ6qY6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6', '赵六', 'zhaoliu@suistation.com', '13800000004', 'avatar4.png', 'USER', 'ACTIVE'),
('teacher001', '$2a$10$rOz0rXQ6qY6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6', '王老师', 'wangteacher@suistation.com', '13800000005', 'avatar5.png', 'ADMIN', 'ACTIVE'),
('manager001', '$2a$10$rOz0rXQ6qY6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6Y6', '系统管理员', 'manager@suistation.com', '13800000006', 'avatar6.png', 'ADMIN', 'ACTIVE');

-- 2. 插入论坛版块测试数据
INSERT INTO `forum_section` (`name`, `description`, `icon`, `sort_order`) VALUES 
('学习交流', '学习经验分享、课程讨论、学术交流', 'study.png', 1),
('社团活动', '社团招新、活动通知、兴趣小组', 'club.png', 2),
('情感天地', '心情分享、情感交流、生活感悟', 'emotion.png', 3),
('校园通知', '学校通知、重要公告、活动信息', 'notice.png', 4),
('二手交易', '闲置物品交易、求购信息', 'trade.png', 5),
('失物招领', '丢失物品登记、拾物招领', 'lost.png', 6);

-- 3. 插入论坛帖子测试数据
INSERT INTO `forum_post` (`section_id`, `user_id`, `title`, `content`, `images`, `view_count`, `reply_count`, `like_count`, `collect_count`, `status`) VALUES 
(1, 2, '高等数学学习方法分享', '大家好，我是大二的学生，想和大家分享一下高等数学的学习经验...', '["math1.jpg", "math2.jpg"]', 156, 23, 45, 12, 'PUBLISHED'),
(1, 3, '数据结构与算法学习心得', '最近在学习数据结构，感觉链表和树结构比较难理解...', '["ds1.jpg"]', 89, 15, 32, 8, 'PUBLISHED'),
(2, 2, '篮球社招新啦！', '校篮球社开始招新啦！欢迎喜欢篮球的同学加入我们...', '["basketball1.jpg", "basketball2.jpg"]', 234, 45, 78, 23, 'PUBLISHED'),
(2, 4, '音乐社周末音乐会', '本周六晚上7点，音乐社将在音乐厅举办音乐会...', '["music1.jpg"]', 167, 32, 56, 18, 'PUBLISHED'),
(3, 3, '最近心情不太好', '最近学习压力比较大，感觉有点焦虑...', NULL, 67, 12, 23, 5, 'PUBLISHED'),
(3, 2, '大学生活感悟', '大学四年很快就要过去了，有很多感悟想和大家分享...', '["life1.jpg"]', 123, 28, 45, 9, 'PUBLISHED'),
(4, 5, '关于期末考试安排的通知', '各位同学请注意，期末考试时间安排如下...', NULL, 345, 56, 89, 34, 'PUBLISHED'),
(4, 6, '校园网络维护通知', '本周六凌晨2点至6点，校园网络将进行维护...', NULL, 278, 34, 67, 21, 'PUBLISHED');

-- 3. 插入帖子回复测试数据
INSERT INTO `forum_reply` (`post_id`, `user_id`, `content`, `like_count`) VALUES 
(1, 3, '感谢分享！我也觉得高数很难，你的方法很有帮助', 5),
(1, 4, '请问有没有推荐的参考书？', 2),
(1, 2, '我推荐《高等数学辅导》这本书，内容很详细', 8),
(2, 2, '数据结构确实需要多练习，建议多做算法题', 6),
(2, 4, '有没有好的在线学习平台推荐？', 3),
(3, 3, '我想加入篮球社！请问怎么报名？', 4),
(3, 5, '篮球社的活动时间是什么时候？', 2),
(4, 2, '音乐会需要门票吗？', 3),
(4, 4, '免费入场，欢迎大家来欣赏！', 7),
(5, 2, '加油！大学生活都会有压力的，适当放松一下', 9),
(5, 5, '可以找心理咨询师聊聊，学校有免费的心理咨询服务', 6),
(6, 3, '同感，时间过得真快', 4),
(6, 4, '珍惜大学时光！', 5);

-- 4. 插入帖子点赞测试数据
INSERT INTO `forum_like` (`post_id`, `user_id`) VALUES 
(1, 2), (1, 3), (1, 4), (1, 5),
(2, 2), (2, 3), (2, 6),
(3, 2), (3, 3), (3, 4), (3, 5), (3, 6),
(4, 2), (4, 3), (4, 4),
(5, 2), (5, 3),
(6, 3), (6, 4), (6, 5);

-- 5. 插入帖子收藏测试数据
INSERT INTO `forum_collect` (`post_id`, `user_id`) VALUES 
(1, 2), (1, 3),
(2, 2), (2, 4),
(3, 3), (3, 5),
(4, 2), (4, 6),
(5, 3),
(6, 4), (6, 5);

-- 6. 插入商品分类测试数据
INSERT INTO `product_category` (`name`, `icon`, `sort_order`) VALUES 
('教材书籍', 'book.png', 1),
('数码产品', 'digital.png', 2),
('生活用品', 'life.png', 3),
('运动器材', 'sports.png', 4),
('服装鞋帽', 'clothes.png', 5),
('其他物品', 'other.png', 6);

-- 7. 插入商品测试数据
INSERT INTO `product` (`category_id`, `user_id`, `title`, `description`, `images`, `price`, `original_price`, `contact_phone`, `contact_wechat`, `location`, `status`) VALUES 
(1, 2, '高等数学教材（第九版）', '几乎全新，无笔记划痕，适合大一新生使用', '["book1.jpg", "book2.jpg"]', 25.00, 45.00, '13800000001', 'zhangsan123', '图书馆门口', 'ON_SALE'),
(1, 3, 'Java编程思想（第五版）', '经典编程书籍，有少量笔记，不影响阅读', '["java1.jpg"]', 35.00, 68.00, '13800000002', 'lisi456', '计算机学院', 'ON_SALE'),
(2, 4, '二手笔记本电脑', '联想ThinkPad，i5处理器，8G内存，256G固态硬盘', '["laptop1.jpg", "laptop2.jpg", "laptop3.jpg"]', 1200.00, 2500.00, '13800000003', 'wangwu789', '宿舍楼3号楼', 'ON_SALE'),
(2, 2, '无线蓝牙耳机', '品牌：小米，使用半年，音质良好', '["earphone1.jpg"]', 80.00, 199.00, '13800000001', 'zhangsan123', '教学楼A座', 'ON_SALE'),
(3, 3, '台灯', 'LED护眼台灯，可调节亮度', '["lamp1.jpg"]', 25.00, 45.00, '13800000002', 'lisi456', '宿舍区', 'SOLD'),
(3, 4, '保温杯', '不锈钢保温杯，500ml容量', '["cup1.jpg"]', 15.00, 30.00, '13800000003', 'wangwu789', '食堂门口', 'ON_SALE'),
(4, 2, '运动鞋', '耐克运动鞋，42码，穿过几次', '["shoes1.jpg", "shoes2.jpg"]', 150.00, 399.00, '13800000001', 'zhangsan123', '体育场', 'ON_SALE'),
(5, 3, '羽毛球拍', '尤尼克斯羽毛球拍，带球包', '["badminton1.jpg"]', 120.00, 280.00, '13800000002', 'lisi456', '体育馆', 'AUDITING');

-- 7. 插入商品收藏测试数据
INSERT INTO `product_collect` (`product_id`, `user_id`) VALUES 
(1, 3), (1, 4),
(2, 2), (2, 5),
(3, 2), (3, 3), (3, 6),
(4, 4),
(7, 3), (7, 4);

-- 8. 插入商品评论测试数据
INSERT INTO `product_comment` (`product_id`, `user_id`, `content`) VALUES 
(1, 3, '书看起来不错，能便宜点吗？'),
(1, 2, '可以小刀，具体私聊'),
(2, 4, '这本书是正版的吗？'),
(2, 3, '是正版，有发票'),
(3, 2, '电脑配置怎么样？能玩英雄联盟吗？'),
(3, 4, '配置足够，可以流畅运行'),
(4, 3, '耳机续航怎么样？'),
(4, 2, '正常使用4-5小时');

-- 9. 插入订单测试数据
INSERT INTO `order` (`order_no`, `product_id`, `buyer_id`, `seller_id`, `price`, `quantity`, `total_amount`, `receiver_name`, `receiver_phone`, `receiver_address`, `payment_no`, `payment_time`, `status`, `expire_time`) VALUES 
('SO202312011200000001', 5, 2, 3, 25.00, 1, 25.00, '张三', '13800000001', '宿舍楼1号楼201室', 'PAY20231201120001', '2023-12-01 12:05:00', 'COMPLETED', '2023-12-01 12:10:00'),
('SO202312021430000002', 6, 3, 4, 15.00, 1, 15.00, '李四', '13800000002', '宿舍楼2号楼305室', 'PAY20231202143001', '2023-12-02 14:35:00', 'DELIVERING', '2023-12-02 14:40:00'),
('SO202312031000000003', 1, 4, 2, 25.00, 1, 25.00, '王五', '13800000003', '教学楼B座101教室', NULL, NULL, 'PENDING_PAYMENT', '2023-12-03 10:10:00'),
('SO202312031500000004', 4, 5, 2, 80.00, 1, 80.00, '赵老师', '13800000004', '教师公寓3单元502', 'PAY20231203150001', '2023-12-03 15:05:00', 'CONFIRMED', '2023-12-03 15:10:00');

-- 10. 插入失物招领测试数据
INSERT INTO `lost_and_found` (`user_id`, `type`, `title`, `description`, `item_type`, `lost_found_time`, `location`, `images`, `urgency`, `contact_info`, `status`) VALUES 
(2, 'LOST', '丢失黑色钱包', '在图书馆丢失一个黑色钱包，内有身份证、学生证和少量现金', '钱包', '2023-12-01 15:00:00', '图书馆三楼阅览室', '["wallet1.jpg"]', 'URGENT', '13800000001', 'PENDING'),
(3, 'FOUND', '捡到一副眼镜', '在食堂捡到一副黑色边框眼镜，放在失物招领处', '眼镜', '2023-12-02 12:30:00', '食堂一楼', '["glasses1.jpg"]', 'NORMAL', '13800000002', 'PENDING'),
(4, 'LOST', '丢失U盘', '在计算机房丢失一个32G金士顿U盘，内有重要学习资料', 'U盘', '2023-12-01 10:00:00', '计算机学院机房', '["usb1.jpg"]', 'URGENT', '13800000003', 'RESOLVED'),
(2, 'FOUND', '捡到学生证', '在操场捡到一张学生证，姓名：李四，学号：2023001', '证件', '2023-12-03 16:00:00', '操场看台', '["card1.jpg"]', 'NORMAL', '13800000001', 'PENDING');

-- 11. 插入感谢信测试数据
INSERT INTO `thank_you_note` (`lost_found_id`, `user_id`, `title`, `content`, `images`) VALUES 
(3, 4, '感谢好心人找回U盘', '非常感谢捡到我U盘的同学，里面的资料对我非常重要！', '["thank1.jpg"]');

-- 12. 插入跑腿任务测试数据
INSERT INTO `errand_task` (`user_id`, `type`, `title`, `description`, `reward`, `location_from`, `location_to`, `deadline`, `contact_info`, `status`) VALUES 
(2, 'PARCEL', '代取快递', '帮忙从快递点取一个包裹送到宿舍楼1号楼', 5.00, '校园快递点', '宿舍楼1号楼', '2023-12-04 18:00:00', '13800000001', 'PENDING'),
(3, 'FOOD', '代买奶茶', '帮忙从校门口奶茶店买两杯珍珠奶茶', 8.00, '校门口奶茶店', '教学楼A座', '2023-12-04 16:30:00', '13800000002', 'ACCEPTED'),
(4, 'PRINT', '代打印资料', '帮忙打印20页学习资料，双面打印', 3.00, '打印店', '图书馆', '2023-12-05 10:00:00', '13800000003', 'COMPLETED'),
(5, 'OTHER', '代买文具', '帮忙买几只黑色水笔和一本笔记本', 5.00, '文具店', '教师办公室', '2023-12-04 17:00:00', '13800000004', 'IN_PROGRESS');

-- 13. 更新跑腿任务接单信息
UPDATE `errand_task` SET `acceptor_id` = 3, `accept_time` = '2023-12-04 15:00:00' WHERE id = 2;
UPDATE `errand_task` SET `acceptor_id` = 2, `accept_time` = '2023-12-04 14:30:00', `complete_time` = '2023-12-04 16:00:00', `proof_images` = '["proof1.jpg"]' WHERE id = 3;
UPDATE `errand_task` SET `acceptor_id` = 4, `accept_time` = '2023-12-04 16:00:00' WHERE id = 4;

-- 14. 插入跑腿任务评价测试数据
INSERT INTO `errand_review` (`task_id`, `reviewer_id`, `reviewee_id`, `rating`, `content`) VALUES 
(3, 4, 2, 5, '打印速度很快，资料整理得很整齐，非常感谢！');

-- 15. 插入资源分类测试数据
INSERT INTO `resource_category` (`name`, `icon`, `sort_order`) VALUES 
('学习笔记', 'note.png', 1),
('作业答案', 'homework.png', 2),
('考试真题', 'exam.png', 3),
('复习资料', 'review.png', 4),
('课件PPT', 'ppt.png', 5),
('其他资源', 'other.png', 6);

-- 16. 插入资源测试数据
INSERT INTO `resource` (`category_id`, `user_id`, `title`, `description`, `file_url`, `file_size`, `file_type`, `course_name`, `teacher_name`, `semester`) VALUES 
(1, 2, '高等数学笔记（第一章）', '高等数学第一章函数与极限的详细笔记', 'math_note1.pdf', 2048576, 'pdf', '高等数学', '张教授', '2023-2024-1'),
(1, 3, '线性代数复习提纲', '线性代数期末考试复习重点整理', 'linear_algebra.pdf', 1572864, 'pdf', '线性代数', '李教授', '2023-2024-1'),
(3, 4, 'C语言程序设计真题', '近三年C语言程序设计期末考试真题', 'c_programming.zip', 3145728, 'zip', 'C语言程序设计', '王老师', '2023-2024-1'),
(2, 2, '数据结构作业答案', '数据结构第一次作业参考答案', 'data_structure.docx', 1048576, 'docx', '数据结构', '赵教授', '2023-2024-1'),
(4, 3, '操作系统复习资料', '操作系统课程重点知识点总结', 'os_review.pdf', 2621440, 'pdf', '操作系统', '陈老师', '2023-2024-1');

-- 16. 插入学习小组测试数据
INSERT INTO `study_group` (`user_id`, `title`, `description`, `subject`, `target`, `max_members`, `current_members`, `meeting_time`, `meeting_location`) VALUES 
(2, '高数学习小组', '一起学习高等数学，互相帮助解决问题', '高等数学', 'SELF_STUDY', 8, 3, '每周三、周五晚上7-9点', '图书馆三楼自习室'),
(3, '考研英语小组', '准备考研英语，一起背单词、做真题', '英语', 'EXAM_PREP', 6, 2, '每天早晨7-8点', '教学楼A座101'),
(4, 'Java项目开发组', '学习Java Web开发，完成实际项目', 'Java编程', 'PROJECT', 5, 1, '周末下午2-5点', '计算机学院实验室');

-- 17. 插入小组成员测试数据
INSERT INTO `study_group_member` (`group_id`, `user_id`, `role`) VALUES 
(1, 2, 'CREATOR'),
(1, 3, 'MEMBER'),
(1, 4, 'MEMBER'),
(2, 3, 'CREATOR'),
(2, 2, 'MEMBER'),
(3, 4, 'CREATOR');

-- 18. 插入聊天会话测试数据
INSERT INTO `chat_session` (`type`, `name`, `avatar`) VALUES 
('PRIVATE', NULL, NULL),
('PRIVATE', NULL, NULL),
('GROUP', '高数学习小组', 'group1.png');

-- 19. 插入会话成员测试数据
INSERT INTO `chat_session_member` (`session_id`, `user_id`, `role`) VALUES 
(1, 2, 'OWNER'),
(1, 3, 'MEMBER'),
(2, 2, 'OWNER'),
(2, 4, 'MEMBER'),
(3, 2, 'OWNER'),
(3, 3, 'MEMBER'),
(3, 4, 'MEMBER');

-- 20. 插入聊天消息测试数据
INSERT INTO `chat_message` (`session_id`, `sender_id`, `content`, `message_type`) VALUES 
(1, 2, '你好，关于那本高数教材，能便宜点吗？', 'TEXT'),
(1, 3, '最低20元，不能再少了', 'TEXT'),
(1, 2, '好的，那我买了，什么时候可以交易？', 'TEXT'),
(1, 3, '明天中午图书馆门口见', 'TEXT'),
(3, 2, '大家好，这周六我们学习小组第一次活动', 'TEXT'),
(3, 3, '收到！需要带什么资料吗？', 'TEXT'),
(3, 2, '带上高数课本和笔记本就行', 'TEXT'),
(3, 4, '我周六下午有空，可以参加', 'TEXT');

-- 21. 更新用户最后登录时间
UPDATE `user` SET `last_login_time` = '2023-12-04 10:00:00', `login_count` = 15 WHERE id = 2;
UPDATE `user` SET `last_login_time` = '2023-12-04 09:30:00', `login_count` = 12 WHERE id = 3;
UPDATE `user` SET `last_login_time` = '2023-12-04 11:20:00', `login_count` = 8 WHERE id = 4;
UPDATE `user` SET `last_login_time` = '2023-12-04 08:45:00', `login_count` = 23 WHERE id = 5;
UPDATE `user` SET `last_login_time` = '2023-12-04 14:00:00', `login_count` = 45 WHERE id = 6;

-- 测试数据使用说明：
-- 1. 先执行database_design.sql创建数据库结构
-- 2. 再执行此文件插入测试数据
-- 3. 测试数据包含完整的业务场景，可用于功能测试
-- 4. 密码字段为示例，实际使用时需要加密处理
-- 5. 可以根据需要修改或扩展测试数据

-- 测试账号信息：
-- 超级管理员：admin/admin123
-- 普通用户：student001/123456, student002/123456, student003/123456, student004/123456
-- 管理员：teacher001/123456, manager001/123456

-- 主要测试场景：
-- 1. 用户登录和权限管理
-- 2. 论坛发帖、回复、点赞、收藏
-- 3. 二手商品发布、浏览、交易
-- 4. 失物招领发布和匹配
-- 5. 跑腿任务发布和接单
-- 6. 学习资源共享和下载
-- 7. 学习小组创建和加入
-- 8. 站内聊天功能

-- 数据验证SQL示例：
-- SELECT * FROM user_activity_view;  -- 查看用户活跃度
-- SELECT * FROM product_statistics_view;  -- 查看商品统计
-- SELECT * FROM forum_hot_view;  -- 查看论坛热度