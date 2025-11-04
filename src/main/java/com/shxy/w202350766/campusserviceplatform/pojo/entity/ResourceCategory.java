package com.shxy.w202350766.campusserviceplatform.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 资源分类表
 * @TableName resource_category
 */
@TableName(value ="resource_category")
@Data
public class ResourceCategory {
    /**
     * 分类ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 资源类型
     */
    private Object type;

    /**
     * 排序
     */
    private Integer sortOrder;

    /**
     * 状态
     */
    private Object status;

    /**
     * 创建时间
     */
    private Date createTime;
}