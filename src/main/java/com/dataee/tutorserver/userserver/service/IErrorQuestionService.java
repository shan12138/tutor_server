package com.dataee.tutorserver.userserver.service;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.commons.exceptions.SQLOperationException;
import com.dataee.tutorserver.entity.Course;
import com.dataee.tutorserver.entity.ErrorQuestion;
import com.dataee.tutorserver.userserver.bean.CourseResponseBean;
import com.dataee.tutorserver.userserver.bean.ErrorQuestionRequestBean;
import com.dataee.tutorserver.userserver.bean.LessonNumberResponseBean;

import java.util.List;

/**
 * @author JinYue
 * @CreateDate 2019/5/7 0:41
 */
public interface IErrorQuestionService {
    /**
     * 保存错题
     *
     * @param errorQuestion
     * @param role
     */
    void saveErrorQuestions(ErrorQuestionRequestBean errorQuestion, Integer personId, String role) throws SQLOperationException;

    /**
     * 显示错题集
     *
     * @param personId
     * @param role
     * @return
     */
    NewPageInfo<ErrorQuestion> getErrorQuestionsByPersonId(Integer personId, String role, Page page) throws BaseServiceException;

    /**
     * 获取指定错题信息
     *
     * @param errorQuestionId
     * @return
     */
    ErrorQuestion getErrorQuestionByQuestionId(int errorQuestionId);

    /**
     * 获取该用户课程列表
     *
     * @param personId
     * @param role
     * @return
     */
    List<CourseResponseBean> getCourseName(int personId, String role);


    /**
     * 获取指定课程的课次列表
     *
     * @param courseId
     * @return
     */
    List<LessonNumberResponseBean> getLessonNumber(int courseId);
}
