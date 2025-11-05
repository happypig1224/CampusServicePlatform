package com.shxy.w202350766.campusserviceplatform.pojo.dto;

import lombok.Data;

/**
 * 跑腿任务状态更新DTO
 * 用于更新跑腿任务状态
 */
@Data
public class ErrandTaskStatusDTO {
    /**
     * 任务ID
     */
    private Long taskId;
    
    /**
     * 任务状态（PENDING-待接单，ACCEPTED-已接单，COMPLETED-已完成，CANCELLED-已取消）
     */
    private String status;
    
    /**
     * 完成证明图片（完成状态时需要）
     */
    private Object proofImages;
    
    /**
     * 备注
     */
    private String remark;
}