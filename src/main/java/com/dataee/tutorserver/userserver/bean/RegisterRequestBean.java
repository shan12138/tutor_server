package com.dataee.tutorserver.userserver.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 用户登录请求信息
 * 复用
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestBean {
    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 20, message = "密码长度应为6-20位")
    private String password;
}
