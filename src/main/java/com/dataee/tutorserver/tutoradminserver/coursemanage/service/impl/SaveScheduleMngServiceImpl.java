package com.dataee.tutorserver.tutoradminserver.coursemanage.service.impl;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.TimeBean;
import com.dataee.tutorserver.commons.errorInfoenum.ServiceExceptionsEnum;
import com.dataee.tutorserver.commons.exceptions.IllegalParameterException;
import com.dataee.tutorserver.commons.exceptions.SQLOperationException;
import com.dataee.tutorserver.entity.Leisure;
import com.dataee.tutorserver.tutoradminserver.coursemanage.bean.LessonBean;
import com.dataee.tutorserver.tutoradminserver.coursemanage.bean.SaveLessonRequestBean;
import com.dataee.tutorserver.tutoradminserver.coursemanage.bean.SaveLessonsRequestBean;
import com.dataee.tutorserver.tutoradminserver.coursemanage.bean.SaveRemarksResponseBean;
import com.dataee.tutorserver.tutoradminserver.coursemanage.dao.SaveScheduleMngMapper;
import com.dataee.tutorserver.tutoradminserver.coursemanage.service.ISaveScheduleMngService;
import com.dataee.tutorserver.utils.TimeWeekUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author JinYue
 * @CreateDate 2019/5/24 17:46
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SaveScheduleMngServiceImpl implements ISaveScheduleMngService {
    private final Logger logger = LoggerFactory.getLogger(SaveScheduleMngServiceImpl.class);

    @Autowired
    private SaveScheduleMngMapper saveScheduleMngMapper;


    @Override
    public void addLessonsByWeek(SaveLessonsRequestBean lessons) throws BaseServiceException {
        //分离出删除的课程和新增的课程
        List<LessonBean> newLessons = lessons.getLessons();
        List<LessonBean> deleteLessons = new ArrayList<>();
        List<LessonBean> addLessons = new ArrayList<>();
        //数据分离
        if (newLessons != null && newLessons.size() != 0) {
            newLessons.forEach(lessonBean -> {
                if (lessonBean.getState() == 0) {
                    deleteLessons.add(lessonBean);
                } else {
                    addLessons.add(lessonBean);
                }
            });
        }
        //初始化数据
        Integer week = TimeWeekUtil.getWeekOfYear(lessons.getStartDate());
        Integer year = lessons.getYear();
        Integer courseId = lessons.getCourseId();
        Integer teacherId = lessons.getTeacherId();
        //判断当前周课是否存在
        Integer weekLessonId = saveScheduleMngMapper.queryWeekBYWeek(week, courseId, teacherId, year);
        //更新备注或插入
        if (weekLessonId == null) {
            saveScheduleMngMapper.addWeekLesson(lessons, week);
            weekLessonId = lessons.getId();
        }
        try {
            //删除课程(批量)
            if (deleteLessons.size() != 0) {
                deleteLessons(deleteLessons, weekLessonId);
            }
            //创建时间转换工具
            if (addLessons.size() != 0) {
                for (LessonBean lesson : addLessons) {
                    TimeBean courseTime = TimeWeekUtil.dayToDate(year, week, lesson.getDay(), lesson.getTime());
                    lesson.setCourseTime(courseTime);
                }
                //批量新增课程
                addLessons(addLessons, weekLessonId, courseId, teacherId);
            }
        } catch (Exception e) {
            throw new BaseServiceException(ServiceExceptionsEnum.FAIL_SAVE_LESSONS);
        }
    }


    @Override
    public void addLessonByWeek(SaveLessonRequestBean lesson) throws BaseServiceException, IllegalParameterException {
        //处理周课
        //初始化数据
        Integer week = TimeWeekUtil.getWeekOfYear(lesson.getStartDate());
        //判断当前周课是否存在
        Integer weekLessonId = saveScheduleMngMapper.queryWeekBYWeek(week, lesson.getCourseId(), lesson.getTeacherId(), lesson.getYear());
        if (weekLessonId == null) {
            saveScheduleMngMapper.addTheWeekLesson(lesson, week);
            weekLessonId = lesson.getId();
        }
        LessonBean newLesson = lesson.getLesson();
        //    判断当前是删除还是增加
        if (lesson.getLesson().getState() == 0) {
            //    获取lessonId
            Integer lessonId = saveScheduleMngMapper.queryLessonId(weekLessonId, newLesson.getDay(), newLesson.getTime());
            //    获取当前课程的课次
            Integer lessonNumber = saveScheduleMngMapper.queryLessonNumberByLessonId(lessonId);
            //    删除课程
            saveScheduleMngMapper.deleteLessonsById(lessonId);
            //    更新课次
            saveScheduleMngMapper.minusLessonNumber(lesson.getCourseId(), lessonNumber);
        } else {
            //    获取当前最大的课次
            TimeBean courseTime = TimeWeekUtil.dayToDate(lesson.getYear(), week, newLesson.getDay(), newLesson.getTime());
            //获取上课时间
            newLesson.setCourseTime(courseTime);
            if (courseTime == null) {
                throw new IllegalParameterException("未获取到上课时间");
            }
            Integer lessonNumber = saveScheduleMngMapper.queryMaxLessonNumber(lesson.getCourseId(), courseTime.getStartTime(), courseTime.getEndTime());
            if (lessonNumber == null) {
                lessonNumber = 0;
            }
            //    增加课程
            saveScheduleMngMapper.insertLesson(newLesson, weekLessonId, lesson.getCourseId(), lesson.getTeacherId(), lessonNumber + 1);
            //    修改课次
            saveScheduleMngMapper.plusLessonNumber(lesson.getCourseId(), lessonNumber + 1, newLesson.getId());
        }

    }

    /**
     * 删除课程
     *
     * @param lessons
     * @param weekLessonId
     */
    @Override
    public void deleteLessons(List<LessonBean> lessons, Integer weekLessonId) throws Exception {
        int status = 0;
        Integer lessonId;
        for (LessonBean lesson : lessons) {
            lessonId = saveScheduleMngMapper.queryLessonId(weekLessonId, lesson.getDay(), lesson.getTime());
            if (lessonId == null) {
                throw new Exception("该课程不存在");
            } else {
                status = saveScheduleMngMapper.deleteLessonsById(lessonId);
                if (status == 0) {
                    throw new Exception("取消排课失败");
                } else {
                    lessonId = null;
                    status = 0;
                }
            }
        }
    }


    /**
     * 添加课程
     *
     * @param lessons
     * @param weekLessonId
     * @param courseId
     * @param teacherId
     */
    private void addLessons(List<LessonBean> lessons, Integer weekLessonId, Integer courseId, Integer teacherId) throws Exception {
        int len = lessons.size();
        int count = saveScheduleMngMapper.addLessons(lessons, weekLessonId, courseId, teacherId);
        if (count != len) {
            throw new Exception("添加课程失败");
        }
    }


    @Override
    public List<Leisure> findRepeatLessons(SaveLessonsRequestBean lessons) {
        return null;
    }


    @Override
    public void saveRemark(SaveRemarksResponseBean remarks) throws SQLOperationException {
        Integer weekOfYear = TimeWeekUtil.getWeekOfYear(remarks.getStartDate());
        Integer weekLessonId = saveScheduleMngMapper.queryWeekBYWeek(weekOfYear, remarks.getCourseId(),
                remarks.getTeacherId(), remarks.getYear());
        //插入或者更新本周的备注

        int count = 0;
        if (weekLessonId == null) {
            count = saveScheduleMngMapper.addWeekLessonByRemark(remarks, weekOfYear);
        } else {
            count = saveScheduleMngMapper.updateWeekLessonById(remarks.getRemarks(), weekLessonId);
        }
        if (count == 0) {
            throw new SQLOperationException();
        }
    }

}
