package com.shxy.w202350766.campusserviceplatform.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 失物招领表
 * @TableName lost_and_found
 */
@TableName(value ="lost_and_found")
@Data
public class LostAndFound {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 发布用户ID
     */
    private Long userId;

    /**
     * 类型（丢失/拾到）
     */
    private Object type;

    /**
     * 标题
     */
    private String title;

    /**
     * 详细描述
     */
    private String description;

    /**
     * 物品类型
     */
    private String itemType;

    /**
     * 丢失/拾到时间
     */
    private Date lostFoundTime;

    /**
     * 地点
     */
    private String location;

    /**
     * 图片列表
     */
    private Object images;

    /**
     * 紧急程度
     */
    private Object urgency;

    /**
     * 联系方式
     */
    private String contactInfo;

    /**
     * 状态
     */
    private Object status;

    /**
     * 解决时间
     */
    private Date resolveTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}