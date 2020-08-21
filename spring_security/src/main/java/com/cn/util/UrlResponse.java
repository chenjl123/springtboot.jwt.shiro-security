package com.cn.util;

import lombok.Data;

import java.io.Serializable;

/**
 *
 * 返回给前端的json数据格式
 *
 */
@Data
public class UrlResponse implements Serializable {
    private int code;
    private String message;
    private Object data;
    private int total;

    @Override
    public String toString() {
        return "UrlResponse{" +
                " code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", total=" + total +
                '}';
    }
}

