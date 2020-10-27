package com.dataee.tutorserver.tutoradminserver.financialmanage.service;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/12 13:40
 */
public interface IFinancialManageService {
    NewPageInfo getWithdrawList(Page page, String keyWord, Integer partnerId, String state);

    void onlineDistribution(int id) throws BaseServiceException;

    void offlineDistribution(int id) throws BaseServiceException;

    NewPageInfo getInvitationGiftParentList(Page page, String keyWord, Integer parentId, String state);

    void invitationParentGiftDistribution(int id) throws BaseServiceException;
}
