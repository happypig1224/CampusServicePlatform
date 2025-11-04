package com.shxy.w202350766.campusserviceplatform.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 小组成员表
 * @TableName study_group_member
 */
@TableName(value ="study_group_member")
@Data
public class StudyGroupMember {
    /**
     * 成员ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 小组ID
     */
    private Long groupId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色
     */
    private Object role;

    /**
     * 加入时间
     */
    private Date joinTime;
}