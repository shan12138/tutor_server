package com.dataee.tutorserver.tutoradminserver.rolemanage.service.impl;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.commons.errorInfoenum.ServiceExceptionsEnum;
import com.dataee.tutorserver.entity.AdminRole;
import com.dataee.tutorserver.entity.Permission;
import com.dataee.tutorserver.tutoradminserver.rolemanage.bean.CreateRoleRequestBean;
import com.dataee.tutorserver.tutoradminserver.rolemanage.bean.RolePermissionGrant;
import com.dataee.tutorserver.tutoradminserver.rolemanage.bean.UpdateRoleRequestBean;
import com.dataee.tutorserver.tutoradminserver.rolemanage.dao.RoleManageMapper;
import com.dataee.tutorserver.tutoradminserver.rolemanage.service.IRoleManageService;
import com.dataee.tutorserver.utils.PageInfoUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/4 11:21
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleManageServiceImpl implements IRoleManageService {
    @Autowired
    private RoleManageMapper roleManageMapper;

    @Override
    public NewPageInfo getRoleList(Page page, String keyword) {
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<AdminRole> role = roleManageMapper.getRoleList(keyword);
        PageInfo pageInfo = new PageInfo(role);
        NewPageInfo newPageInfo = PageInfoUtil.read(pageInfo);
        return newPageInfo;
    }

    @Override
    public List<Permission> getPermissionsOfRole(Integer roleId) {
        return roleManageMapper.getPermissionsOfRole(roleId);
    }

    @Override
    public List<Permission> getPermissionsOfCurrentUser(Integer roleId) {
        return roleManageMapper.getPermissionsOfCurrentUser(roleId);
    }

    @Override
    public void createRole(CreateRoleRequestBean role, int createdId) throws BaseServiceException {
        AdminRole newRole = new AdminRole(role.getRemark(), 1, createdId);
        roleManageMapper.createRole(newRole);
        for(RolePermissionGrant one : role.getPermissions()) {
            Permission permission = roleManageMapper.getPermissionById(one.getPermissionId());
            if(permission == null) {
                throw new BaseServiceException(ServiceExceptionsEnum.PERMISSION_NOT_FOUND);
            }
            roleManageMapper.insertPermissionOfRole(newRole.getRoleId(), permission.getId(), one.isGrant());
        }
    }

    @Override
    public void updateRole(int roleId, UpdateRoleRequestBean role) throws BaseServiceException {
        AdminRole adminRole = roleManageMapper.getRoleById(roleId);
        if(adminRole == null) {
            throw new BaseServiceException(ServiceExceptionsEnum.ADMINROLE_NOT_FOUND);
        }
        roleManageMapper.updateRoleRemark(roleId, role.getRemark());
        for(RolePermissionGrant one : role.getAddPermissions()) {
            Permission permission = roleManageMapper.getPermissionById(one.getPermissionId());
            if(permission == null) {
                throw new BaseServiceException(ServiceExceptionsEnum.PERMISSION_NOT_FOUND);
            }
            roleManageMapper.insertPermissionOfRole(roleId, permission.getId(), one.isGrant());
        }
        for(RolePermissionGrant one : role.getUpdatePermissions()) {
            Permission permission = roleManageMapper.getPermissionById(one.getPermissionId());
            if(permission == null) {
                throw new BaseServiceException(ServiceExceptionsEnum.PERMISSION_NOT_FOUND);
            }
            roleManageMapper.updatePermissionOfRole(roleId, permission.getId(), one.isGrant());
        }
        for(int id : role.getDeletePermissions()) {
            Permission permission = roleManageMapper.getPermissionById(id);
            if(permission == null) {
                throw new BaseServiceException(ServiceExceptionsEnum.PERMISSION_NOT_FOUND);
            }
            roleManageMapper.deletePermissionOfRole(roleId, permission.getId());
        }
    }

    @Override
    public void deleteRole(int roleId) throws BaseServiceException {
        AdminRole adminRole = roleManageMapper.getRoleById(roleId);
        if(adminRole == null) {
            throw new BaseServiceException(ServiceExceptionsEnum.ADMINROLE_NOT_FOUND);
        }
        if(adminRole.getIsSystemCreated()) {
            throw new BaseServiceException(ServiceExceptionsEnum.SYSTEMADMINROLE_CANT_DELETE);
        }
        roleManageMapper.deleteAllPermissionOfRole(roleId);
        roleManageMapper.deleteRole(roleId);
    }

    @Override
    public List<Permission> getAllPermissions() {
        return roleManageMapper.getAllPermissions();
    }
}
