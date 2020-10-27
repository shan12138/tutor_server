package com.dataee.tutorserver.commons.baseexceptions;

import com.dataee.tutorserver.commons.errorInfoenum.ControllerExceptionEnum;

/**
 * 当前后台控制器中发生错误的异常的基类
 *
 * @author JinYue
 */
public class BaseControllerException extends Exception {
    private int status;

    /**
     * 接受枚举类中的异常信息
     *
     * @param controllerExceptionEnum
     */
    public BaseControllerException(ControllerExceptionEnum controllerExceptionEnum) {
        this(controllerExceptionEnum.getStatus(), controllerExceptionEnum.toString());
    }

    /**
     * 接受枚举类中的异常信息
     * 可重写异常原因
     *
     * @param controllerExceptionEnum
     * @param reasonPhrase
     */
    public BaseControllerException(ControllerExceptionEnum controllerExceptionEnum, String reasonPhrase) {
        this(controllerExceptionEnum.getStatus(), controllerExceptionEnum.getStatus() + ":" + reasonPhrase);
    }


    private BaseControllerException(int status, String reasonPhrase) {
        super(reasonPhrase);
        this.status = status;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
