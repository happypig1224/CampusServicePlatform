package com.shxy.w202350766.campusserviceplatform.pojo.dto;

import lombok.Data;

/**
 * @Author 吴汇明
 * @School 绥化学院
 * @CreateTime 2025.10.31
 * @Description 论坛帖子数据传输对象
 */
@Data
public class ForumPostDTO {
    /**
     * 帖子标题
     */
    private String title;
    
    /**
     * 帖子内容
     */
    private String content;
    
    /**
     * 版块ID
     */
    private Long sectionId;
    
    /**
     * 用户ID
     */
    private Long userId;
}