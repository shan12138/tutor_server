package com.dataee.tutorserver.tutoradminserver.filemanage.service;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.tutoradminserver.filemanage.bean.ErrorQuestionResponseBean;


/**
 * @author JinYue
 * @CreateDate 2019/5/14 21:59
 */
public interface IErrorQuestionsService {
    /**
     * 获取所有错题列表
     *
     * @return
     */
    NewPageInfo<ErrorQuestionResponseBean> getAllErrorQuestions(Page page,
                                                                String studentName,
                                                                String queryCondition,
                                                                Integer lessonNumber,
                                                                String essentialContent);

    /**
     * 根据指定的编号获取错题
     *
     * @param id
     * @return
     */
    ErrorQuestionResponseBean getErrorQuestion(int id) throws BaseServiceException;

    /**
     * 标记打印状态
     *
     * @param isPrint
     */
    void printed(int id, boolean isPrint);

    /**
     * 标记排课状态
     *
     * @param isCourse
     */
    void coursed(int id, boolean isCourse);

    /**
     * 下载指定的错题包
     *
     * @param errorquestionId
     * @return
     */
    String downloadErrorQuestionPackage(Integer errorquestionId) throws BaseServiceException;
}
