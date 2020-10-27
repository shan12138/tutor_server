package com.dataee.tutorserver.userserver.service;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.UserPrincipals;
import com.dataee.tutorserver.entity.User;
import com.dataee.tutorserver.userserver.bean.LoginResponseBean;

import java.util.List;

public interface IUserService {
    /**
     * 通过用户名查找用户
     *
     * @param username
     * @param role
     * @return
     */
    User findByName(String username, String role);

    /**
     * 用户注册
     *
     * @param userId   用户ID
     * @param username 用户名
     * @param password 用户加密后的密码
     * @param role     用户角色
     */
    void registerUser(String userId, String username, String password, String role) throws BaseServiceException;

    /**
     * 修改用户密码
     *
     * @param password
     * @param id       用户表id （key）
     */
    void updatePassword(int id, String password) throws BaseServiceException;

    /**
     * 获取用户当前登录系统的身份ID
     *
     * @param userId
     * @param role
     * @return
     * @throws BaseServiceException
     */
    Integer getPersonId(String userId, String role) throws BaseServiceException;

    /**
     * 获取用户口令
     *
     * @param userId
     * @return
     */
    String findSecretByUserId(String userId);

    /**
     * 获取用户凭证
     *
     * @param username
     * @param role
     * @return
     */
    UserPrincipals getPrincipals(String username, String role);

    List<String> getHomePictures();

    /**
     * 根据用户ID查找用户的角色ID
     *
     * @param userId
     * @return
     */
    Integer getPersonRoleId(String userId);

    /**
     * 密码校验
     *
     * @param userId
     * @param password
     * @return
     */
    void verifyPassword(String userId, String password) throws BaseServiceException;


    /**
     * @param phone
     * @param role
     * @return
     */
    Integer findRegisterUser(String phone, String role);

    void updateTelephone(String userId, String phoneNumber);
}
