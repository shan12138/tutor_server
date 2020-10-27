package com.dataee.tutorserver.tutorpatriarchserver.service;

import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.entity.ParentInvitation;
import com.dataee.tutorserver.tutorpatriarchserver.bean.ParentInvitationNum;
import com.dataee.tutorserver.tutorpatriarchserver.bean.ParentInvitationRequestBean;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/7 10:26
 */
public interface IInvitationService {
    void inviteParent(Integer parentId, ParentInvitationRequestBean parentInvitation);

    NewPageInfo<ParentInvitation> getInvitationList(Integer parentId, Page page);

    NewPageInfo<ParentInvitation> getInvitationRegisterList(Integer parentId, Page page);

    ParentInvitationNum getParentInvitationNum(Integer parentId);
}
