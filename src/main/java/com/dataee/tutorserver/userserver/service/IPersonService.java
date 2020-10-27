package com.dataee.tutorserver.userserver.service;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.userserver.bean.UserBean;

/**
 * 基础用户业务
 *
 * @author JinYue
 * @CreateDate 2019/5/14 1:49
 */
public interface IPersonService {
    /**
     * 获取人物的当前状态
     *
     * @param personId
     * @param role
     * @return
     */
    Integer getPersonState(Integer personId, String role) throws BaseServiceException;

    /**
     * 根据当前的personId和state判断当前这个人是否存在
     *
     * @param personId
     * @param role
     * @return
     */
    Boolean findPerson(Integer personId, String role);

    /**
     * 根据personId获取person的name
     *
     * @param tableName
     * @param personId
     * @return
     */
    String getPersonName(String tableName, Integer personId);

    UserBean getUser(Integer personId, String role);
}
