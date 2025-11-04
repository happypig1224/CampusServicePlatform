package com.shxy.w202350766.campusserviceplatform.pojo.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 商品详情VO
 * 用于返回给前端的商品详情数据
 */
@Data
public class ProductDetailVO {
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
     * 发布者真实姓名
     */
    private String realName;
    
    /**
     * 发布者联系方式
     */
    private String userContact;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date updateTime;
    
    /**
     * 格式化后的创建时间（用于显示）
     */
    private String createTimeText;
    
    /**
     * 格式化后的更新时间（用于显示）
     */
    private String updateTimeText;
    
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
    
    /**
     * 商品详情图片列表（完整尺寸）
     */
    private List<String> detailImages;
    
    /**
     * 商品属性列表（如：品牌、型号、新旧程度等）
     */
    private List<ProductAttributeVO> attributes;
    
    /**
     * 相似商品推荐列表
     */
    private List<ProductListVO> similarProducts;
    
    /**
     * 商品评论列表
     */
    private List<ProductCommentVO> comments;
    
    /**
     * 商品评分
     */
    private BigDecimal rating;
    
    /**
     * 商品评论数量
     */
    private Integer commentCount;
    
    /**
     * 是否可以下单
     */
    private Boolean canOrder;
    
    /**
     * 是否可以收藏
     */
    private Boolean canCollect;
}