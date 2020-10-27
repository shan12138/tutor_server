package com.dataee.tutorserver.tutoradminserver.subjectandgrademanage.service;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/7 23:08
 */
public interface ISubjectAndGradeManageService {
    void changeData(String table, Integer id, Integer priority) throws BaseServiceException;

    void addData(String table, String name, Integer priority) throws BaseServiceException;

    NewPageInfo getAllSubject(Page page);

    NewPageInfo getAllGrade(Page page);
}
