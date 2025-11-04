package com.shxy.w202350766.campusserviceplatform.pojo.vo;

import lombok.Data;

import java.util.Date;

/**
 * 商品评论VO
 * 用于商品详情中的评论展示
 */
@Data
public class ProductCommentVO {
    /**
     * 评论ID
     */
    private Long id;
    
    /**
     * 商品ID
     */
    private Long productId;
    
    /**
     * 评论用户ID
     */
    private Long userId;
    
    /**
     * 评论用户名
     */
    private String username;
    
    /**
     * 评论用户头像
     */
    private String avatar;
    
    /**
     * 评论内容
     */
    private String content;
    
    /**
     * 评论评分（1-5星）
     */
    private Integer rating;
    
    /**
     * 评论图片列表
     */
    private String images;
    
    /**
     * 评论时间
     */
    private Date createTime;
    
    /**
     * 格式化后的评论时间（用于显示）
     */
    private String createTimeText;
    
    /**
     * 是否为匿名评论
     */
    private Boolean isAnonymous;
    
    /**
     * 商家回复
     */
    private String merchantReply;
    
    /**
     * 商家回复时间
     */
    private Date replyTime;
    
    /**
     * 格式化后的商家回复时间（用于显示）
     */
    private String replyTimeText;
}