package com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.service;

import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.entity.Appointment;
import com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.bean.BindAuditionClassBean;
import com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.bean.NodeBean;


public interface ICreateAppointmentService {
    void  appointAuditionClass(BindAuditionClassBean bindAuditionClassBean) throws BaseControllerException;

    NewPageInfo<NodeBean> appointList(Integer workFlowId, Page page);

    void trainDepartmentAccept(Integer workFlowId,Integer nodeId) throws BaseControllerException;

    void trainDepartmentRefuse(Integer workFlowId, Integer nodeId) throws BaseControllerException;


}
