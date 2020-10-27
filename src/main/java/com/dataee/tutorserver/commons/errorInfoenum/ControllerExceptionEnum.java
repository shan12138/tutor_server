package com.dataee.tutorserver.commons.errorInfoenum;

/**
 * 当前控制器端发生错误的类型
 *
 * @author JinYue
 */
public enum ControllerExceptionEnum {

    //该异常信息为自定义信息(一般发生在字段校验阶段)
    ILLEGAL_PARAMETER(600, "非法参数"),
    INCORRECT_VERIFICATION_CODE(610, "验证码错误"),
    UNKNOWN_PHONE(611, "未获取到手机号，请重试"),
    NULL_USER_INFO(612, "未获取到用户信息，请重新登录"),
    UNKNOWN_FILENAME(613, "未获取到文件名"),
    UNKNOWN_USER(614, "用户不存在"),
    FILE_ERROR(616, "文件操作异常"),
    FILE_INFO_ERROR(618, "文件信息异常"),
    ANALYSIS_ERROR(617, "文件解析错误，请检查后重试！"),
    RE_LOGIN(615, "请重新登录"),
    DATA_EXISTS(616, "该授课范围已添加过"),
    EMPTY_CODE(617, "空的code"),
    UNKNOWN_ERROR(618, "未知错误"),
    USER_CHECK_FAILED(619, "用户信息校验失败"),
    BIND_TELEPHONE_EXISTS(620, "用户已绑定过手机号"),
    WITHDRAW_BEYOND(621, "提现金额超出可提现金额数值");
    private int status;
    private String reasonPhrase;

    ControllerExceptionEnum(int status, String reasonPhrase) {
        this.status = status;
        this.reasonPhrase = reasonPhrase;
    }

    public final int getStatus() {
        return this.status;
    }

    public final String getReasonPhrase() {
        return this.reasonPhrase;
    }

    @Override
    public String toString() {
        return reasonPhrase;
    }
}
