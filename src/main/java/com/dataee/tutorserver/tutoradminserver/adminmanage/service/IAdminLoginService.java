package com.dataee.tutorserver.tutoradminserver.adminmanage.service;

import com.dataee.tutorserver.commons.bean.UserPrincipals;

/**
 * @author JinYue
 * @CreateDate 2019/5/14 3:03
 */
public interface IAdminLoginService {
    /**
     * 获取管理员的口令
     *
     * @param adminId
     * @return
     */
    String findSecretByAdminId(String adminId);

    /**
     * 获取管理员的凭证信息
     *
     * @param adminName
     * @param role
     * @return
     */
    UserPrincipals getPrincipals(String adminName, String role);
}
