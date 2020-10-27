package com.dataee.tutorserver.entity;

import lombok.Data;

/**
 * 应用的用户
 *
 * @Author JinYue
 * @CreateDate 2019/4/18
 */
@Data
public class User {
    private Integer id;
    private String userId;
    private String username;
    private String password;
    private Role role;
    private String crtDate;
    private String modDate;
    private Integer state;
}
