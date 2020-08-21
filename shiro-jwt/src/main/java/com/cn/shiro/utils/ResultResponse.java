package com.cn.shiro.utils;

import lombok.Data;

import java.io.Serializable;

/**
 *
 * 返回给前端的json数据格式
 *
 */
@Data
public class ResultResponse implements Serializable {
    private int code;
    private String message;
    private Object data;
    private int total;

    public ResultResponse(int code, String message){
        this.code = code;
        this.message = message;
    }
    @Override
    public String toString() {
        return "{" +
                " code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", total=" + total +
                '}';
    }
}

