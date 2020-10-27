package com.dataee.tutorserver.tutoradminserver.adminmanage.service.impl;

import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.commons.errorInfoenum.ServiceExceptionsEnum;
import com.dataee.tutorserver.commons.exceptions.SQLOperationException;
import com.dataee.tutorserver.entity.AdminRole;
import com.dataee.tutorserver.entity.Administrator;
import com.dataee.tutorserver.entity.Role;
import com.dataee.tutorserver.tutoradminserver.adminmanage.bean.AdminManage;
import com.dataee.tutorserver.tutoradminserver.adminmanage.bean.AdminPasswordRequestBean;
import com.dataee.tutorserver.tutoradminserver.adminmanage.dao.AdminManageMapper;
import com.dataee.tutorserver.tutoradminserver.adminmanage.service.IAdminManageService;
import com.dataee.tutorserver.utils.EncodeUtil;
import com.dataee.tutorserver.utils.PageInfoUtil;
import com.dataee.tutorserver.utils.SecurityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/5 21:26
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AdminManageServiceImpl implements IAdminManageService {
    @Autowired
    private AdminManageMapper adminManageMapper;

    @Override
    public NewPageInfo getAdminList(Page page, String keyword, Integer roleId) {
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<AdminManage> admins = adminManageMapper.getAdminList(keyword, roleId);
        PageInfo pageInfo = new PageInfo(admins);
        NewPageInfo newPageInfo = PageInfoUtil.read(pageInfo);
        return newPageInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeAdmin(AdminManage adminManage) throws BaseServiceException {
        try {
            int number = adminManageMapper.changeAdminBasicInfo(adminManage);
            if (number != 1) {
                throw new SQLOperationException();
            }
            number = adminManageMapper.changeAdminRole(adminManage.getRoleId(), adminManage.getAdminAuthId());
            if (number != 1) {
                throw new SQLOperationException();
            }
        } catch (DuplicateKeyException e) {
            throw new BaseServiceException(ServiceExceptionsEnum.DATA_EXIST);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAdmin(Integer id) throws BaseServiceException {
        adminManageMapper.deleteAdmin(id);
        int adminAuthId = adminManageMapper.getAdminAuthId(id);
        int number = adminManageMapper.deleteAdminAuth(adminAuthId);
        if (number != 1) {
            throw new SQLOperationException();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void transferAdminPower(Integer adminAuthId) throws BaseServiceException, BaseControllerException {
        int number = adminManageMapper.changeAdminRole(100, adminAuthId);
        if (number != 1) {
            throw new SQLOperationException();
        }
        int id = SecurityUtil.getPersonId();
        int oldAdminAuthId = adminManageMapper.getAdminAuthId(id);
        adminManageMapper.changeAdminRole(102, oldAdminAuthId);
    }

    @Override
    public List<AdminRole> getAdminRole() {
        return adminManageMapper.getAdminRole();
    }


    @Override
    public void changeAdminPasswordById(AdminPasswordRequestBean adminPassword) throws SQLOperationException {
        //    获取admin的id
        String adminId = adminManageMapper.queryAdminIdById(adminPassword.getId());
        if (adminId != null) {
            String encodeNewPassword = EncodeUtil.encodePassword(adminPassword.getNewPassword(), adminId);
            adminManageMapper.updateAdminPasswordById(adminPassword.getId(), encodeNewPassword);
        } else {
            throw new SQLOperationException();
        }
    }
}
