package com.shxy.w202350766.campusserviceplatform.pojo.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 失物招领物品详情VO
 * 用于返回给前端的失物招领物品详情数据
 */
@Data
public class LostFoundItemDetailVO {
    /**
     * 物品ID
     */
    private Long id;
    
    /**
     * 物品标题
     */
    private String title;
    
    /**
     * 物品描述
     */
    private String description;
    
    /**
     * 物品类型（LOST-失物，FOUND-招领）
     */
    private String type;
    
    /**
     * 物品分类名称
     */
    private String categoryName;
    
    /**
     * 丢失时间（失物）
     */
    private LocalDateTime lostTime;
    
    /**
     * 拾获时间（招领）
     */
    private LocalDateTime foundTime;
    
    /**
     * 地点
     */
    private String location;
    
    /**
     * 图片列表
     */
    private List<String> images;
    
    /**
     * 联系方式
     */
    private String contact;
    
    /**
     * 状态（PENDING-待解决，RESOLVED-已解决）
     */
    private String status;
    
    /**
     * 存放地点（仅招领物品）
     */
    private String storage;
    
    /**
     * 悬赏金额（仅失物）
     */
    private Double reward;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 浏览次数
     */
    private Long viewCount;
}