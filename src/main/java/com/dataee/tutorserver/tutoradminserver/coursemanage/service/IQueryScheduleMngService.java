package com.dataee.tutorserver.tutoradminserver.coursemanage.service;

import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.tutoradminserver.coursemanage.bean.CourseLesson;
import com.dataee.tutorserver.tutoradminserver.coursemanage.bean.ScheduleResponseBean;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/24 22:15
 */
public interface IQueryScheduleMngService {
    ScheduleResponseBean getLessons(Integer teacherId, Integer week, Integer courseId);

    NewPageInfo<CourseLesson> getLessonsByCourseId(Integer courseId,String teacherName,String status,String startTime,String endTime, Page page);
}
