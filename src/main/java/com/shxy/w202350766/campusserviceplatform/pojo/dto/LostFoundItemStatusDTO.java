package com.shxy.w202350766.campusserviceplatform.pojo.dto;

import lombok.Data;

/**
 * 失物招领物品状态更新DTO
 * 用于接收前端的失物招领物品状态更新请求
 */
@Data
public class LostFoundItemStatusDTO {
    /**
     * 物品ID
     */
    private Long itemId;
    
    /**
     * 状态（PENDING-待解决，RESOLVED-已解决）
     */
    private String status;
    
    /**
     * 解决描述
     */
    private String description;
}