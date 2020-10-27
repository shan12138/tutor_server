package com.dataee.tutorserver.commons.baseexceptions;

import com.dataee.tutorserver.commons.errorInfoenum.ServiceExceptionsEnum;

/**
 * 当前后台业务中发生错误的异常的基类
 *
 * @author JinYue
 */
public class BaseServiceException extends Exception {
    private int status;

    public BaseServiceException(ServiceExceptionsEnum serviceExceptionsEnum) {
        this(serviceExceptionsEnum.getStatus(), serviceExceptionsEnum.getReasonPhrase());
    }

    public BaseServiceException(ServiceExceptionsEnum serviceExceptionsEnum, String reasonPhrase) {
        this(serviceExceptionsEnum.getStatus(), serviceExceptionsEnum.getStatus() + ":" + reasonPhrase);
    }

    private BaseServiceException(int status, String reasonPhrase) {
        super(reasonPhrase);
        this.status = status;
    }

    public final int getStatus() {
        return this.status;
    }

}
