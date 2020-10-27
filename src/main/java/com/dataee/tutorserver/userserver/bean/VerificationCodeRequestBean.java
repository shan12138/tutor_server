package com.dataee.tutorserver.userserver.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 验证码
 *
 * @author JinYue
 * @CreateDate 2019/4/27 21:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerificationCodeRequestBean {
    @NotBlank(message = "验证码不能为空")
    @Size(min = 6, max = 6, message = "验证码输入有误")
    private String verificationCode;
}
