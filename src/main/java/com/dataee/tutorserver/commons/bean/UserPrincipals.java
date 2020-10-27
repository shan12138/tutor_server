package com.dataee.tutorserver.commons.bean;

import com.dataee.tutorserver.entity.Permission;
import com.dataee.tutorserver.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * 当前登录用户的凭证信息
 *
 * @author JinYue
 * @CreateDate 2019/4/29 0:48
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPrincipals {
    private int id;
    private String currRole;
    private String userId;
    private String username;
    private String personName;
    private Integer personId;
    private List<Role> roles;
    private List<Permission> permissions;
    private Integer state;
}
