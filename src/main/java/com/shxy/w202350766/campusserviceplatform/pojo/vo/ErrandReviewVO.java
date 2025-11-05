package com.shxy.w202350766.campusserviceplatform.pojo.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 跑腿任务评价VO
 * 用于返回给前端的跑腿任务评价数据
 */
@Data
public class ErrandReviewVO {
    /**
     * 评价ID
     */
    private Long id;
    
    /**
     * 任务ID
     */
    private Long taskId;
    
    /**
     * 任务标题
     */
    private String taskTitle;
    
    /**
     * 评价用户ID
     */
    private Long reviewerId;
    
    /**
     * 评价用户名
     */
    private String reviewerName;
    
    /**
     * 评价用户头像
     */
    private String reviewerAvatar;
    
    /**
     * 被评价用户ID
     */
    private Long revieweeId;
    
    /**
     * 被评价用户名
     */
    private String revieweeName;
    
    /**
     * 被评价用户头像
     */
    private String revieweeAvatar;
    
    /**
     * 评分（1-5）
     */
    private Integer rating;
    
    /**
     * 评分星级（用于显示）
     */
    private String ratingStars;
    
    /**
     * 评价内容
     */
    private String content;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 格式化后的创建时间（用于显示）
     */
    private String createTimeText;
    
    /**
     * 是否自己的评价（当前用户）
     */
    private Boolean isOwnReview;
}