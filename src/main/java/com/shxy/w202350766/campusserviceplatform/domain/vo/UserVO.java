package com.shxy.w202350766.campusserviceplatform.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;

import java.time.LocalDateTime;

@lombok.Data
public class UserVO {
    private String avatar;
    private LocalDateTime createTime;
    private String phone;
    private Integer gender;
    private String role;
    private String nickname;
    private String token;
    /**
     * 用户ID
     * 使用@TableField注解指定映射到数据库的id字段
     */
    @TableField("id")
    private Long userId;
    
    private String username;
    private String bio;
}