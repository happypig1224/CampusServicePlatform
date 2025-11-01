package com.shxy.w202350766.campusserviceplatform.domain.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 商品列表VO
 * 用于返回给前端的商品列表数据
 */
@Data
public class ProductListVO {
    /**
     * 商品ID
     */
    private Long id;
    
    /**
     * 商品标题
     */
    private String title;
    
    /**
     * 商品描述
     */
    private String description;
    
    /**
     * 商品价格
     */
    private BigDecimal price;
    
    /**
     * 商品原价
     */
    private BigDecimal originalPrice;
    
    /**
     * 商品图片列表（JSON数组）
     */
    private List<String> images;
    
    /**
     * 商品分类ID
     */
    private Long categoryId;
    
    /**
     * 商品分类名称
     */
    private String categoryName;
    
    /**
     * 商品状态（AUDITING-审核中，ON_SALE-在售，SOLD-已售出，OFF_SHELF-已下架）
     */
    private String status;
    
    /**
     * 商品状态文本（用于显示）
     */
    private String statusText;
    
    /**
     * 商品状态颜色（用于显示）
     */
    private String statusColor;
    
    /**
     * 联系电话
     */
    private String contactPhone;
    
    /**
     * 联系微信
     */
    private String contactWechat;
    
    /**
     * 商品位置
     */
    private String location;
    
    /**
     * 浏览次数
     */
    private Integer viewCount;
    
    /**
     * 收藏次数
     */
    private Integer collectCount;
    
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
    private Date createTime;
    
    /**
     * 格式化后的创建时间（用于显示）
     */
    private String createTimeText;
    
    /**
     * 是否已收藏（当前用户）
     */
    private Boolean isCollected;
    
    /**
     * 是否为自己的商品（当前用户）
     */
    private Boolean isOwnProduct;
    
    /**
     * 商品缩略图（第一张图片）
     */
    private String thumbnail;
    
    /**
     * 格式化后的价格（用于显示）
     */
    private String priceText;
    
    /**
     * 格式化后的原价（用于显示）
     */
    private String originalPriceText;
    
    /**
     * 格式化后的浏览次数（用于显示）
     */
    private String viewCountText;
    
    /**
     * 格式化后的收藏次数（用于显示）
     */
    private String collectCountText;
    
    /**
     * 联系方式文本（用于显示）
     */
    private String contactText;
}