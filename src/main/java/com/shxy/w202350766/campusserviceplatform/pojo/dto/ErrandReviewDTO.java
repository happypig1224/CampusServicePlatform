package com.shxy.w202350766.campusserviceplatform.pojo.dto;

import lombok.Data;

/**
 * 跑腿任务评价DTO
 * 用于接收前端提交的跑腿任务评价数据
 */
@Data
public class ErrandReviewDTO {
    /**
     * 任务ID
     */
    private Long taskId;
    
    /**
     * 评分（1-5）
     */
    private Integer rating;
    
    /**
     * 评价内容
     */
    private String content;
    
    /**
     * 被评价用户ID（接单者ID）
     */
    private Long revieweeId;
}