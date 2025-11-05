package com.shxy.w202350766.campusserviceplatform.pojo.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 跑腿任务发布DTO
 * 用于接收前端提交的跑腿任务数据
 */
@Data
public class ErrandTaskDTO {
    /**
     * 任务分类ID
     */
    private Integer categoryId;
    
    /**
     * 任务标题
     */
    private String title;
    
    /**
     * 任务描述
     */
    private String description;
    
    /**
     * 任务报酬
     */
    private BigDecimal reward;
    
    /**
     * 任务地点
     */
    private String location;
    
    /**
     * 起始地点
     */
    private String locationFrom;
    
    /**
     * 目的地
     */
    private String locationTo;
    
    /**
     * 截止时间
     */
    private Date deadline;
    
    /**
     * 联系人姓名
     */
    private String contactName;
    
    /**
     * 联系电话
     */
    private String contactInfo;
    
    /**
     * 是否紧急任务
     */
    private Boolean isUrgent;
    
    /**
     * 上传的图片文件列表
     */
    private List<MultipartFile> images;
    
    /**
     * 任务备注
     */
    private String remark;
}