package com.dataee.tutorserver.commons.commonservice;

import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;

/**
 * 获取OSS访问权限
 *
 * @author JinYue
 * @CreateDate 2019/5/11 15:55
 */
public interface ISTSProvisionAuthorityService {
    /**
     * 根据当前的角色去获取自定义的权限
     *
     * @param roleId
     * @param keyWord 代表当前全向对应的资源关键词
     * @return
     */
    String getPolicyByRole(String keyWord, Integer roleId) throws BaseServiceException;

    /**
     * 根据关键字和角色查找权限
     *
     * @param keyWord
     * @param role
     * @param sessionName 定义sessionName
     * @return
     */
    AssumeRoleResponse.Credentials getUserResourceCredentials(String keyWord, String role, String sessionName) throws BaseServiceException;

    /**
     * 直接根据自定义的policy获取临时权限
     *
     * @param policy
     * @return
     */
    AssumeRoleResponse.Credentials getProvisionCredentials(String policy, String sessionName) throws BaseServiceException;

    /**
     * 管理员获取合同权限
     *
     * @param keyWord
     * @param role
     * @param sessionName
     * @return
     */
    AssumeRoleResponse.Credentials getAdminContractCredentials(String keyWord, String role, String sessionName) throws BaseServiceException;

}
