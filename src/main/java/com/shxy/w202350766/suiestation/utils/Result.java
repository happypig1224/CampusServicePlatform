package com.shxy.w202350766.suiestation.utils;

/**
 * @Author 吴汇明
 * @School 绥化学院
 * @CreateTime 2025.10.30
 * @Description 统一返回结果类
 */
public class Result<T> {
    private Integer code;
    private String msg;
    private T data;
     public Result() {
    }
    public Result(Integer code, String msg, T data) {
         this.code = code;
         this.msg = msg;
         this.data = data;
    }
    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
     /**
     * 成功返回结果
     * @param data
     * @return
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "success", data);
    }
    /**
     * 失败返回结果
     * @param code
     * @param msg
     * @return
     */
    public static <T> Result<T> fail(Integer code, String msg) {
        return new Result<>(code, msg, null);
    }
    public static <T> Result<T> fail(Integer code, String msg,T data) {
        return new Result<>(code, msg, data);
    }
    public static <T> Result<T> fail(String msg) {
        return new Result<>(400, msg, null);
    }
    
    /**
     * 错误返回结果
     * @param msg
     * @return
     */
    public static <T> Result<T> error(String msg) {
        return new Result<>(401, msg, null);
    }
}