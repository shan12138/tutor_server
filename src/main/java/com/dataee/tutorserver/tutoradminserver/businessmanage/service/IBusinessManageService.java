package com.dataee.tutorserver.tutoradminserver.businessmanage.service;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.entity.*;
import com.dataee.tutorserver.tutoradminserver.businessmanage.bean.PartnerResponseBean;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/7 16:58
 */
public interface IBusinessManageService {
    List<PartnerResponseBean> getPartnerList(Page page, String keyWord, String telephone);

    void changePartnerState(Integer id, String state) throws BaseServiceException;

    WeChatUser getWeChatUserByTelephone(String telephone) throws BaseServiceException;

    void createPartner(Integer weChatUserId, String name) throws BaseServiceException;

    NewPageInfo<Parent> getInvitationParent(Page page, String keyWord, String telephone, String state, Integer partnerId);

    List<Parent> getParentByTelephone(String telephone);

    void matchParentAccount(Integer id, Integer parentId) throws BaseServiceException;

    List<Administrator> getConsultantList();

    void distributionConsultant(Integer parentInvitationId, Integer consultantId) throws BaseServiceException;

    ParentInvitation getInviteParent(Integer id);
}
