package com.shxy.w202350766.campusserviceplatform.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;
import org.springframework.cglib.core.Local;

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
     * 物品分类ID
     */
    private Long categoryId;

    /**
     * 丢失/拾到时间
     */
    private LocalDateTime lostFoundTime;

    /**
     * 悬赏金额
     */
    private Double reward;

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
     * 浏览次数
     */
    private Long viewCount;

    /**
     * 解决时间
     */
    private LocalDateTime resolveTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}