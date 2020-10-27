package com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.service;

import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.entity.*;
import com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.bean.SaveReviewForm;
import com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.bean.SubmitFlowBean;

public interface ICreateFormService {

    void saveRegisterForm(RegisterForm registerForm) throws BaseServiceException, BaseControllerException;

    void saveAccompanyRegisterForm(AccompanyRegisterForm accompanyRegisterForm) throws BaseServiceException, BaseControllerException;

    void saveReviewForm(SaveReviewForm saveReviewForm) throws BaseServiceException, BaseControllerException;

    void saveMatchTeacherForm(MatchTeacherForm matchTeacherForm)throws BaseServiceException, BaseControllerException;

    void saveTrainForm(TrainForm trainForm)throws BaseServiceException, BaseControllerException;

    void saveAuditionFeedbackForm(AuditionFeedbackForm auditionFeedbackForm)throws BaseServiceException, BaseControllerException;

    void submitRegisterForm(SubmitFlowBean submitFlowBean) throws BaseServiceException, BaseControllerException;

    void submitReviewForm(SubmitFlowBean submitFlowBean) throws BaseServiceException, BaseControllerException;

    void submitMatchTeacherForm(SubmitFlowBean submitFlowBean)throws BaseServiceException, BaseControllerException;

    void submitTrainForm(SubmitFlowBean submitFlowBean)throws BaseServiceException, BaseControllerException;

    void submitAuditionFeedbackForm(SubmitFlowBean submitFlowBean)throws BaseServiceException, BaseControllerException;

    void submitRefuseReviewForm(SubmitFlowBean submitFlowBean) throws BaseControllerException;
}
