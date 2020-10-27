package com.dataee.tutorserver.tutoradminserver.coursemanage.service.impl;

import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.tutoradminserver.coursemanage.bean.CourseLesson;
import com.dataee.tutorserver.tutoradminserver.coursemanage.bean.CurrentCourseResponseBean;
import com.dataee.tutorserver.tutoradminserver.coursemanage.bean.Schedule;
import com.dataee.tutorserver.tutoradminserver.coursemanage.bean.ScheduleResponseBean;
import com.dataee.tutorserver.tutoradminserver.coursemanage.dao.QueryScheduleMngMapper;
import com.dataee.tutorserver.tutoradminserver.coursemanage.service.IQueryScheduleMngService;
import com.dataee.tutorserver.utils.PageInfoUtil;
import com.dataee.tutorserver.utils.TimeWeekUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/24 22:16
 */
@Service
public class QueryScheduleMngServiceImpl implements IQueryScheduleMngService {
    @Autowired
    private QueryScheduleMngMapper queryScheduleMngMapper;

    @Override
    public ScheduleResponseBean getLessons(Integer teacherId, Integer week, Integer courseId) {
        List<Schedule> otherLessons = queryScheduleMngMapper.getOtherLessons(teacherId, week, courseId);
        List<Schedule> thisLessons = queryScheduleMngMapper.getThisLessons(teacherId, week, courseId);
        List<Schedule> lessons = new ArrayList<>();
        lessons.addAll(otherLessons);
        lessons.addAll(thisLessons);
        ScheduleResponseBean scheduleResponseBean = new ScheduleResponseBean();
        scheduleResponseBean.setScheduleList(lessons);
        scheduleResponseBean.setRemarks(queryScheduleMngMapper.getRemarks(week, courseId, teacherId));
        scheduleResponseBean.setWeek(week);
        return scheduleResponseBean;
    }

    @Override
    public NewPageInfo<CourseLesson> getLessonsByCourseId(Integer courseId,String teacherName,String status,String startTime,String endTime, Page page) {
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<CourseLesson> lessonsByCourse = queryScheduleMngMapper.getLessonsByCourseId(courseId, teacherName, status, startTime, endTime);
        NewPageInfo<CourseLesson> newPageInfo = PageInfoUtil.read(new PageInfo(lessonsByCourse));
        return newPageInfo;
    }

}
