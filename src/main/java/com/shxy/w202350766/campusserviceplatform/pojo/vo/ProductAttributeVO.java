package com.shxy.w202350766.campusserviceplatform.pojo.vo;

import lombok.Data;

/**
 * 商品属性VO
 * 用于商品详情中的属性展示
 */
@Data
public class ProductAttributeVO {
    /**
     * 属性ID
     */
    private Long id;
    
    /**
     * 属性名称
     */
    private String name;
    
    /**
     * 属性值
     */
    private String value;
    
    /**
     * 属性排序
     */
    private Integer sortOrder;
}