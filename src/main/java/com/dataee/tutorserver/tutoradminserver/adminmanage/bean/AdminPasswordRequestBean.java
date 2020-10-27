package com.dataee.tutorserver.tutoradminserver.adminmanage.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 管理员修改指定管理员的密码
 *
 * @author JinYue
 * @CreateDate 2019/5/22 15:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminPasswordRequestBean {
    @NotNull(message = "")
    private Integer id;
    @NotBlank(message = "")
    private String newPassword;
}
