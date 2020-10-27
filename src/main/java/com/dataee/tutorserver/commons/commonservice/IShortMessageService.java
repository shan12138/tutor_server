package com.dataee.tutorserver.commons.commonservice;


import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;

/**
 * 发送短信息服务
 *
 * @author JinYue
 * @CreateDate 2019/5/3 13:27
 */
public interface IShortMessageService {
    /**
     * 发送验证码短信
     *
     * @param phoneNumber
     * @return
     */
    String sendMessage(String phoneNumber) throws BaseServiceException;
}
