package com.shxy.w202350766.campusserviceplatform.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 用户表
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User {
    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码（加密存储）
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 用户介绍
     */
    private String bio;

    /**
     * 角色
     */
    private Object role;

    /**
     * 状态
     */
    private Object status;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 登录次数
     */
    private Integer loginCount;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}