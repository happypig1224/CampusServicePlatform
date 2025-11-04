package com.shxy.w202350766.campusserviceplatform.pojo.vo;

import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 失物招领物品VO
 * 用于返回给前端的失物招领物品数据
 */
@Data
public class LostFoundItemVO {
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
     * 物品类型文本（用于显示）
     */
    private String typeText;
    
    /**
     * 物品类型颜色（用于显示）
     */
    private String typeColor;
    
    /**
     * 物品分类ID
     */
    private Long categoryId;
    
    /**
     * 物品分类名称
     */
    private String categoryName;
    
    /**
     * 丢失/拾到时间
     */
    private LocalDateTime lostFoundTime;
    
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
     * 状态文本（用于显示）
     */
    private String statusText;
    
    /**
     * 状态颜色（用于显示）
     */
    private String statusColor;
    
    /**
     * 存放地点（仅招领物品）
     */
    private String storage;
    
    /**
     * 悬赏金额（仅失物）
     */
    private Double reward;
    
    /**
     * 发布者ID
     */
    private Long userId;
    
    /**
     * 发布者用户名
     */
    private String username;
    
    /**
     * 发布者头像
     */
    private String avatar;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 格式化后的创建时间（用于显示）
     */
    private String createTimeText;
    
    /**
     * 格式化后的丢失/拾到时间（用于显示）
     */
    private String lostFoundTimeText;
    
    /**
     * 浏览次数
     */
    private Integer viewCount;
    
    /**
     * 格式化后的浏览次数（用于显示）
     */
    private String viewCountText;
    
    /**
     * 物品缩略图（第一张图片）
     */
    private String thumbnail;
    
    /**
     * 是否为自己的物品（当前用户）
     */
    private Boolean isOwnItem;
    
    /**
     * 是否已收藏（当前用户）
     */
    private Boolean isCollected;
}