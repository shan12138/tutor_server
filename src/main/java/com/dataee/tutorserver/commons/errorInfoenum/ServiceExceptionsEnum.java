package com.dataee.tutorserver.commons.errorInfoenum;


/**
 * 当前业务发生的错误类型
 *
 * @author JinYue
 */
public enum ServiceExceptionsEnum {
    DATA_PROCESSING_ERRORS(700, "数据信息处理异常"),
    UNKNOWNACCOUNT(711, "用户名或密码错误"),
    USER_EXIST(710, "用户已存在"),
    USER_NOT_EXIST(712, "用户不存在"),
    ROLE_NOT_EXIT(713, "该角色不存在"),
    FAIL_PROVISION_AUTHORITY(714, "权限获取失败"),
    //短信服务异常码
    MESSAGE_SERVER(715, "短信验证服务器异常"),
    MESSAGE_CLIENT(716, "短信验证客户端异常"),
    MESSAGE_IOERROR(717, "短信响应时数据转换发生异常"),
    MESSAGE_FAIL(720, "短信请求错误"),
    UNKNOWN_DATA(718, "出现未知的数据"),
    FILE_NOT_EXIT(722, "文件不存在"),
    FILE_IO_EXCEPTION(721, "获取文件流失败"),
    OBJECT_OPERATE_EXCEPTION(724, "文件对象操作异常"),
    CLIENT_OPERATE_EXCEPTION(723, "文件存储服务异常"),
    DATA_EXIST(730, "数据已存在"),
    NONE_COURSE_ATTEND(731, "尚无课程需要签到/签退"),
    CANT_ATTEND(732, "当前时间不属于签到时间段内，不能进行签到/签退"),
    COURSE_ATTEND_EXIST(733, "当前课程已签过到/签过退"),
    CANT_CHECK_OUT(734, "尚未签到不能签退"),
    CANT_CHANGE_STATE(735, "非正式状态不能被禁用/启用"),
    CANT_DELETE_COURSE(736, "已分配课程不能删除"),
    CANT_DISTRIBUTE_TEACHER(737, "已分配过该教师不能再次分配"),
    COURSE_EXISTS(738, "该课程信息已存在"),
    FAIL_SAVE_LESSONS(740, "存储课程信息失败，请刷新后重试"),
    CANT_CHANGE_USING(739, "正在使用中的年级或科目不能被修改"),
    CANT_DELETE_USING(741, "不能删除部门存在子部门的部门"),
    FAIL_SAVE_LEISURE(742, "更新课余时间失败，请刷新后重新尝试"),
    CANT_DELETE_ROOT(743, "不能删除根部门"),
    PASSWORD_ERROR(745, "密码验证失败"),
    WRONG_OPERATION(744, "错误请求，剩余的课时记录为负,请检查后重新操作"),
    FILE_ZIP_FAIL(749, "文件压缩异常"),
    FILE_IO_FAIL(748, "文件操作失败"),
    CANT_DISTRIBUTE_SPEAKING(746, "已安排教师的课程不能分配试讲"),
    CANT_ATTEND_WITHOUT_READ(747, "尚未阅读当堂课件，不能签到"),
    CANT_ATTEND_WITHOUT_RESOURCE(748, "课堂尚未上传课件，不能签到"),
    PERMISSION_NOT_FOUND(1000, "权限不存在"),
    ADMINROLE_NOT_FOUND(1001, "角色不存在"),
    PRODUCT_NOT_FOUND(1002, "产品不存在"),
    PRODUCT_ATTRIBUTE_NOT_FOUND(1003, "产品字段不存在"),
    PARENT_LEVEL_NOT_FOUND(1004, "家长等级不存在"),
    CANT_DELETE_PARENT_LEVEL(1005, "不能删除中间等级"),
    TEACHER_LEVEL_NOT_FOUND(1006, "教师不存在"),
    PARTNER_NOT_FOUND(1007, "合伙人不存在"),
    SYSTEMADMINROLE_CANT_DELETE(1008, "系统创建角色不能删除"),
    PARENT_INVITATION_NOT_FOUND(1009, "受邀家长信息不存在"),
    WE_CHAT_USER_NOT_EXIST(1010, "合伙人尚未绑定微信账户"),
    PARTNER_EXISTS(1011, "合伙人已存在"),
    INVITE_CODE_NOT_FOUND(1012, "邀请码不存在"),
    WITHDRAW_NOT_FOUND(1013, "提现申请不存在"),
    INVITE_TEACHER_EXIST(1014, "该教师已被邀请"),
    PARENT_ACCOUNT_NOT_MATCH(1015, "该家长尚未匹配账号，不能添加账号"),
    NOT_CHANGE_TEACHER(1016, "不能更换老师"),
    FORM_TEACHER_EXIST(1017, "该教师已任教"),
    NO_REGISTER(1018, "该教师没有注册app"),
    NO_INVITED(1019, "您不能邀请其他老师"),
    MATCH_EXISTS(1020, "该家长已匹配过");
    private int status;
    private String reasonPhrase;

    ServiceExceptionsEnum(int status, String reasonPhrase) {
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
        return this.reasonPhrase;
    }
}
