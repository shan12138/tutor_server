package com.dataee.tutorserver.commons.exceptions;

import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.errorInfoenum.ControllerExceptionEnum;

/**
 * 用户信息获取异常
 *
 * @author JinYue
 * @CreateDate 2019/4/29 0:22
 */
public class NullUserInformationException extends BaseControllerException {
    public NullUserInformationException() {
        super(ControllerExceptionEnum.NULL_USER_INFO);
    }
}
