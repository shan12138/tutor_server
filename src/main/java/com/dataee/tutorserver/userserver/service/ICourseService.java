package com.dataee.tutorserver.userserver.service;

import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.entity.Grade;
import com.dataee.tutorserver.entity.Parent;
import com.dataee.tutorserver.entity.Subject;
import com.dataee.tutorserver.userserver.bean.GetCourseTeacherListResponseBean;
import com.dataee.tutorserver.userserver.bean.GetTodayCourseResponseBean;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/4/27 11:29
 */
public interface ICourseService {
    List<Grade> getGrade();

    List<Subject> getSubject();

    NewPageInfo<GetCourseTeacherListResponseBean> getCourseList(String tableId, Integer id, Page page);



    List<GetTodayCourseResponseBean> getTeacherTodayCourse(String userId);

    List<GetTodayCourseResponseBean> getParentTodayCourse(String userId);

    List<Parent> getParentList(int state);
}
