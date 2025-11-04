package com.shxy.w202350766.campusserviceplatform.pojo.dto;

import lombok.Data;

/**
 * 失物招领物品查询DTO
 * 用于接收前端的失物招领物品查询参数
 */
@Data
public class LostFoundItemQueryDTO {
    /**
     * 当前页码
     */
    private Integer page = 1;
    
    /**
     * 每页大小
     */
    private Integer limit = 10;
    
    /**
     * 物品类型（LOST-失物，FOUND-招领）
     */
    private String type;
    
    /**
     * 物品分类ID
     */
    private Long categoryId;
    
    /**
     * 搜索关键词
     */
    private String keyword;
    
    /**
     * 排序方式（latest-最新发布，oldest-最早发布）
     */
    private String sort = "latest";
}