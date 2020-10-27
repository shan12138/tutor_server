package com.dataee.tutorserver.tutorpatriarchserver.service;

import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.entity.Bill;
import com.dataee.tutorserver.entity.Course;
import com.dataee.tutorserver.entity.Lesson;
import com.dataee.tutorserver.tutorpatriarchserver.bean.ClassHourDetail;
import com.dataee.tutorserver.tutorteacherserver.bean.ScheduleBean;
import com.dataee.tutorserver.userserver.bean.GetCourseTeacherListResponseBean;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/6 15:41
 */
public interface IParentCourseService {
    Course getCourseDetailInfo(String courseId);

    List<ScheduleBean> getSchedule(Integer personId, Integer week, String year);

    NewPageInfo getLessonDetailInfo(String courseId, Page page);

    void changeCourseAddress(Integer addressId, Integer courseId);

    Lesson getLessonById(Integer id);

    List<GetCourseTeacherListResponseBean> getCourseList (String tableId, Integer id);

    List<Lesson>  getLessonsByCourseId(Integer courseId);

    NewPageInfo<Bill> getParentConsume(Integer courseId, Page page);
}
