package com.shxy.w202350766.campusserviceplatform.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * 商品表
 * @TableName product
 */
@TableName(value ="product")
@Data
public class Product {
    /**
     * 商品ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 发布用户ID
     */
    private Long userId;

    /**
     * 商品标题
     */
    private String title;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 商品图片（JSON数组）
     */
    private Object images;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 原价
     */
    private BigDecimal originalPrice;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 联系微信
     */
    private String contactWechat;

    /**
     * 交易地点
     */
    private String location;

    /**
     * 浏览数
     */
    private Integer viewCount;

    /**
     * 收藏数
     */
    private Integer collectCount;

    /**
     * 状态
     */
    private Object status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}