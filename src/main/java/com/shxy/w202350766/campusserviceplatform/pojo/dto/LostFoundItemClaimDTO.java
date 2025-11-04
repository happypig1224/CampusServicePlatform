package com.shxy.w202350766.campusserviceplatform.pojo.dto;

import lombok.Data;

/**
 * 失物招领物品认领DTO
 * 用于接收前端的失物招领物品认领请求
 */
@Data
public class LostFoundItemClaimDTO {
    /**
     * 物品ID
     */
    private Long itemId;
    
    /**
     * 认领描述
     */
    private String description;
    
    /**
     * 联系方式
     */
    private String contact;
}