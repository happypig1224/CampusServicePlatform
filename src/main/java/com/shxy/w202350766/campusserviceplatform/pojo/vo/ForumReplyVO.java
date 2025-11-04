package com.shxy.w202350766.campusserviceplatform.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 论坛回复VO
 * @Author 吴汇明
 * @School 绥化学院
 * @CreateTime 2025.10.31
 */
@Data
public class ForumReplyVO {
    private Long id;
    private Long postId;
    private String postTitle;
    private Long userId;
    private String userNickname;
    private String userAvatar;
    private String content;
    private Integer likeCount;
    private LocalDateTime createTime;
}