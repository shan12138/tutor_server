package com.dataee.tutorserver.tutorpatriarchserver.service.impl;

import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.entity.Parent;
import com.dataee.tutorserver.entity.ParentInvitation;
import com.dataee.tutorserver.entity.Student;
import com.dataee.tutorserver.tutorpatriarchserver.bean.ParentInvitationNum;
import com.dataee.tutorserver.tutorpatriarchserver.bean.ParentInvitationRequestBean;
import com.dataee.tutorserver.tutorpatriarchserver.dao.InvitationMapper;
import com.dataee.tutorserver.tutorpatriarchserver.service.IInvitationService;
import com.dataee.tutorserver.utils.PageInfoUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/7 10:26
 */
@Service
public class InvitationServiceImpl implements IInvitationService {
    @Autowired
    private InvitationMapper invitationMapper;

    @Override
    public void inviteParent(Integer parentId, ParentInvitationRequestBean parentInvitation) {
        Parent parent = invitationMapper.getParentById(parentId);
        invitationMapper.inviteParent(parent.getParentId(), parent.getInviteCode(), parentInvitation);
    }

    @Override
    public NewPageInfo<ParentInvitation> getInvitationList(Integer parentId, Page page) {
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<ParentInvitation> invitations = invitationMapper.getInvitationList(parentId);
        PageInfo pageInfo = new PageInfo(invitations);
        NewPageInfo<ParentInvitation> newPageInfo = PageInfoUtil.read(pageInfo);
        return newPageInfo;
    }

    @Override
    public NewPageInfo<ParentInvitation> getInvitationRegisterList(Integer parentId, Page page) {
        PageHelper.startPage(page.getPage(), page.getLimit());
        String inviteCode = invitationMapper.getInviteCode(parentId);
        List<ParentInvitation> invitations = invitationMapper.getInvitationRegisterList(inviteCode);
        PageInfo pageInfo = new PageInfo(invitations);
        NewPageInfo<ParentInvitation> newPageInfo = PageInfoUtil.read(pageInfo);
        return newPageInfo;
    }

    @Override
    public ParentInvitationNum getParentInvitationNum(Integer parentId) {
        ParentInvitationNum parentInvitationNum = new ParentInvitationNum();
        parentInvitationNum.setInviteParentNum(invitationMapper.getInviteParentNum(parentId));
        parentInvitationNum.setSigningNum(invitationMapper.getSigningNum(parentId, "已签约"));
        parentInvitationNum.setInviteCode(invitationMapper.getInviteCode(parentId));
        return parentInvitationNum;
    }
}
