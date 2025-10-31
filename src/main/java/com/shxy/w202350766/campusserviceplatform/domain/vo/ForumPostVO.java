package com.shxy.w202350766.campusserviceplatform.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 论坛帖子VO
 * @Author 吴汇明
 * @School 绥化学院
 * @CreateTime 2025.10.31
 */
@Data
public class ForumPostVO {
    private Long id;
    private Long sectionId;
    private String sectionName;
    private Long userId;
    private String userNickname;
    private String userAvatar;
    private String title;
    private String content;
    private Integer viewCount;
    private Integer replyCount;
    private Integer likeCount;
    private Integer collectCount;
    private Boolean isTop;
    private Boolean isEssence;
    private Date createTime;
    private Date lastReplyTime;
}