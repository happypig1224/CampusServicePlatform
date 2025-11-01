package com.shxy.w202350766.campusserviceplatform.constant;

/**
 * @Author 吴汇明
 * @School 绥化学院
 * @CreateTime
 */
public class ProductStatusEnum {
    /*'AUDITING', 'ON_SALE', 'SOLD', 'OFF_SHELF'*/
    /**
     * 待审核
     */
    public static final String WAITING_AUDIT = "AUDITING";
    /**
     * 审核通过
     */
    public static final String AUDIT_PASS = "ON_SALE";
    /**
     * 已销售
     */
    public static final String SOLD = "SOLD";
    /**
     * 审核拒绝
     */
    public static final String AUDIT_REJECT = "OFF_SHELF";
}
