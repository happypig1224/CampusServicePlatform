package com.shxy.w202350766.suiestation.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 会话成员表
 * @TableName chat_session_member
 */
@TableName(value ="chat_session_member")
@Data
public class ChatSessionMember {
    /**
     * 成员ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 会话ID
     */
    private Long sessionId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 在会话中的昵称
     */
    private String nickname;

    /**
     * 角色
     */
    private Object role;

    /**
     * 加入时间
     */
    private Date joinTime;
}