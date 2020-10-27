package com.dataee.tutorserver.tutoradminserver.adminmanage.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

/**
 * 创建管理员
 *
 * @author JinYue
 * @CreateDate 2019/5/21 15:12
 */
@Data
@AllArgsConstructor
public class AdminRequestBean {
    private String adminId;
    @NotBlank(message = "用户名不能为空")
    private String adminName;
    @NotNull(message = "未添加管理员类型")
    private Integer roleId;
    @NotBlank(message = "手机号不能为空")
    private String account;
    @NotBlank(message = "密码不能为空")
    private String password;
    private String position;
    private String remark;
    private Integer departmentId;

    public AdminRequestBean() {
        this.adminId = UUID.randomUUID().toString();
    }
}
