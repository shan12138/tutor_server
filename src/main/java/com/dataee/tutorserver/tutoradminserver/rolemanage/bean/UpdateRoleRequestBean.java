package com.dataee.tutorserver.tutoradminserver.rolemanage.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/4 15:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRoleRequestBean {
    private String remark;
    private List<RolePermissionGrant> addPermissions;
    private List<RolePermissionGrant> updatePermissions;
    private List<Integer> deletePermissions;
}
