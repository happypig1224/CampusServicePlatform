package com.shxy.w202350766.suiestation.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 帖子表
 * @TableName forum_post
 */
@TableName(value ="forum_post")
@Data
public class ForumPost {
    /**
     * 帖子ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 版块ID
     */
    private Long sectionId;

    /**
     * 发帖用户ID
     */
    private Long userId;

    /**
     * 帖子标题
     */
    private String title;

    /**
     * 帖子内容
     */
    private String content;

    /**
     * 图片列表（JSON数组）
     */
    private Object images;

    /**
     * 浏览数
     */
    private Integer viewCount;

    /**
     * 回复数
     */
    private Integer replyCount;

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 收藏数
     */
    private Integer collectCount;

    /**
     * 是否置顶
     */
    private Integer isTop;

    /**
     * 是否精华
     */
    private Integer isEssence;

    /**
     * 状态
     */
    private Object status;

    /**
     * 最后回复时间
     */
    private Date lastReplyTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}