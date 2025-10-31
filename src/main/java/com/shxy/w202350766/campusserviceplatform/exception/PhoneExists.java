package com.shxy.w202350766.campusserviceplatform.exception;

/**
 * @Author 吴汇明
 * @School 绥化学院
 * @CreateTime
 */
public class PhoneExists extends RuntimeException {
    public PhoneExists() {
        super("手机号已存在");
    }
}
