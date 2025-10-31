package com.shxy.w202350766.campusserviceplatform.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 帖子点赞表
 * @TableName forum_like
 */
@TableName(value ="forum_like")
@Data
public class ForumLike {
    /**
     * 点赞ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 帖子ID
     */
    private Long postId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;
}