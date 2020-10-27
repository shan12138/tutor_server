package com.dataee.tutorserver.tutoradminserver.adminmanage.service.impl;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.exceptions.SQLOperationException;
import com.dataee.tutorserver.tutoradminserver.adminmanage.bean.AdminRequestBean;
import com.dataee.tutorserver.tutoradminserver.adminmanage.dao.AdminRegisterMapper;
import com.dataee.tutorserver.tutoradminserver.adminmanage.service.IAdminRegisterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author JinYue
 * @CreateDate 2019/5/21 16:23
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class AdminRegisterServiceImpl implements IAdminRegisterService {
    private final Logger logger = LoggerFactory.getLogger(AdminRegisterServiceImpl.class);

    @Autowired
    private AdminRegisterMapper adminRegisterMapper;

    @Override
    public void addAddmin(String crtId, AdminRequestBean adminRequestBean) throws SQLOperationException {
        //    创建管理员账号
        int count = adminRegisterMapper.addNewAdmin(crtId, adminRequestBean);
        //    添加管理员角色
        if (count == 1) {
            int result = adminRegisterMapper.addAdminAuth(crtId, adminRequestBean.getRoleId(), adminRequestBean.getAdminId());
            if (result != 1) {
                throw new SQLOperationException();
            }
        } else {
            throw new SQLOperationException();
        }

    }
}
