package com.shxy.w202350766.campusserviceplatform.pojo.vo;

import lombok.Data;

/**
 * 商品查询条件VO
 * 用于接收前端传递的商品查询参数
 */
@Data
public class ProductQueryVO {
    /**
     * 当前页码
     */
    private Integer page = 1;
    
    /**
     * 每页数量
     */
    private Integer limit = 10;
    
    /**
     * 商品分类ID
     */
    private Long categoryId;
    
    /**
     * 关键词搜索（商品标题、描述）
     */
    private String keyword;
    
    /**
     * 最低价格
     */
    private Double minPrice;
    
    /**
     * 最高价格
     */
    private Double maxPrice;
    
    /**
     * 商品状态（AUDITING-审核中，ON_SALE-在售，SOLD-已售出，OFF_SHELF-已下架）
     */
    private String status;
    
    /**
     * 排序字段（price-价格，view_count-浏览量，collect_count-收藏量，create_time-发布时间）
     */
    private String sort = "create_time";
    
    /**
     * 排序方式（asc-升序，desc-降序）
     */
    private String order = "desc";
    
    /**
     * 商品位置
     */
    private String location;
    
    /**
     * 发布者ID
     */
    private Long userId;
    
    /**
     * 是否只看自己的商品
     */
    private Boolean onlyOwn;
    
    /**
     * 是否包含已售出的商品
     */
    private Boolean includeSold = false;
    
    /**
     * 是否包含已下架的商品
     */
    private Boolean includeOffShelf = false;
    
    /**
     * 价格区间（low-低价，mid-中价，high-高价）
     */
    private String priceRange;
    
    /**
     * 新旧程度（new-全新，like_new-九成新，good-八成新，fair-七成新，poor-六成新及以下）
     */
    private String condition;
}