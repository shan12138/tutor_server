package com.dataee.tutorserver.tutoradminserver.adminmanage.bean;

import com.dataee.tutorserver.entity.Permission;
import com.dataee.tutorserver.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author JinYue
 * @CreateDate 2019/6/22 14:38
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminLoginResponseBean {
    private Integer userId;
    private String username;
    private List<Role> roles;
    private List<Permission> permissions;

    public AdminLoginResponseBean(Integer userId, String username, List<Role> roles) {
        this.userId = userId;
        this.username = username;
        this.roles = roles;
    }
}
