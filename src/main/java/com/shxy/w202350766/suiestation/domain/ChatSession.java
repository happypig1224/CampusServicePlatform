package com.shxy.w202350766.suiestation.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 聊天会话表
 * @TableName chat_session
 */
@TableName(value ="chat_session")
@Data
public class ChatSession {
    /**
     * 会话ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 会话类型
     */
    private Object type;

    /**
     * 会话名称（群聊时使用）
     */
    private String name;

    /**
     * 会话头像
     */
    private String avatar;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}