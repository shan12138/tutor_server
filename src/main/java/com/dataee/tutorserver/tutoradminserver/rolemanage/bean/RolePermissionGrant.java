package com.dataee.tutorserver.tutoradminserver.rolemanage.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/4 15:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolePermissionGrant {
    private int permissionId;
    private boolean grant;
}
