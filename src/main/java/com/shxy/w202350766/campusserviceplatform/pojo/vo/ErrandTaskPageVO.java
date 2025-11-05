package com.shxy.w202350766.campusserviceplatform.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 * 跑腿任务分页结果VO
 * 用于返回给前端的跑腿任务分页数据
 */
@Data
public class ErrandTaskPageVO {
    /**
     * 当前页码
     */
    private Long pageNum;
    
    /**
     * 每页数量
     */
    private Long pageSize;
    
    /**
     * 总记录数
     */
    private Long total;
    
    /**
     * 总页数
     */
    private Long pages;
    
    /**
     * 是否有上一页
     */
    private Boolean hasPrevious;
    
    /**
     * 是否有下一页
     */
    private Boolean hasNext;
    
    /**
     * 任务列表
     */
    private List<ErrandTaskVO> tasks;
    
    /**
     * 分页导航数据
     */
    private List<Integer> navigatepageNums;
    
    /**
     * 导航页码数
     */
    private Integer navigatePages;
    
    /**
     * 当前导航页码
     */
    private Integer navigatepageNum;
    
    /**
     * 是否为第一页
     */
    private Boolean isFirstPage;
    
    /**
     * 是否为最后一页
     */
    private Boolean isLastPage;
    
    /**
     * 是否有数据
     */
    private Boolean hasContent;
}