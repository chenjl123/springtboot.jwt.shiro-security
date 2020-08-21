package com.cn.shiro.exception;

import lombok.Data;

/**
 * 自定义异常
 */
@Data
public class BusinessException extends RuntimeException{
    private int code;
    private String msg;

    public BusinessException() {
    }

    public BusinessException(int code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
