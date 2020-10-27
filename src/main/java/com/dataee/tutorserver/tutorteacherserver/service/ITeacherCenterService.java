package com.dataee.tutorserver.tutorteacherserver.service;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.commons.exceptions.SQLOperationException;
import com.dataee.tutorserver.entity.Question;
import com.dataee.tutorserver.entity.Teacher;
import com.dataee.tutorserver.entity.TeacherLabel;
import com.dataee.tutorserver.tutorteacherserver.bean.*;

import java.util.List;

public interface ITeacherCenterService {
    List<Question> getPaper(String grade, String subject);

    void changeTeaInfo(TeacherDetailInfoRequestBean teacherDetailInfoRequestBean) throws SQLOperationException;

    Teacher getTeacherInfo(int teacherId);

    NewPageInfo<Teacher> queryTeacher(Page page);

    String getHeadPicture(Integer personId);

    void updateTeacherState(Integer teacherId) throws SQLOperationException;

    void saveCourseScore(ScoreBean score);

    void saveTeachingArea(ScoreBean score);

    Integer getTeachingAreaId(ScoreBean score);

    NewPageInfo getScoreHistory(Integer teacherId, Page page);

    void writeFirstInformation(TeacherBasicInfoRequestBean teacherBasicInfoRequestBean);

    void writeSecondInformation(TeacherBasicInfoRequestBean teacherBasicInfoRequestBean);

    Teacher getFirstInformation(Integer teacherId);

    Teacher getSecondInformation(Integer teacherId);

    Teacher getThirdInformation(Integer teacherId);

    TeacherLabel getTeacherLabel(Integer teacherId);

    BankAccountResponseBean getBankAccountInfo(Integer teacherId) throws BaseServiceException;

    StudentCardResponseBean getStudentCardPicture(Integer teacherId) throws BaseServiceException;


}
