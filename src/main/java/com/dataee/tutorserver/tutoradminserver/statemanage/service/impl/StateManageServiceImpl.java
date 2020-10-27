package com.dataee.tutorserver.tutoradminserver.statemanage.service.impl;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.errorInfoenum.ServiceExceptionsEnum;
import com.dataee.tutorserver.tutoradminserver.statemanage.dao.StateManageMapper;
import com.dataee.tutorserver.tutoradminserver.statemanage.service.IStateManageService;
import com.dataee.tutorserver.tutoradminserver.subjectandgrademanage.dao.SubjectAndGradeManageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/10 19:00
 */
@Service
public class StateManageServiceImpl implements IStateManageService {
    @Autowired
    private StateManageMapper stateManageMapper;
    @Autowired
    private SubjectAndGradeManageMapper subjectAndGradeManageMapper;

    /**
     * 修改表的状态
     * teacher表中state为4代表正式家教
     * parent中3代表正式家长
     * 其他表中1代表有效状态
     * 0均代表无效状态
     *
     * @param table
     * @param id
     * @throws BaseServiceException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeDataState(String table, Integer id) throws BaseServiceException {
        int state;
        if (table.equals("parent")) {
            state = stateManageMapper.getDataState(table, "parent_id", id);
            if (state != 3 && state != 0) {
                throw new BaseServiceException(ServiceExceptionsEnum.CANT_CHANGE_STATE);
            }
//            int userAuthId = stateManageMapper.getUserAuthId(id, "parent_id", "parent");
//            stateManageMapper.changeUserAuthState(userAuthId, state == 3 ? 0 : 1);
            stateManageMapper.changeDataState(table, state == 3 ? 0 : 3, "parent_id", id);
            stateManageMapper.changeUserState(stateManageMapper.getUserId("parent", "parent_id", id), state == 3 ? 0 : 1);
        } else if (table.equals("teacher")) {
            state = stateManageMapper.getDataState(table, "teacher_id", id);
            if (state != 4 && state != 0) {
                throw new BaseServiceException(ServiceExceptionsEnum.CANT_CHANGE_STATE);
            }
//            int userAuthId = stateManageMapper.getUserAuthId(id, "teacher_id", "teacher");
//            stateManageMapper.changeUserAuthState(userAuthId, state == 4 ? 0 : 1);
            stateManageMapper.changeDataState(table, state == 4 ? 0 : 4, "teacher_id", id);
            stateManageMapper.changeUserState(stateManageMapper.getUserId("teacher", "teacher_id", id), state == 4 ? 0 : 1);
        } else {
            String oldName = subjectAndGradeManageMapper.getOldNameById(table, id);
            Integer count = subjectAndGradeManageMapper.getCourseByObject(oldName);
            if (count != 0) {
                throw new BaseServiceException(ServiceExceptionsEnum.CANT_CHANGE_USING);
            }
            state = stateManageMapper.getDataState(table, "id", id);
            stateManageMapper.changeDataState(table, state == 1 ? 0 : 1, "id", id);
        }
    }

}
