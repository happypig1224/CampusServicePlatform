package com.shxy.w202350766.campusserviceplatform.pojo.dto;

import lombok.Data;

/**
 * @Author 吴汇明
 * @School 绥化学院
 * @CreateTime 2025.10.31
 * @Description 论坛点赞数据传输对象
 */
@Data
public class ForumLikeDTO {
    /**
     * 帖子ID
     */
    private Long postId;
    
    /**
     * 用户ID
     */
    private Long userId;
}