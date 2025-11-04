package com.shxy.w202350766.campusserviceplatform.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 论坛统计VO
 * @Author 吴汇明
 * @School 绥化学院
 * @CreateTime 2025.10.31
 */
@Data
public class ForumStatsVO {
    private Long totalPosts;
    private Long todayPosts;
    private Long totalUsers;
}