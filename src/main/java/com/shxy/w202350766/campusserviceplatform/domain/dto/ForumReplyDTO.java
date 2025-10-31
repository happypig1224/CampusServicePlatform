package com.shxy.w202350766.campusserviceplatform.domain.dto;

import lombok.Data;

/**
 * @Author 吴汇明
 * @School 绥化学院
 * @CreateTime  2025.10.31
 * @Description 论坛回复数据传输对象
 */
@Data
public class ForumReplyDTO {
    /**
     * 回复的用户ID
     */
    private Long userId;
    /**
     * 回复的帖子ID
     */
    private Long postId;
    /**
     * 回复的评论ID（如果是回复评论）
     */
    private Long replyId;
    /**
     * 回复的内容
     */
    private String content;
}
