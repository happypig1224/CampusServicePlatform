package com.shxy.w202350766.campusserviceplatform.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 跑腿任务表
 * @TableName errand_task
 */
@TableName(value ="errand_task")
@Data
public class ErrandTask {
    /**
     * 任务ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 发布用户ID
     */
    private Long userId;

    /**
     * 任务类型
     */
    private Object type;

    /**
     * 任务标题
     */
    private String title;

    /**
     * 任务描述
     */
    private String description;

    /**
     * 酬金（必须≥3元）
     */
    private BigDecimal reward;

    /**
     * 起始地点
     */
    private String locationFrom;

    /**
     * 目的地
     */
    private String locationTo;

    /**
     * 截止时间
     */
    private Date deadline;

    /**
     * 联系方式
     */
    private String contactInfo;

    /**
     * 状态
     */
    private Object status;

    /**
     * 接单用户ID
     */
    private Long acceptorId;

    /**
     * 接单时间
     */
    private Date acceptTime;

    /**
     * 完成时间
     */
    private Date completeTime;

    /**
     * 完成证明图片
     */
    private Object proofImages;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}