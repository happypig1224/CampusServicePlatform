package com.shxy.w202350766.campusserviceplatform.pojo.dto;

import lombok.Data;

/**
 * 失物招领收藏DTO
 * 用于接收前端提交的失物招领收藏数据
 */
@Data
public class LostFoundCollectDTO {
    /**
     * 物品ID
     */
    private Long itemId;
}