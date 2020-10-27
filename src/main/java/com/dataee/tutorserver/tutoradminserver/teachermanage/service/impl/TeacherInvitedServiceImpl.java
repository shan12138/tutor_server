package com.dataee.tutorserver.tutoradminserver.teachermanage.service.impl;

import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.entity.TeacherInvitation;
import com.dataee.tutorserver.tutoradminserver.teachermanage.bean.InviteTeacherGift;
import com.dataee.tutorserver.tutoradminserver.teachermanage.bean.TeacherInvitedBean;
import com.dataee.tutorserver.tutoradminserver.teachermanage.bean.TeacherListResponseBean;
import com.dataee.tutorserver.tutoradminserver.teachermanage.dao.TeacherInvitedMapper;
import com.dataee.tutorserver.tutoradminserver.teachermanage.service.TeacherInvitedService;
import com.dataee.tutorserver.utils.PageInfoUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Service
public class TeacherInvitedServiceImpl implements TeacherInvitedService {
    @Autowired
    TeacherInvitedMapper teacherInvitedMapper;
    @Override
    public NewPageInfo<TeacherInvitation> getTeacherInviteList(String queryCondition, String telephone, Integer partnerId, String status, Page page) {
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<TeacherInvitation> teacherInvites = teacherInvitedMapper.getTeacherInviteList(queryCondition, telephone, partnerId, status);
        List<TeacherInvitedBean> teacherInvitedBeans =new ArrayList<>();
        for (TeacherInvitation teacherInvite:teacherInvites){
            TeacherInvitedBean teacherInvitedBean =new TeacherInvitedBean();
            teacherInvitedBean.setId(teacherInvite.getId());
            teacherInvitedBean.setTeacherName(teacherInvite.getTeacherName());
            teacherInvitedBean.setTelephone(teacherInvite.getTelephone());
            teacherInvitedBean.setStatus(teacherInvite.getStatus());
            if(teacherInvite.getPartner()!=null){
                teacherInvitedBean.setInvitePerson(teacherInvite.getPartner().getPartnerName());
                teacherInvitedBean.setInvitePersonTel(teacherInvite.getPartner().getTelephone());
            }
            else if(teacherInvite.getTeacher()!=null){
                teacherInvitedBean.setInvitePerson(teacherInvite.getTeacher().getTeacherName());
                teacherInvitedBean.setInvitePersonTel(teacherInvite.getTeacher().getTelephone());
            }
            teacherInvitedBeans.add(teacherInvitedBean);
        }
        PageInfo pageInfo = new PageInfo(teacherInvitedBeans);
        NewPageInfo newPageInfo = PageInfoUtil.read(pageInfo);
        return newPageInfo;
    }

    @Override
    public NewPageInfo<InviteTeacherGift> getTeacherGiftInviteList(String queryCondition, Integer invitePerson, String status,Page page) {
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<InviteTeacherGift>  inviteList = teacherInvitedMapper.getTeacherGiftInviteList(queryCondition, invitePerson, status);
        PageInfo pageInfo = new PageInfo(inviteList);
        NewPageInfo newPageInfo = PageInfoUtil.read(pageInfo);
        return newPageInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateInviteTeacherGiftStatus(Integer id) {
        teacherInvitedMapper.updateInviteTeacherGiftStatus(id);
    }
}
