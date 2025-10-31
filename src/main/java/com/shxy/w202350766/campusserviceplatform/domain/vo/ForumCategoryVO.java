package com.shxy.w202350766.campusserviceplatform.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 吴汇明
 * @School 绥化学院
 * @CreateTime 2025.10.31
 */
@Data
public class ForumCategoryVO {
    private Long categoryId;
    private String name;
    private String description;
    private String icon;
    private Integer postCount;
    private Integer sort;
}
