package com.dataee.tutorserver.tutoradminserver.patriarchmanage.service;

import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.entity.ParentInvitation;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/7 10:29
 */
public interface IParentInvitationService {
    NewPageInfo<ParentInvitation> getInvitationList(Integer adminId, Page page);

    void updateInviteParentAdminState(Integer id, String state);
}
