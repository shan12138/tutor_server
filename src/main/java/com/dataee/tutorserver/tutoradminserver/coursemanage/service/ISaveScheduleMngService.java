package com.dataee.tutorserver.tutoradminserver.coursemanage.service;


import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.exceptions.IllegalParameterException;
import com.dataee.tutorserver.commons.exceptions.SQLOperationException;
import com.dataee.tutorserver.entity.Leisure;
import com.dataee.tutorserver.tutoradminserver.coursemanage.bean.LessonBean;
import com.dataee.tutorserver.tutoradminserver.coursemanage.bean.SaveLessonRequestBean;
import com.dataee.tutorserver.tutoradminserver.coursemanage.bean.SaveLessonsRequestBean;
import com.dataee.tutorserver.tutoradminserver.coursemanage.bean.SaveRemarksResponseBean;

import java.util.List;

/**
 * 保存排课信息
 *
 * @author JinYue
 * @CreateDate 2019/5/24 17:45
 */
public interface ISaveScheduleMngService {
    /**
     * 更新本周排课(批量)
     *
     * @param lessons
     */
    void addLessonsByWeek(SaveLessonsRequestBean lessons) throws BaseServiceException;


    /**
     * 更新本周排课
     *
     * @param lesson
     * @throws BaseServiceException
     */
    void addLessonByWeek(SaveLessonRequestBean lesson) throws BaseServiceException, IllegalParameterException;

    /**
     * 课程查重
     *
     * @param lessons
     * @return
     */
    List<Leisure> findRepeatLessons(SaveLessonsRequestBean lessons);

    /**
     * 删除指定的lessons
     *
     * @param lessons
     * @param weekLessonId
     * @throws Exception
     */
    void deleteLessons(List<LessonBean> lessons, Integer weekLessonId) throws Exception;


    /**
     * 保存指定周的备注
     *
     * @param remarks
     */
    void saveRemark(SaveRemarksResponseBean remarks) throws SQLOperationException;
}
