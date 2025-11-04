package com.shxy.w202350766.campusserviceplatform.pojo.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 失物招领分类VO
 * 用于返回给前端的失物招领分类数据
 */
@Data
public class LostFoundCategoryVO {
    /**
     * 分类ID
     */
    private Long id;
    
    /**
     * 分类名称
     */
    private String name;
    
    /**
     * 分类图标
     */
    private String icon;
    
    /**
     * 排序值
     */
    private Integer sort;

    /**
     * 物品数量
     */
    private Integer itemCount;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}