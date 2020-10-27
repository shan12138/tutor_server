package com.dataee.tutorserver.utils;

import com.dataee.tutorserver.commons.bean.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

/**
 * Response的传送工具
 *
 * @author JinYue
 * 后期改动
 */
public class ResultUtil {
    private static final boolean SUCCESS = true;
    private static final boolean FAIL = false;

    public ResultUtil() {
    }

    /**
     * 接口请求成功返回,带请求体
     *
     * @param object
     * @return
     */
    public static ResponseEntity success(Object object) {
        Message message = new Message(SUCCESS, object);
        return new ResponseEntity(message, HttpStatus.OK);
    }


    public static ResponseEntity success(String msg) {
        Message message = new Message(SUCCESS, msg);
        return new ResponseEntity(message, HttpStatus.OK);
    }

    /**
     * 接口请求成功返回，不带body
     *
     * @return
     */
    public static ResponseEntity success() {
        Message message = new Message();
        return new ResponseEntity(message, HttpStatus.OK);
    }

    /**
     * 带自定义请求头
     *
     * @param status
     * @param headers
     * @param resultMessage
     * @return
     */
    public static ResponseEntity success(HttpStatus status, MultiValueMap<String, String> headers, String resultMessage) {
        Message message = new Message(SUCCESS, resultMessage);
        return new ResponseEntity(message, headers, status);
    }

    /**
     * 带自定义请求头
     *
     * @param status
     * @param headers
     * @param object
     * @return
     */
    public static ResponseEntity success(HttpStatus status, MultiValueMap<String, String> headers, Object object) {
        Message message = new Message(SUCCESS, object);
        return new ResponseEntity(message, headers, status);
    }

    /**
     * 接口请求失败返回,带body
     *
     * @param status
     * @param resultMessage
     * @return
     */
    public static ResponseEntity error(HttpStatus status, String resultMessage) {
        Message message = new Message(FAIL, resultMessage);
        return new ResponseEntity(message, status);
    }

    /**
     * 接口请求失败返回，不带body
     *
     * @param status
     * @return
     */
    public static ResponseEntity error(HttpStatus status) {
        Message message = new Message(FAIL, status.getReasonPhrase());
        return new ResponseEntity(message, status);
    }

    public static ResponseEntity error(HttpStatus status, MultiValueMap<String, String> headers, String resultMessage) {
        Message message = new Message(FAIL, resultMessage);
        return new ResponseEntity(message, headers, status);
    }
}
