package com.shxy.w202350766.campusserviceplatform.pojo.vo;

import lombok.Data;
import java.util.Date;

/**
 * 失物招领认领VO
 * 用于返回给前端的失物招领认领数据
 */
@Data
public class LostFoundClaimVO {
    /**
     * 认领ID
     */
    private Long id;
    
    /**
     * 物品ID
     */
    private Long itemId;
    
    /**
     * 认领人ID
     */
    private Long userId;
    
    /**
     * 认领人用户名
     */
    private String username;
    
    /**
     * 认领人头像
     */
    private String avatar;
    
    /**
     * 认领描述
     */
    private String description;
    
    /**
     * 联系方式
     */
    private String contact;
    
    /**
     * 认领状态（PENDING-待审核，APPROVED-已通过，REJECTED-已拒绝）
     */
    private String status;
    
    /**
     * 认领状态文本（用于显示）
     */
    private String statusText;
    
    /**
     * 认领状态颜色（用于显示）
     */
    private String statusColor;
    
    /**
     * 认领时间
     */
    private Date createTime;
    
    /**
     * 格式化后的认领时间（用于显示）
     */
    private String createTimeText;
    
    /**
     * 审核时间
     */
    private Date auditTime;
    
    /**
     * 格式化后的审核时间（用于显示）
     */
    private String auditTimeText;
    
    /**
     * 审核人ID
     */
    private Long auditorId;
    
    /**
     * 审核人用户名
     */
    private String auditorName;
    
    /**
     * 审核意见
     */
    private String auditRemark;
}