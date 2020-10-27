package com.dataee.tutorserver.tutorteacherserver.service;

import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.entity.Teacher;
import com.dataee.tutorserver.entity.TeacherInvitation;
import com.dataee.tutorserver.tutorteacherserver.bean.TeacherInvite;
import com.dataee.tutorserver.tutorteacherserver.bean.TeacherInviteCount;

public interface ITeacherInviteService {
    Teacher getTeacherByTeacherId(Integer teacherId);

    void  insertTeacherInvite(TeacherInvitation teacherInvitation) throws BaseServiceException, BaseControllerException;

    NewPageInfo<TeacherInvite> getTeachersById(Integer teacherId, Page page);

    TeacherInviteCount getInvitedCount(Integer teacherId);

    void teacherInviteTeacher(TeacherInvitation teacherInvitation,Integer personId) throws BaseServiceException;

}
