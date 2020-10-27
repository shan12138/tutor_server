package com.dataee.tutorserver.tutoradminserver.patriarchmanage.service;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.entity.Parent;
import com.dataee.tutorserver.entity.ParentLevel;
import com.dataee.tutorserver.entity.Partner;
import com.dataee.tutorserver.entity.Student;
import com.dataee.tutorserver.tutoradminserver.messagemanage.bean.InfoChangeVerifyRequestBean;
import com.dataee.tutorserver.tutoradminserver.patriarchmanage.bean.ClassAndHour;
import com.dataee.tutorserver.tutoradminserver.patriarchmanage.bean.UpdateInvitation;
import com.dataee.tutorserver.userserver.bean.GetCourseTeacherListResponseBean;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/10 15:22
 */
public interface IParentManageService {
    NewPageInfo getParentList(Integer state,Page page);

    Parent getParentDetail(String parentId);

    void acceptParentDetail(InfoChangeVerifyRequestBean infoChangeVerifyRequestBean) throws BaseServiceException;

    void denyParentDetail(InfoChangeVerifyRequestBean infoChangeVerifyRequestBean) throws BaseServiceException;

    NewPageInfo queryParent(String queryCondition, String state, String sex, Page page);

    List<ClassAndHour> getClassAndHourOfParent(String parentId);

    NewPageInfo<Parent> getAuthEdParentList(Page page,String studentName,String sex);

    List<Student> getOwnChildren(String id);

    List<GetCourseTeacherListResponseBean> getCourseList(String s, String id);

    void changeInvitation(UpdateInvitation updateInvitation);

    List<ParentLevel> getParentLevelList();

    List<Partner> getPartnerList();
}
