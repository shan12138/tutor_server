package com.dataee.tutorserver.tutoradminserver.adminmanage.service;

import com.dataee.tutorserver.commons.exceptions.SQLOperationException;
import com.dataee.tutorserver.tutoradminserver.adminmanage.bean.AdminRequestBean;

/**
 * @author JinYue
 * @CreateDate 2019/5/21 16:21
 */
public interface IAdminRegisterService {
    /**
     * 增加管理员
     *
     * @param crtId
     * @param adminRequestBean
     */
    void addAddmin(String crtId, AdminRequestBean adminRequestBean) throws SQLOperationException;
}
