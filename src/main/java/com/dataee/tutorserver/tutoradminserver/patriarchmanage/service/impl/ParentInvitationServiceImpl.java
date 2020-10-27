package com.dataee.tutorserver.tutoradminserver.patriarchmanage.service.impl;

import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.entity.ParentInvitation;
import com.dataee.tutorserver.tutoradminserver.coursemanage.dao.CourseMngMapper;
import com.dataee.tutorserver.tutoradminserver.patriarchmanage.dao.ParentInvitationMapper;
import com.dataee.tutorserver.tutoradminserver.patriarchmanage.service.IParentInvitationService;
import com.dataee.tutorserver.tutorpatriarchserver.dao.InvitationMapper;
import com.dataee.tutorserver.utils.PageInfoUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/7 10:29
 */
@Service
public class ParentInvitationServiceImpl implements IParentInvitationService {
    @Autowired
    private ParentInvitationMapper parentInvitationMapper;
    @Autowired
    private CourseMngMapper courseMngMapper;

    @Override
    public NewPageInfo<ParentInvitation> getInvitationList(Integer adminId, Page page) {
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<ParentInvitation> invitationList = parentInvitationMapper.getInvitationList(adminId);
        PageInfo pageInfo = new PageInfo(invitationList);
        NewPageInfo<ParentInvitation> newPageInfo = PageInfoUtil.read(pageInfo);
        return newPageInfo;
    }

    @Override
    public void updateInviteParentAdminState(Integer id, String state) {
        parentInvitationMapper.updateInviteParentAdminState(id, state);
    }
}
