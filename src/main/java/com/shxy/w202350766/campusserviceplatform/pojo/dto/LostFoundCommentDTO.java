package com.shxy.w202350766.campusserviceplatform.pojo.dto;

import lombok.Data;

/**
 * 失物招领评论DTO
 * 用于接收前端提交的失物招领评论数据
 */
@Data
public class LostFoundCommentDTO {
    /**
     * 物品ID
     */
    private Long itemId;
    
    /**
     * 评论内容
     */
    private String content;
    
    /**
     * 父评论ID（回复评论时使用）
     */
    private Long parentId;
}