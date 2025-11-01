package com.shxy.w202350766.campusserviceplatform.domain.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.BitSet;

/**
 * @Author 吴汇明
 * @School 绥化学院
 * @CreateTime
 */
@Data
public class ProductVO {
    private Long id;
    private String title;
    private String description;
    private Object images;
    private BigDecimal price;
    private BigDecimal originalPrice;
    private String contactPhone;
    private String contactWechat;
    private String location;
    private Integer viewCount;
    private Integer stock;
    private String categoryId;
    private String categoryName;
    private Long userId;
    private String userName;
    private String createTime;
    private String updateTime;
}
