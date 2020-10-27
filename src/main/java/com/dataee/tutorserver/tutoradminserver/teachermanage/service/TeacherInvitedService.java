package com.dataee.tutorserver.tutoradminserver.teachermanage.service;

import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.entity.TeacherInvitation;
import com.dataee.tutorserver.tutoradminserver.teachermanage.bean.InviteTeacherGift;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeacherInvitedService {
    NewPageInfo<TeacherInvitation> getTeacherInviteList(String queryCondition, String telephone, Integer partnerId, String status, Page page);

    NewPageInfo<InviteTeacherGift> getTeacherGiftInviteList(String queryCondition, Integer invitePerson, String status,Page page);

    void  updateInviteTeacherGiftStatus(Integer id);
}
