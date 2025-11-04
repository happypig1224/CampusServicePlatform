package com.shxy.w202350766.campusserviceplatform.pojo.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 失物招领物品发布DTO
 * 用于接收前端提交的失物招领物品数据
 */
@Data
public class LostFoundItemDTO {
    /**
     * 物品类型（LOST-失物，FOUND-招领）
     */
    private String type;
    
    /**
     * 物品标题
     */
    private String title;
    
    /**
     * 物品描述
     */
    private String description;
    
    /**
     * 物品分类ID
     */
    private Long categoryId;
    
    /**
     * 丢失/拾到时间
     */
    private LocalDateTime lostFoundTime;
    
    /**
     * 地点
     */
    private String location;
    
    /**
     * 联系方式
     */
    private String contact;
    
    /**
     * 存放地点（仅招领物品）
     */
    private String storage;
    
    /**
     * 悬赏金额（仅失物）
     */
    private Double reward;
    
    /**
     * 上传的图片文件列表
     */
    private Object images;

}