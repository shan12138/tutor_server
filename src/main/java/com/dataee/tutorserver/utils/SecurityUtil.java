package com.dataee.tutorserver.utils;

import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.bean.UserPrincipals;
import com.dataee.tutorserver.commons.exceptions.NullUserInformationException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取当前的用户身份
 *
 * @author JinYue
 */
public class SecurityUtil {
    private static final Logger logger = LoggerFactory.getLogger(SecurityUtil.class);

    /**
     * 该功能能够获取到当前的会话连接
     * subject不仅代表用户还可以代表作业，任务等连接
     *
     * @return
     */
    public static Subject currentSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * 从Shiro中的缓存中获取到用户信息
     *
     * @return
     */
    public static UserPrincipals getPrincipal() throws BaseControllerException {
        Subject currentUser = SecurityUtils.getSubject();
        UserPrincipals userPrincipals = (UserPrincipals) currentUser.getPrincipal();
        if (userPrincipals == null) {
            throw new NullUserInformationException();
        }
        return userPrincipals;
    }

    /**
     * 获取到用户的Id
     *
     * @return
     */
    public static String getUserId() throws BaseControllerException {
        try {
            UserPrincipals userPrincipals = getPrincipal();
            return userPrincipals.getUserId();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new NullUserInformationException();
        }
    }

    /**
     * 获取用户表的主键Id
     *
     * @return
     * @throws BaseControllerException
     */
    public static int getId() throws BaseControllerException {
        try {
            UserPrincipals userPrincipals = getPrincipal();
            return userPrincipals.getId();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new NullUserInformationException();
        }
    }

    /**
     * 获取用户当前登录的身份ID
     *
     * @return
     * @throws BaseControllerException
     */
    public static Integer getPersonId() throws BaseControllerException {
        try {
            UserPrincipals userPrincipals = getPrincipal();
            return userPrincipals.getPersonId();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new NullUserInformationException();
        }
    }
}
