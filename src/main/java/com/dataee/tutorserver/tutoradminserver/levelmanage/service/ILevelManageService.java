package com.dataee.tutorserver.tutoradminserver.levelmanage.service;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.entity.ParentLevel;
import com.dataee.tutorserver.entity.TeacherLevel;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/5 11:23
 */
public interface ILevelManageService {
    NewPageInfo getParentLevelList(Page page, int id) throws BaseServiceException;

    void createParentLevel(int id, ParentLevel parentLevel) throws BaseServiceException;

    void updateParentLevel(ParentLevel parentLevel) throws BaseServiceException;

    void deleteParentLevel(int id) throws BaseServiceException;

    NewPageInfo getTeacherLevelList(Page page);

    void createTeacherLevel(TeacherLevel teacherLevel);

    void updateTeacherLevel(TeacherLevel teacherLevel) throws BaseServiceException;

    void deleteTeacherLevel(int id) throws BaseServiceException;
}
