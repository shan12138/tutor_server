package com.dataee.tutorserver.tutoradminserver.rolemanage.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/4 13:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRoleRequestBean {
    private String remark;
    private List<RolePermissionGrant> permissions;
}
