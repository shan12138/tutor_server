package com.dataee.tutorserver.userserver.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户登录返回的信息
 *
 * @author JinYue
 * @CreateDate 2019/5/22 23:11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseBean {
    private String name;
    private Integer state;
}
