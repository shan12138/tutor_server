package com.dataee.tutorserver.tutoradminserver.filemanage.service;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.exceptions.IllegalParameterException;
import com.dataee.tutorserver.tutoradminserver.filemanage.bean.ParentContractRequestBean;
import com.dataee.tutorserver.tutoradminserver.filemanage.bean.TeacherContractRequestBean;

/**
 * 管理家长合同的上传和学生合同的上传
 *
 * @author JinYue
 * @CreateDate 2019/6/29 10:59
 */
public interface ISaveContractManageService {
    /**
     * 保存教员的合同
     *
     * @param teacherContract
     */
    void saveTeacherContract(TeacherContractRequestBean teacherContract);

    /**
     * 保存家长的合同
     *
     * @param parentContract
     */
    void saveParentContract(ParentContractRequestBean parentContract);
}
