package com.dataee.tutorserver.userserver.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginInfoRequestBean {
    @Length(min = 11, max = 11, message = "用户名长度应为11位")
    private String username;
    @Length(min = 6, max = 20, message = "密码长度应为6到20位")
    private String password;
}

