package com.dataee.tutorserver.tutoradminserver.businessmanage.service.impl;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.commons.errorInfoenum.ServiceExceptionsEnum;
import com.dataee.tutorserver.entity.*;
import com.dataee.tutorserver.tutoradminserver.businessmanage.bean.PartnerResponseBean;
import com.dataee.tutorserver.tutoradminserver.businessmanage.dao.BusinessManageMapper;
import com.dataee.tutorserver.tutoradminserver.businessmanage.service.IBusinessManageService;
import com.dataee.tutorserver.utils.InviteCodeUtil;
import com.dataee.tutorserver.utils.PageInfoUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/7 16:58
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BusinessManageServiceImpl implements IBusinessManageService {
    @Autowired
    private BusinessManageMapper businessManageMapper;

    @Override
    public List<PartnerResponseBean> getPartnerList(Page page, String keyWord, String telephone) {
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<PartnerResponseBean> partnerList = businessManageMapper.getPartnerList(keyWord, telephone);
        return partnerList;
    }

    @Override
    public void changePartnerState(Integer id, String state) throws BaseServiceException {
        Partner partner = businessManageMapper.getPartnerById(id);
        if(partner == null) {
            throw new BaseServiceException(ServiceExceptionsEnum.PARTNER_NOT_FOUND);
        }
        businessManageMapper.changePartnerState(id, state);
    }

    @Override
    public WeChatUser getWeChatUserByTelephone(String telephone) throws BaseServiceException {
        User user = businessManageMapper.getUserByTelephone(telephone);
        if(user == null) {
            throw new BaseServiceException(ServiceExceptionsEnum.USER_NOT_EXIST);
        }
        WeChatUser weChatUser = businessManageMapper.getWeChatUser(user.getUserId());
        if(weChatUser == null) {
            throw new BaseServiceException(ServiceExceptionsEnum.WE_CHAT_USER_NOT_EXIST);
        }
        return weChatUser;
    }

    @Override
    public void createPartner(Integer weChatUserId, String name) throws BaseServiceException {
        WeChatUser weChatUser = businessManageMapper.getWeChatUserById(weChatUserId);
        if(weChatUser == null) {
            throw new BaseServiceException(ServiceExceptionsEnum.WE_CHAT_USER_NOT_EXIST);
        }
        Partner partner = new Partner(name, weChatUser.getTelephone(),
                weChatUser.getWeChatUserId(), InviteCodeUtil.getlinkNo());
        businessManageMapper.createPartner(partner);
    }

    @Override
    public NewPageInfo<Parent> getInvitationParent(Page page, String keyWord, String telephone, String state, Integer partnerId) {
        Integer sumCount = businessManageMapper.getSumCountParentInvitation();
        List<ParentInvitation> parentInvitations = businessManageMapper.getParentInvitations(keyWord, telephone, state, page.getLimit(), page.getPage() - 1);
        List<Parent> parentList = new ArrayList<>();
        for(ParentInvitation parentInvitation : parentInvitations) {
            if(parentInvitation.getInvitedParentId() == null && partnerId == null) {
                // 尚未匹配家长账号的家长
                Parent parent = businessManageMapper.getNotMatchedParent(parentInvitation.getId());
                if(parent != null) {
                    parentList.add(parent);
                }
            }
            else {
                // 匹配家长账号的家长
                Parent parent = businessManageMapper.getMatchedParent(parentInvitation.getId(), partnerId);
                parent.setInvitedParentId(parentInvitation.getInvitedParentId());
                if(parent != null) {
                    parentList.add(parent);
                }
            }
        }
        NewPageInfo newPageInfo = new NewPageInfo();
        newPageInfo.setTotal(sumCount == null ? 0 : sumCount);
        newPageInfo.setList(parentList);
        return newPageInfo;
    }

    @Override
    public List<Parent> getParentByTelephone(String telephone) {
        return businessManageMapper.getParentByTelephone(telephone);
    }

    @Override
    public void matchParentAccount(Integer id, Integer parentId) throws BaseServiceException {
        Integer invitedParentId = businessManageMapper.getInvitedParentId(id);
        Integer parentInvitationId = businessManageMapper.getInviteParentByParentId(parentId);
        if(invitedParentId != null || parentInvitationId != null) {
            throw new BaseServiceException(ServiceExceptionsEnum.MATCH_EXISTS);
        }
        businessManageMapper.matchParentAccount(id, parentId, "未跟进");
    }

    @Override
    public List<Administrator> getConsultantList() {
        return businessManageMapper.getConsultantList();
    }

    @Override
    public void distributionConsultant(Integer parentInvitationId, Integer consultantId) {
        businessManageMapper.distributionConsultant(parentInvitationId, consultantId, "未跟进");
    }

    @Override
    public ParentInvitation getInviteParent(Integer id) {
        return businessManageMapper.getInviteParentById(id);
    }
}
