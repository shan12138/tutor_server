package com.dataee.tutorserver.tutoradminserver.adminmanage.service;

import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.commons.exceptions.SQLOperationException;
import com.dataee.tutorserver.entity.AdminRole;
import com.dataee.tutorserver.entity.Role;
import com.dataee.tutorserver.tutoradminserver.adminmanage.bean.AdminManage;
import com.dataee.tutorserver.tutoradminserver.adminmanage.bean.AdminPasswordRequestBean;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/5 21:26
 */
public interface IAdminManageService {
    NewPageInfo getAdminList(Page page, String keyword, Integer roleId);

    void changeAdmin(AdminManage adminManage) throws BaseServiceException;

    void deleteAdmin(Integer id) throws BaseServiceException;

    /**
     * 修改指定管理员的密码
     *
     * @param adminPassword
     */
    void changeAdminPasswordById(AdminPasswordRequestBean adminPassword) throws SQLOperationException;

    void transferAdminPower(Integer adminAuthId) throws BaseServiceException, BaseControllerException;

    List<AdminRole> getAdminRole();
}
