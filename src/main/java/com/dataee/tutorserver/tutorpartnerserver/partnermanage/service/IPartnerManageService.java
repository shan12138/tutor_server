package com.dataee.tutorserver.tutorpartnerserver.partnermanage.service;

import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.entity.Partner;
import com.dataee.tutorserver.tutorpartnerserver.partnermanage.bean.ParentDetailInfo;
import com.dataee.tutorserver.tutorpartnerserver.partnermanage.bean.PartnerInfo;
import com.dataee.tutorserver.tutorpartnerserver.partnermanage.bean.PartnerParentRequestBean;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/12 15:53
 */
public interface IPartnerManageService {
    PartnerInfo getParentInvitationInfo(Integer weChatUserId);

    NewPageInfo getParentInvitationList(Page page, Integer weChatUserId);

    NewPageInfo getParentInvitationRegisterList(Page page, Integer weChatUserId);

    List<ParentDetailInfo> getParentInfoDetail(int parentId);

    void inviteParent(Integer weChatUserId, PartnerParentRequestBean parent);

    Partner getPartnerInfo(Integer weChatUserId);

    void updatePartnerInfo(Integer weChatUserId, String alipayAccount, String alipayName);

    void withdrawMoney(Integer weChatUserId, Integer number);

    NewPageInfo getMoneyDetail(Page page, Integer weChatUserId);

    NewPageInfo getWithdrawDetail(Page page, Integer weChatUserId);

    Partner getPartnerByWeChatUserId(int weChatUserId);

    Double getParentInvitationMoney(Integer partnerId);

    Double getWithdrawingMoney(Integer partnerId);

    Double getWithdrawedMoney(Integer partnerId);

    String getPartnerTelephone(Integer weChatUserId);
}
