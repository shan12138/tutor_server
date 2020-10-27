package com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.service;

import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.entity.*;
import com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.bean.CancelAuditionClassBean;
import com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.bean.CreateFlowTypeBean;
import com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.bean.TeacherConsumeRequestBean;

import java.util.List;

public interface ICreateFlowService {

    void  createFlow(CreateFlowTypeBean createFlowTypeBean) throws BaseControllerException;

    NewPageInfo<WorkFlow> getAllFlow(String state,Integer adminId, Page page);

    Administrator getAdminById(Integer id);

    List<Administrator> getAdminByRole(Integer roleId);

    WorkFlow getWorkFlowByWorkFlowId(Integer workFlowId);

    void  cancelAudition(CancelAuditionClassBean cancelAuditionClassBean) throws BaseControllerException;

    void changeTeacher(CancelAuditionClassBean cancelAuditionClassBean) throws BaseControllerException, BaseServiceException;

    String  getTeacherConsumeUrl(Integer nodeId) throws BaseServiceException;

}
