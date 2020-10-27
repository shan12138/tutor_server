package com.dataee.tutorserver.userserver.service.impl;

import com.dataee.tutorserver.commons.bean.UserPrincipals;
import com.dataee.tutorserver.commons.errorInfoenum.ServiceExceptionsEnum;
import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.exceptions.SQLOperationException;
import com.dataee.tutorserver.entity.User;
import com.dataee.tutorserver.userserver.bean.LoginResponseBean;
import com.dataee.tutorserver.userserver.dao.PersonMapper;
import com.dataee.tutorserver.userserver.dao.UserMapper;
import com.dataee.tutorserver.userserver.service.IUserService;
import com.dataee.tutorserver.utils.EncodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements IUserService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String[] persons = {"admin", "teacher", "parent"};
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PersonMapper personMapper;

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 通过用户名查找用户
     *
     * @param username
     * @return
     */
    @Override
    public User findByName(String username, String role) {

        try {
            User user = userMapper.selectUserByUserName(username, role);
            return user;
        } catch (Exception e) {
            logger.error("用户登录错误：{}", e.getMessage());
            return null;
        }
    }

    /**
     * 用户注册
     *
     * @param userId   用户ID
     * @param username 用户名
     * @param password 用户加密后的密码
     * @param role     身份说明
     * @throws Exception
     */
    @Override
    public void registerUser(String userId, String username, String password, String role) throws BaseServiceException {
        User user = findByName(username, role);
        if (user != null) {
            throw new BaseServiceException(ServiceExceptionsEnum.USER_EXIST);
        } else {
            //获取roleId
            Integer roleId = userMapper.queryRoleIdByRole(role);
            if (roleId == null) {
                throw new BaseServiceException(ServiceExceptionsEnum.ROLE_NOT_EXIT);
            } else {
                //创建用户
                int updateCount = userMapper.insertNewUser(userId, username, password, roleId);
                if (updateCount != 1) {
                    throw new SQLOperationException();
                } else {
                    //添加用户角色
                    //addUserAuth(userId, roleId);
                    if(!role.equals("we_chat_user")) {
                        //添加角色对应的人物账户
                        addPersonAccount(userId, role, username);
                    }
                }
            }
        }
    }

    /**
     * 目前系统暂不使用该功能
     *
     * @param userId
     * @param roleId
     * @throws SQLOperationException
     */
    private void addUserAuth(String userId, Integer roleId) throws SQLOperationException {
        int updateCount = userMapper.insertUserAuth(userId, roleId);
        if (updateCount != 1) {
            throw new SQLOperationException();
        }
    }

    private void addPersonAccount(String userId, String role, String phone) throws SQLOperationException {
        int updateCount = userMapper.insertPersonAccount(userId, role, phone);
        if (updateCount != 1) {
            throw new SQLOperationException();
        }
    }


    @Override
    public void updatePassword(int id, String password) throws BaseServiceException {
        try {
            int count = userMapper.updatePasswordByUserId(id, password);
            if (count != 1) {
                //可能是用户数据更新后id有改动
                throw new NullPointerException();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SQLOperationException();
        }
    }

    @Override
    public Integer getPersonId(String userId, String role) throws BaseServiceException {
        Integer personId = userMapper.queryPersonId(userId, role);
        if (personId == null) {
            throw new BaseServiceException(ServiceExceptionsEnum.USER_NOT_EXIST);
        }
        return personId;
    }

    @Override
    public String findSecretByUserId(String userId) {
        String secret = userMapper.querySecretByUserId(userId);
        return secret;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserPrincipals getPrincipals(String username, String role) {
        UserPrincipals principals = userMapper.queryPrincipals(username, role);
        Integer personId = userMapper.queryPersonId(principals.getUserId(), role);
        Integer state = personMapper.queryPersonState(principals.getUserId(), role);
        principals.setPersonId(personId);
        principals.setState(state);
        return principals;
    }

    @Override
    public List<String> getHomePictures() {
        return userMapper.getHomePictures();
    }

    @Override
    public Integer getPersonRoleId(String userId) {
        Integer roleId = userMapper.queryRoleIdByUserId(userId);
        return roleId;
    }

    @Override
    public void verifyPassword(String userId, String password) throws BaseServiceException {
        //    获取原密码
        String daoOldPassword = userMapper.queryUserPasswordById(userId);
        //    将前端传来的密码进行编码
        String encodePassword = EncodeUtil.encodePassword(password, userId);
        //    数据比对结果
        if (!daoOldPassword.equals(encodePassword)) {
            throw new BaseServiceException(ServiceExceptionsEnum.PASSWORD_ERROR);
        }
    }


    @Override
    public Integer findRegisterUser(String phone, String role) {
        Integer count = userMapper.queryUserInRegister(phone, role);
        if (count == null) {
            count = 0;
        }
        return count;
    }

    @Override
    public void updateTelephone(String userId, String phoneNumber) {
        userMapper.updateTelephone(userId, phoneNumber);
    }
}
