package com.shxy.w202350766.campusserviceplatform.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 学习小组表
 * @TableName study_group
 */
@TableName(value ="study_group")
@Data
public class StudyGroup {
    /**
     * 小组ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 创建用户ID
     */
    private Long userId;

    /**
     * 小组标题
     */
    private String title;

    /**
     * 小组描述
     */
    private String description;

    /**
     * 学习科目
     */
    private String subject;

    /**
     * 学习目标
     */
    private Object target;

    /**
     * 最大成员数
     */
    private Integer maxMembers;

    /**
     * 当前成员数
     */
    private Integer currentMembers;

    /**
     * 活动时间
     */
    private String meetingTime;

    /**
     * 活动地点
     */
    private String meetingLocation;

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