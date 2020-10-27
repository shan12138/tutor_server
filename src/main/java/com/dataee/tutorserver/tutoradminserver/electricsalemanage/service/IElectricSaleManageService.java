package com.dataee.tutorserver.tutoradminserver.electricsalemanage.service;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.entity.IntentionCustomer;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/29 21:02
 */
public interface IElectricSaleManageService {
//    void completeElectricTask(IntentionInfoRequestBean intentionInfo) throws BaseServiceException;

    NewPageInfo getIntentionCustomer(Page page);

    IntentionCustomer getIntentionCustomerDetailInfo(Integer id);

    void changeCustomer(IntentionCustomer intentionCustomer) throws BaseServiceException;

//    List<ElectricSalesTask> getElectricTaskList(String complete, Integer electricSaleId);

    void createIntentionCustomer(IntentionCustomer intentionCustomer) throws BaseServiceException;

    void changeCustomerState(Integer id);

    NewPageInfo getCourseIntentionCustomer(Page page,String queryCondition,String parentSex,String tutorSex);

    NewPageInfo queryIntentionCustomer(String queryCondition, String parentSex,
                                       String teacherSex, String state, Page page);
}
