package com.shxy.w202350766.suiestation.exception;

/**
 * @Author 吴汇明
 * @School 绥化学院
 * @CreateTime
 */
public class UserNameExists extends RuntimeException{
    public UserNameExists() {
        super("用户名已存在");
    }
}
