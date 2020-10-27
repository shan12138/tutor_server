package com.dataee.tutorserver.tutorteacherserver.service;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.entity.Leisure;
import com.dataee.tutorserver.tutorteacherserver.bean.TeacherOtherInfoEndorsementRequestBean;
import com.dataee.tutorserver.tutorteacherserver.bean.TeacherOtherInfoLeisureTimeRequestBean;
import com.dataee.tutorserver.tutorteacherserver.bean.TeacherOtherInfoStudentCardRequestBean;

import java.util.List;

/**
 * 教员报名业务
 *
 * @author JinYue
 * @CreateDate 2019/5/10 11:44
 */
public interface ITeacherSignUpService {
    /**
     * 上传学生证信息
     *
     * @param studentCardBean
     * @param personId
     */
    void addStudentCardInfo(TeacherOtherInfoStudentCardRequestBean studentCardBean, Integer personId) throws BaseServiceException;

    /**
     * 上传头像
     *
     * @param resourceAddress
     * @param personId
     */
    void addHeadportrait(String resourceAddress, Integer personId) throws BaseServiceException;

    /**
     * 上传为我代言信息
     *
     * @param endorsementBean
     * @param personId
     */
    void addEndorsementInfo(TeacherOtherInfoEndorsementRequestBean endorsementBean, Integer personId) throws BaseServiceException;


    /**
     * 添加休闲时间
     *
     * @param lersureList
     * @param personId
     */
    void addLeisureTimeInfo(List<Leisure> lersureList, Integer personId) throws BaseServiceException;

    /**
     * 更新空余时间表
     *
     * @param leisures
     * @param personId
     * @throws BaseServiceException
     */
    void updateLeisureTimeInfo(List<Leisure> leisures, Integer personId) throws BaseServiceException;

}
