package com.shxy.w202350766.campusserviceplatform.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.ConstructorArgs;

/**
 * 跑腿任务分类VO
 * 用于返回给前端的跑腿任务分类数据
 */
@Data
public class ErrandCategoryVO {
    /**
     * 分类ID
     */
    private Integer id;
    
    /**
     * 分类名称
     */
    private String name;
    
    /**
     * 分类图标
     */
    private String icon;
    
    /**
     * 分类描述
     */
    private String description;
    
    /**
     * 排序序号
     */
    private Integer sortOrder;
    
    /**
     * 任务数量
     */
    private Long taskCount;
    
    /**
     * 是否启用
     */
    private Boolean enabled;
    
    /**
     * 分类颜色（用于显示）
     */
    private String color;
}