package com.shxy.w202350766.campusserviceplatform.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 操作日志表
 * @TableName operation_log
 */
@TableName(value ="operation_log")
@Data
public class OperationLog {
    /**
     * 日志ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 操作用户ID
     */
    private Long userId;

    /**
     * 操作模块
     */
    private String module;

    /**
     * 操作内容
     */
    private String operation;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 用户代理
     */
    private String userAgent;

    /**
     * 请求参数
     */
    private String params;

    /**
     * 操作结果
     */
    private Object result;

    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 执行时间（毫秒）
     */
    private Long executeTime;

    /**
     * 创建时间
     */
    private Date createTime;
}