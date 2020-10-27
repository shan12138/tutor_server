package com.dataee.tutorserver.tutoradminserver.teachermanage.service;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.entity.Teacher;
import com.dataee.tutorserver.tutoradminserver.messagemanage.bean.InfoChangeVerifyRequestBean;
import com.dataee.tutorserver.tutoradminserver.patriarchmanage.bean.ClassAndHour;
import com.dataee.tutorserver.tutoradminserver.teachermanage.bean.PlatformInfoChangeRequestBean;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/10 23:29
 */
public interface ITeacherManageService {
    NewPageInfo getTeacherAuthEdList(Page page);

    NewPageInfo getTeacherAuthIngList(Page page,String queryCondition, String state,String sex);

    Teacher getTeacherDetail(String id);

//    void acceptTeacherBasicInfo(InfoChangeVerifyRequestBean infoChangeVerifyRequestBean) throws BaseServiceException;

//    void denyTeacherBasicInfo(InfoChangeVerifyRequestBean infoChangeVerifyRequestBean) throws BaseServiceException;

    void acceptTeacherNextInfo(InfoChangeVerifyRequestBean infoChangeVerifyRequestBean) throws BaseServiceException;

    void denyTeacherNextInfo(InfoChangeVerifyRequestBean infoChangeVerifyRequestBean) throws BaseServiceException;

    NewPageInfo queryTeacher(String queryCondition, String state, String sex,Integer start,Integer end, Page page);

    String getTeacherIdOfCourse(String teacherId);

    void changePlatformInfo(PlatformInfoChangeRequestBean platformChangeInfo);

    void changePlatformIntroduce(String platformIntroduce, Integer teacherId);

}
