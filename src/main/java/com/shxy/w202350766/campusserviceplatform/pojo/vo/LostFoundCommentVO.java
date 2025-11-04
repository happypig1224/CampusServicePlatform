package com.shxy.w202350766.campusserviceplatform.pojo.vo;

import lombok.Data;
import java.util.Date;
import java.util.List;

/**
 * 失物招领评论VO
 * 用于返回给前端的失物招领评论数据
 */
@Data
public class LostFoundCommentVO {
    /**
     * 评论ID
     */
    private Long id;
    
    /**
     * 物品ID
     */
    private Long itemId;
    
    /**
     * 评论人ID
     */
    private Long userId;
    
    /**
     * 评论人用户名
     */
    private String username;
    
    /**
     * 评论人头像
     */
    private String avatar;
    
    /**
     * 评论内容
     */
    private String content;
    
    /**
     * 评论时间
     */
    private Date createTime;
    
    /**
     * 格式化后的评论时间（用于显示）
     */
    private String createTimeText;
    
    /**
     * 点赞数
     */
    private Integer likeCount;
    
    /**
     * 格式化后的点赞数（用于显示）
     */
    private String likeCountText;
    
    /**
     * 是否已点赞（当前用户）
     */
    private Boolean isLiked;
    
    /**
     * 回复列表
     */
    private List<LostFoundCommentVO> replies;
    
    /**
     * 父评论ID
     */
    private Long parentId;
    
    /**
     * 回复的评论人用户名
     */
    private String replyToUsername;
}