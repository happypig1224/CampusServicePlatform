package com.shxy.w202350766.campusserviceplatform.domain.dto;

import lombok.Data;

/**
 * @Author 吴汇明
 * @School 绥化学院
 * @CreateTime 2025.10.31
 * @Description 论坛帖子查询数据传输对象
 */
@Data
public class ForumPostQueryDTO {
    /**
     * 当前页码
     */
    private Integer page = 1;
    
    /**
     * 每页大小
     */
    private Integer limit = 10;
    
    /**
     * 版块ID
     */
    private Long sectionId;
    
    /**
     * 搜索关键词
     */
    private String keyword;
    
    /**
     * 排序方式
     */
    private String sort = "latest";
}