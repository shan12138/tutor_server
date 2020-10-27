package com.dataee.tutorserver.tutoradminserver.statemanage.service;

import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/10 18:59
 */
public interface IStateManageService {
    void changeDataState(String table, Integer id) throws BaseServiceException, BaseControllerException;
}
