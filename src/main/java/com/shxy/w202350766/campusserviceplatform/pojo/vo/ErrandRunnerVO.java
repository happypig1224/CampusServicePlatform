package com.shxy.w202350766.campusserviceplatform.pojo.vo;

import lombok.Data;

/**
 * 跑腿达人VO
 * 用于返回给前端的跑腿达人数据
 */
@Data
public class ErrandRunnerVO {
    /**
     * 用户ID
     */
    private Long id;
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 用户头像
     */
    private String avatar;
    
    /**
     * 用户评分
     */
    private Integer rating;
    
    /**
     * 评分星级（用于显示）
     */
    private String ratingStars;
    
    /**
     * 已完成任务数
     */
    private Integer completedTasks;
    
    /**
     * 进行中任务数
     */
    private Integer ongoingTasks;
    
    /**
     * 总收入
     */
    private Double totalIncome;
    
    /**
     * 排名
     */
    private Integer rank;
    
    /**
     * 认证状态（VERIFIED-已认证，UNVERIFIED-未认证）
     */
    private String verificationStatus;
    
    /**
     * 认证状态文本（用于显示）
     */
    private String verificationStatusText;
    
    /**
     * 认证状态颜色（用于显示）
     */
    private String verificationStatusColor;
    
    /**
     * 个人简介
     */
    private String bio;
    
    /**
     * 接单范围
     */
    private String serviceArea;
    
    /**
     * 专长领域
     */
    private String specialties;
    
    /**
     * 注册时间
     */
    private String registerTime;
    
    /**
     * 格式化后的注册时间（用于显示）
     */
    private String registerTimeText;
    
    /**
     * 平均完成时间（小时）
     */
    private Double averageCompletionTime;
    
    /**
     * 完成率（百分比）
     */
    private Double completionRate;
}