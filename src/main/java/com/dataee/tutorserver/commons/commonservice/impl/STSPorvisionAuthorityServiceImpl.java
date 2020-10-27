package com.dataee.tutorserver.commons.commonservice.impl;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.commonservice.ISTSProvisionAuthorityService;
import com.dataee.tutorserver.commons.errorInfoenum.ServiceExceptionsEnum;
import com.dataee.tutorserver.entity.Policy;
import com.dataee.tutorserver.userserver.dao.UserMapper;
import com.dataee.tutorserver.userserver.dao.PolicyMapper;
import com.dataee.tutorserver.utils.PolicyToJsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author JinYue
 * @CreateDate 2019/5/11 16:27
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class STSPorvisionAuthorityServiceImpl implements ISTSProvisionAuthorityService {
    private final Logger logger = LoggerFactory.getLogger(STSPorvisionAuthorityServiceImpl.class);

    @Autowired
    private DefaultAcsClient stsClient;
    @Autowired
    private AssumeRoleRequest stsRequest;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PolicyMapper policyMapper;

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    private final String SERVICE = "oss";

    @Override
    public String getPolicyByRole(String keyWord, Integer roleId) throws BaseServiceException {
        if (roleId != null) {
            //    根据keyWord和roleId查询出我要的权限
            Policy policy = policyMapper.queryPolicyByRoleAndService(roleId, keyWord, SERVICE);
            if (policy == null) {
                throw new BaseServiceException(ServiceExceptionsEnum.FAIL_PROVISION_AUTHORITY);
            }
            //    生成有效的Policy字符串
            String policyJson = PolicyToJsonUtil.serializer(policy);
            logger.info(policyJson);
            return policyJson;
        } else {
            throw new BaseServiceException(ServiceExceptionsEnum.ROLE_NOT_EXIT);
        }
    }

    @Override
    public AssumeRoleResponse.Credentials getProvisionCredentials(String policy, String sessionName) throws BaseServiceException {
        try {
            stsRequest.setPolicy(policy);
            stsRequest.setRoleSessionName(sessionName);
            final AssumeRoleResponse response = stsClient.getAcsResponse(stsRequest);
            AssumeRoleResponse.Credentials credentials = response.getCredentials();
            return credentials;
        } catch (ClientException e) {
            logger.error(e.getMessage(), e);
            throw new BaseServiceException(ServiceExceptionsEnum.FAIL_PROVISION_AUTHORITY);
        }
    }

    @Override
    public AssumeRoleResponse.Credentials getUserResourceCredentials(String keyWord, String role, String sessionName) throws BaseServiceException {
        Integer roleId = userMapper.queryRoleIdByRole(role);
        String policy = getPolicyByRole(keyWord, roleId);
        if (policy == null || policy.length() == 0) {
            logger.error("该用户没有该权限！");
            throw new BaseServiceException(ServiceExceptionsEnum.FAIL_PROVISION_AUTHORITY);
        }
        AssumeRoleResponse.Credentials credentials = getProvisionCredentials(policy, sessionName);
        return credentials;
    }

    @Override
    public AssumeRoleResponse.Credentials getAdminContractCredentials(String keyWord, String role, String sessionName) throws BaseServiceException {
        //获取contract的policy
        Integer roleId = userMapper.queryRoleIdByAdminRole(role);
        String contractPolicy = getPolicyByRole(keyWord, roleId);
        AssumeRoleResponse.Credentials credentials = getProvisionCredentials(contractPolicy, sessionName);
        return credentials;
    }
}
