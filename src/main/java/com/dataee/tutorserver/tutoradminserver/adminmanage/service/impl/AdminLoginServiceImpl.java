package com.dataee.tutorserver.tutoradminserver.adminmanage.service.impl;

import com.dataee.tutorserver.commons.bean.UserPrincipals;
import com.dataee.tutorserver.tutoradminserver.adminmanage.dao.AdminLoginMapper;
import com.dataee.tutorserver.tutoradminserver.adminmanage.service.IAdminLoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author JinYue
 * @CreateDate 2019/5/14 3:03
 */
@Service
public class AdminLoginServiceImpl implements IAdminLoginService {
    private final Logger logger = LoggerFactory.getLogger(AdminLoginServiceImpl.class);
    @Autowired
    private AdminLoginMapper adminLoginMapper;

    @Override
    public UserPrincipals getPrincipals(String account, String role) {
        UserPrincipals principals = adminLoginMapper.queryPrincipalsByAdminNameAndROle(account);
        return principals;
    }

    @Override
    public String findSecretByAdminId(String adminId) {
        String secret = adminLoginMapper.querySecretByAdminId(adminId);
        return secret;
    }
}
