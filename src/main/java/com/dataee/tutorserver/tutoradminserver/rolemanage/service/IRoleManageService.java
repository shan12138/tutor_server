package com.dataee.tutorserver.tutoradminserver.rolemanage.service;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.entity.Permission;
import com.dataee.tutorserver.tutoradminserver.rolemanage.bean.CreateRoleRequestBean;
import com.dataee.tutorserver.tutoradminserver.rolemanage.bean.UpdateRoleRequestBean;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/4 11:21
 */
public interface IRoleManageService {
    NewPageInfo getRoleList(Page page, String keyword);

    List<Permission> getPermissionsOfRole(Integer roleId);

    List<Permission> getPermissionsOfCurrentUser(Integer roleId);

    void createRole(CreateRoleRequestBean role, int createdId) throws BaseServiceException;

    void updateRole(int roleId, UpdateRoleRequestBean role) throws BaseServiceException;

    void deleteRole(int roleId) throws BaseServiceException;

    List<Permission> getAllPermissions();
}
