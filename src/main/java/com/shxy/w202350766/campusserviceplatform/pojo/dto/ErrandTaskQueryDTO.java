package com.shxy.w202350766.campusserviceplatform.pojo.dto;

import lombok.Data;

/**
 * 跑腿任务查询DTO
 * 用于接收前端查询跑腿任务的参数
 */
@Data
public class ErrandTaskQueryDTO {
    /**
     * 搜索关键词
     */
    private String keyword;
    
    /**
     * 任务分类ID
     */
    private Long categoryId;
    
    /**
     * 排序方式（latest-最新发布，price-asc-价格从低到高，price-desc-价格从高到低，urgent-紧急任务）
     */
    private String sortBy;
    
    /**
     * 是否仅显示紧急任务
     */
    private Boolean onlyUrgent;
    
    /**
     * 任务状态（PENDING-待接单，ACCEPTED-已接单，COMPLETED-已完成，CANCELLED-已取消）
     */
    private String status;
    
    /**
     * 发布者ID
     */
    private Long publisherId;
    
    /**
     * 接单者ID
     */
    private Long acceptorId;
    
    /**
     * 当前页码
     */
    private Integer pageNum = 1;
    
    /**
     * 每页数量
     */
    private Integer pageSize = 10;
}