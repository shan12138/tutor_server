package com.dataee.tutorserver.tutoradminserver.coursemanage.service;

import com.dataee.tutorserver.commons.exceptions.IllegalParameterException;
import com.dataee.tutorserver.commons.exceptions.SQLOperationException;

import java.util.List;

/**
 * @author JinYue
 * @CreateDate 2019/5/27 22:32
 */
public interface ILessonsService {
    /**
     * 获取本周之后的weekLessons的Id列表
     *
     * @param week
     * @param courseId
     * @param teacherId
     */
    List<Integer> getWeekLessonIds(Integer week, Integer courseId, Integer teacherId, Integer year);

    /**
     * 获取本周的weekLessonsId
     *
     * @param week
     * @param courseId
     * @param teacherId
     * @param year
     * @return
     */
    Integer getWeekOfYearId(Integer week, Integer courseId, Integer teacherId, Integer year);

    /**
     * 删除指定周的lessons
     *
     * @param weekLessonIdList
     */
    void deleteLessons(List<Integer> weekLessonIdList) throws SQLOperationException;


    /**
     * 删除某一周指定日期之后的课程
     *
     * @param weekLessonId
     * @param dayOfWeek
     * @throws SQLOperationException
     */
    void deleteLessons(Integer weekLessonId, Integer dayOfWeek) throws SQLOperationException;


    /**
     * 删除指定课程本周及本周以后的周课记录
     *
     * @param weekLessonsIdList
     */
    void deleteWeekLessons(List<Integer> weekLessonsIdList) throws SQLOperationException;


    /**
     * 清空排课
     *
     * @param teacherId
     * @param courseId
     */
    void cleanAllLessonsByWeek(Integer teacherId, Integer courseId) throws SQLOperationException, IllegalParameterException;
}
