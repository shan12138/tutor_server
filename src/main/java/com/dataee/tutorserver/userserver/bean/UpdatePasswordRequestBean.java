package com.dataee.tutorserver.userserver.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 修改用户密码
 *
 * @author JinYue
 * @CreateDate 2019/4/29 0:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordRequestBean {
    @NotBlank
    @Length(min = 6, max = 20, message = "用户密码应为6-20位")
    private String oldPassword;
    @NotBlank
    @Length(min = 6, max = 20, message = "用户密码应为6-20位")
    private String password;
}
