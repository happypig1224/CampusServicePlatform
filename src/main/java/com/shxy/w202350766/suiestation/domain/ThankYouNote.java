package com.shxy.w202350766.suiestation.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 感谢信表
 * @TableName thank_you_note
 */
@TableName(value ="thank_you_note")
@Data
public class ThankYouNote {
    /**
     * 感谢信ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 失物招领ID
     */
    private Long lostFoundId;

    /**
     * 发布用户ID
     */
    private Long userId;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 图片列表
     */
    private Object images;

    /**
     * 是否公开
     */
    private Integer isPublic;

    /**
     * 创建时间
     */
    private Date createTime;
}