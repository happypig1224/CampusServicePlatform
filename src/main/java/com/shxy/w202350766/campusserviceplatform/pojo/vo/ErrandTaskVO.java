package com.shxy.w202350766.campusserviceplatform.pojo.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 跑腿任务VO
 * 用于返回给前端的跑腿任务数据
 */
@Data
public class ErrandTaskVO {
    /**
     * 任务ID
     */
    private Long id;
    
    /**
     * 任务标题
     */
    private String title;
    
    /**
     * 任务描述
     */
    private String description;
    
    /**
     * 任务分类ID
     */
    private Long categoryId;
    
    /**
     * 任务分类名称
     */
    private String categoryName;
    
    /**
     * 任务报酬
     */
    private BigDecimal reward;
    
    /**
     * 任务地点
     */
    private String location;
    
    /**
     * 起始地点
     */
    private String locationFrom;
    
    /**
     * 目的地
     */
    private String locationTo;
    
    /**
     * 截止时间
     */
    private LocalDateTime deadline;
    
    /**
     * 格式化后的截止时间（用于显示）
     */
    private String deadlineText;
    
    /**
     * 联系方式
     */
    private String contactInfo;
    
    /**
     * 任务状态（PENDING-待接单，ACCEPTED-已接单，COMPLETED-已完成，CANCELLED-已取消）
     */
    private Object status;
    
    /**
     * 状态文本（用于显示）
     */
    private String statusText;
    
    /**
     * 状态颜色（用于显示）
     */
    private String statusColor;
    
    /**
     * 是否紧急任务
     */
    private Boolean isUrgent;
    
    /**
     * 发布者ID
     */
    private Long publisherId;
    
    /**
     * 发布者用户名
     */
    private String publisherName;
    
    /**
     * 发布者头像
     */
    private String publisherAvatar;
    
    /**
     * 接单者ID
     */
    private Long acceptorId;
    
    /**
     * 接单者用户名
     */
    private String acceptorName;
    
    /**
     * 接单者头像
     */
    private String acceptorAvatar;
    
    /**
     * 接单时间
     */
    private LocalDateTime acceptTime;
    
    /**
     * 完成时间
     */
    private LocalDateTime completeTime;
    
    /**
     * 任务图片列表
     */
    private List<String> images;
    
    /**
     * 任务缩略图（第一张图片）
     */
    private String thumbnail;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 格式化后的创建时间（用于显示）
     */
    private String createTimeText;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 浏览次数
     */
    private Long viewCount;
    
    /**
     * 是否为自己的任务（当前用户）
     */
    private Boolean isOwnTask;
    
    /**
     * 是否已接单（当前用户）
     */
    private Boolean isAcceptedByCurrentUser;
    
    /**
     * 是否可以接单（当前用户）
     */
    private Boolean canAccept;
    
    /**
     * 是否可以取消（当前用户）
     */
    private Boolean canCancel;
    
    /**
     * 是否可以完成（当前用户）
     */
    private Boolean canComplete;
    
    /**
     * 是否可以评价（当前用户）
     */
    private Boolean canReview;
    
    /**
     * 剩余时间（用于显示）
     */
    private String remainingTime;
    
    /**
     * 任务备注
     */
    private String remark;
}