package com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.bean;

import com.dataee.tutorserver.entity.*;
import com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.dao.CreateFormMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NodeBean {
    private Integer  nodeId;
    private WorkFlow workFlow;
    private Integer  sequenceNum;
    //操作者
    private Administrator operator;
    private Administrator submitter;
    private Date operationTime;
    private Date           createTime;
    private Integer stepNum;
    private Integer        type;


    private RegisterForm registerForm;
    private AccompanyRegisterForm accompanyRegisterForm;
    private HeadMasterExamine headMasterExamine;
    private MatchTeacherForm matchTeacherForm;
    private TrainForm trainForm;
    private AuditionFeedbackForm auditionFeedbackForm;
    private Appointment appointment;
    private Operation operation;
}
