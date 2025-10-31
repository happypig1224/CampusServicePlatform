package com.shxy.w202350766.campusserviceplatform.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 论坛版块表
 * @TableName forum_section
 */
@TableName(value ="forum_section")
@Data
public class ForumSection {
    /**
     * 版块ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 版块名称
     */
    private String name;

    /**
     * 版块描述
     */
    private String description;

    /**
     * 版块图标
     */
    private String icon;

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

    /**
     * 更新时间
     */
    private Date updateTime;
}