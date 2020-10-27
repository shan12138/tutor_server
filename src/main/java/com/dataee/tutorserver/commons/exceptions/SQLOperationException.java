package com.dataee.tutorserver.commons.exceptions;

import com.dataee.tutorserver.commons.errorInfoenum.ServiceExceptionsEnum;
import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;

/**
 * 数据库操作异常
 */
public class SQLOperationException extends BaseServiceException {
    public SQLOperationException() {
        super(ServiceExceptionsEnum.DATA_PROCESSING_ERRORS);
    }
}
