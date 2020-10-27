package com.dataee.tutorserver.tutorminiprogressserver.service;

import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.entity.Partner;
import com.dataee.tutorserver.tutorminiprogressserver.bean.InvitedTeacherCountAndMoney;
import com.dataee.tutorserver.tutorminiprogressserver.dao.InvitedTeacherMapper;
import com.dataee.tutorserver.tutorteacherserver.bean.TeacherInvite;

import java.util.List;

public interface InvitedTeacherService {
    NewPageInfo<TeacherInvite> getTeachersById(Integer partnerId, Page page);

    Partner getPartnerByPartnerId(Integer partnerId);

    InvitedTeacherCountAndMoney teacherCountAndMoney(Integer partnerId);

    Integer getPartnerId(Integer id);

}
