package com.dataee.tutorserver.tutorteacherserver.service;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.entity.Course;
import com.dataee.tutorserver.entity.Lesson;
import com.dataee.tutorserver.tutorpatriarchserver.bean.Remarks;
import com.dataee.tutorserver.tutorteacherserver.bean.ScheduleBean;

import java.text.ParseException;
import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/15 19:18
 */
public interface ITeacherCourseService {
    Course getAttendanceRecord(Integer courseId, Integer teacherId);

    void checkIn(Integer lessonId, Integer teacherId) throws BaseServiceException, ParseException;

    void checkOut(Integer personId, Integer lessonId,Integer courseId) throws BaseServiceException, ParseException;

    List<ScheduleBean> getSchedule(Integer personId, Integer week, String year);

    List<Remarks> getParentRecord(Integer lessonId);

    NewPageInfo getAttendanceRecordPageInfo(Integer courseId, Integer personId, Page page);

    Lesson getLessonById(Integer id);


}
