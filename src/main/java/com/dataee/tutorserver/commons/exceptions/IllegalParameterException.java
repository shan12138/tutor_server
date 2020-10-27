package com.dataee.tutorserver.commons.exceptions;

import com.dataee.tutorserver.commons.errorInfoenum.ControllerExceptionEnum;
import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;

/**
 * 处理客户端传来的参数的异常信息
 *
 * @author JinYue
 */
public class IllegalParameterException extends BaseControllerException {
    private String reasonPhrase;

    public IllegalParameterException() {
        super(ControllerExceptionEnum.ILLEGAL_PARAMETER);
    }

    public IllegalParameterException(String reasonPhrase) {
        super(ControllerExceptionEnum.ILLEGAL_PARAMETER, reasonPhrase);
        this.reasonPhrase = reasonPhrase;
    }

    public String getReasonPhrase() {
        return this.reasonPhrase;
    }
}
