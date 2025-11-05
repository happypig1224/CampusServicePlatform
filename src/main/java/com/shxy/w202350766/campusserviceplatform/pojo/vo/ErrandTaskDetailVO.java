package com.shxy.w202350766.campusserviceplatform.pojo.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 跑腿任务详情VO
 * 用于返回给前端的跑腿任务详情数据
 */
@Data
public class ErrandTaskDetailVO {
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
    private String status;
    
    /**
     * 状态文本（用于显示）
     */
    private String statusText;
    
    /**
     * 状态颜色（用于显示）
     */
    private String statusColor;
    
    /**
     * 发布者信息
     */
    private ErrandPublisherVO publisher;
    
    /**
     * 接单者信息
     */
    private ErrandAcceptorVO acceptor;
    
    /**
     * 接单时间
     */
    private LocalDateTime acceptTime;
    
    /**
     * 格式化后的接单时间（用于显示）
     */
    private String acceptTimeText;
    
    /**
     * 完成时间
     */
    private LocalDateTime completeTime;
    
    /**
     * 格式化后的完成时间（用于显示）
     */
    private String completeTimeText;
    
    /**
     * 任务图片列表
     */
    private List<String> images;
    
    /**
     * 完成证明图片列表
     */
    private List<String> proofImages;
    
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
     * 格式化后的更新时间（用于显示）
     */
    private String updateTimeText;
    
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
     * 是否可以完成（接任务用户）
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
    
    /**
     * 任务评价列表
     */
    private List<ErrandReviewVO> reviews;
    
    /**
     * 浏览次数
     */
    private Long viewCount;
    
    /**
     * 格式化后的浏览次数（用于显示）
     */
    private String viewCountText;
    
    /**
     * 发布者信息内部类
     */
    @Data
    public static class ErrandPublisherVO {
        private Long id;
        private String username;
        private String avatar;
        private String phone;
        private String email;
        private Double rating;
        private Integer completedTasks;
    }
    
    /**
     * 接单者信息内部类
     */
    @Data
    public static class ErrandAcceptorVO {
        private Long id;
        private String username;
        private String avatar;
        private String phone;
        private String email;
        private Double rating;
        private Integer completedTasks;
    }
}