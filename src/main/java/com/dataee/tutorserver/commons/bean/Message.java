package com.dataee.tutorserver.commons.bean;

import lombok.Data;


/**
 * 构造ResponseEntity
 *
 * @param <T> 后期改动
 * @author JinYue
 */
@Data
public class Message<T> {
    private String success;
    private String msg;
    private T data;


    public Message() {
        this.success = "OK";
        this.msg = "当前请求成功";
        this.data = null;
    }


    /**
     * 接受当前的请求状态和请求的信息
     *
     * @param isSuccess
     * @param statusInfo
     */
    public Message(Boolean isSuccess, String statusInfo) {
        this.success = isSuccess ? "OK" : "ERROR";
        this.msg = statusInfo;
        this.data = null;
    }

    /**
     * 请求带请求体
     *
     * @param isSuccess
     * @param data
     */
    public Message(Boolean isSuccess, T data) {
        if (isSuccess) {
            this.success = "OK";
            this.msg = "当前请求成功";
        } else {
            this.success = "ERROR";
            this.msg = "当前请求错误";
        }
        this.data = data;
    }

    /**
     * 接受带数据体的参数
     *
     * @param isSuccess
     * @param msg
     * @param data
     */
    public Message(Boolean isSuccess, String msg, T data) {
        this.success = isSuccess ? "OK" : "ERROR";
        this.msg = msg;
        this.data = data;
    }
}
