package com.shxy.w202350766.campusserviceplatform.pojo.vo;

import lombok.Data;

/**
 * 跑腿任务统计VO
 * 用于返回给前端的跑腿任务统计数据
 */
@Data
public class ErrandStatsVO {
    /**
     * 总任务数
     */
    private Long totalTasks;
    
    /**
     * 待接单任务数
     */
    private Long pendingTasks;
    
    /**
     * 进行中任务数
     */
    private Long ongoingTasks;
    
    /**
     * 已完成任务数
     */
    private Long completedTasks;
    
    /**
     * 已取消任务数
     */
    private Long cancelledTasks;
    
    /**
     * 我发布的任务数
     */
    private Long myPublishedTasks;
    
    /**
     * 我接受的任务数
     */
    private Long myAcceptedTasks;
    
    /**
     * 我完成的任务数
     */
    private Long myCompletedTasks;
    
    /**
     * 总收入
     */
    private Double totalIncome;
    
    /**
     * 本月收入
     */
    private Double monthlyIncome;
    
    /**
     * 平均任务报酬
     */
    private Double averageReward;
    
    /**
     * 完成率
     */
    private Double completionRate;
    
    /**
     * 平均评分
     */
    private Double averageRating;
    
    /**
     * 紧急任务数
     */
    private Long urgentTasks;
    
    /**
     * 今日任务数
     */
    private Long todayTasks;
    
    /**
     * 本周任务数
     */
    private Long weeklyTasks;
    
    /**
     * 本月任务数
     */
    private Long monthlyTasks;
}