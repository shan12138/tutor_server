package com.dataee.tutorserver.tutorpartnerserver.partnermanage.service.impl;

import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.entity.*;
import com.dataee.tutorserver.tutorpartnerserver.partnermanage.bean.*;
import com.dataee.tutorserver.tutorpartnerserver.partnermanage.bean.Commission;
import com.dataee.tutorserver.tutorpartnerserver.partnermanage.dao.PartnerManageMapper;
import com.dataee.tutorserver.tutorpartnerserver.partnermanage.service.IPartnerManageService;
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
 * @CreateDate 2019/11/12 15:54
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PartnerManageServiceImpl implements IPartnerManageService {
    @Autowired
    private PartnerManageMapper partnerManageMapper;

    @Override
    public PartnerInfo getParentInvitationInfo(Integer weChatUserId) {
        Partner partner = partnerManageMapper.getPartnerByWeChatUserId(weChatUserId);
        PartnerInfo partnerInfo = new PartnerInfo();
        partnerInfo.setInviteCode(partner.getInviteCode());
        partnerInfo.setInviteParentNum(partnerManageMapper.getInviteParentNum(partner.getPartnerId()));
        partnerInfo.setSigningNum(partnerManageMapper.getSignNum(partner.getPartnerId(), "已签约"));
        partnerInfo.setSumMoney(partnerManageMapper.getSumMoney(partner.getPartnerId()));
        List<Commission> commissions = partnerManageMapper.getCommissions(partner.getPartnerId());
        Double commissionSum = 0.0;
        List<Integer> courseIdList = new ArrayList<>();
        for(Commission commission : commissions) {
            ParentLevel parentLevel = partnerManageMapper.getParentLevelByParentId(commission.getParentId(), commission.getProductId());
            if(!courseIdList.contains(commission.getCourseId())) {
                commissionSum += commission.getMoney() * parentLevel.getFirstCommissionRatio() / 10000;
                courseIdList.add(commission.getCourseId());
            }
            else {
                commissionSum += commission.getMoney() * parentLevel.getNextCommissionRatio() / 10000;
            }
        }
        partnerInfo.setExpectedReturn(commissionSum);
        return partnerInfo;
    }

    @Override
    public NewPageInfo getParentInvitationList(Page page, Integer weChatUserId) {
        Partner partner = partnerManageMapper.getPartnerByWeChatUserId(weChatUserId);
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<ParentInvitation> parentInvitations =
                partnerManageMapper.getParentInvitationList(partner.getPartnerId());
        PageInfo pageInfo = new PageInfo(parentInvitations);
        NewPageInfo newPageInfo = PageInfoUtil.read(pageInfo);
        return newPageInfo;
    }

    @Override
    public NewPageInfo getParentInvitationRegisterList(Page page, Integer weChatUserId) {
        Partner partner = partnerManageMapper.getPartnerByWeChatUserId(weChatUserId);
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<ParentInvitation> parentInvitations =
                partnerManageMapper.getParentInvitationRegisterList(partner.getPartnerId());
        PageInfo pageInfo = new PageInfo(parentInvitations);
        NewPageInfo newPageInfo = PageInfoUtil.read(pageInfo);
        return newPageInfo;
    }

    @Override
    public List<ParentDetailInfo> getParentInfoDetail(int parentId) {
        List<ParentDetailInfo> parentDetailInfoList = partnerManageMapper.getParentInfoDetail(parentId);
        for(ParentDetailInfo parentDetailInfo : parentDetailInfoList) {
            List<Commission> commissions = parentDetailInfo.getCommissionList();
            Double expectedReturn = 0.0;
            Double actualIncome = 0.0;
            for(int i = 0; i < commissions.size(); i++) {
                Commission commission = commissions.get(i);
                ParentLevel parentLevel = partnerManageMapper.getParentLevelByParentId(commission.getParentId(),
                        commission.getProductId());
                if(i == 0) {
                    expectedReturn += commission.getMoney() * parentLevel.getFirstCommissionRatio() / 10000;
                    actualIncome += commission.getRealMoney() * parentLevel.getFirstCommissionRatio() / 10000;
                }
                else {
                    expectedReturn += commission.getMoney() * parentLevel.getNextCommissionRatio() / 10000;
                    actualIncome += commission.getRealMoney() * parentLevel.getNextCommissionRatio() / 10000;
                }
            }
            parentDetailInfo.setExpectedReturn(expectedReturn);
            parentDetailInfo.setActualIncome(actualIncome);
        }
        return parentDetailInfoList;
    }

    @Override
    public void inviteParent(Integer weChatUserId, PartnerParentRequestBean parent) {
        Partner partner = partnerManageMapper.getPartnerByWeChatUserId(weChatUserId);
        partnerManageMapper.inviteParent(partner.getPartnerId(), partner.getInviteCode(), parent);
    }

    @Override
    public Partner getPartnerInfo(Integer weChatUserId) {
        return partnerManageMapper.getPartnerInfo(weChatUserId);
    }

    @Override
    public void updatePartnerInfo(Integer weChatUserId, String aliPayAccount, String alipayName) {
        partnerManageMapper.updatePartnerInfo(weChatUserId, aliPayAccount, alipayName);
    }

    @Override
    public void withdrawMoney(Integer weChatUserId, Integer number) {
        Partner partner = partnerManageMapper.getPartnerByWeChatUserId(weChatUserId);
        partnerManageMapper.createWithdrawMoney(partner.getPartnerId(), number);
    }

    @Override
    public NewPageInfo getMoneyDetail(Page page, Integer weChatUserId) {
        Partner partner = partnerManageMapper.getPartnerByWeChatUserId(weChatUserId);
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<Bill> bills =
                partnerManageMapper.getBills(partner.getPartnerId(), "合伙人提现");
        PageInfo pageInfo = new PageInfo(bills);
        NewPageInfo newPageInfo = PageInfoUtil.read(pageInfo);
        return newPageInfo;
    }

    @Override
    public NewPageInfo getWithdrawDetail(Page page, Integer weChatUserId) {
        Partner partner = partnerManageMapper.getPartnerByWeChatUserId(weChatUserId);
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<WithdrawalsRecord> withdrawalsRecords =
                partnerManageMapper.getWithdrawDetail(partner.getPartnerId());
        PageInfo pageInfo = new PageInfo(withdrawalsRecords);
        NewPageInfo newPageInfo = PageInfoUtil.read(pageInfo);
        return newPageInfo;
    }

    @Override
    public Partner getPartnerByWeChatUserId(int weChatUserId) {
        return partnerManageMapper.getPartnerByWeChatUserId(weChatUserId);
    }

    @Override
    public Double getParentInvitationMoney(Integer partnerId) {
        List<Product> products = partnerManageMapper.getProducts();
        List<Parent> parents = partnerManageMapper.getParents(partnerId);
        double result = 0.0;
        for(Product product : products) {
            for(Parent parent : parents) {
                // 获取合伙人邀请的家长的购买的课程的课时消耗的佣金列表(由于佣金比例不同因此不能直接相加)
                List<ParentDetailInfo> parentDetailInfos = partnerManageMapper.getMoney(
                        parent.getParentId(), product.getId());
                for(ParentDetailInfo parentDetailInfo : parentDetailInfos) {
                    // 按照不同的佣金比例计算出实际所获得的佣金总和
                    List<Commission> commissions = parentDetailInfo.getCommissionList();
                    Double actualIncome = 0.0;
                    for(int i = 0; i < commissions.size(); i++) {
                        Commission commission = commissions.get(i);
                        ParentLevel parentLevel = partnerManageMapper.getParentLevelByParentId(
                                commission.getParentId(), product.getId());
                        if(parentLevel == null) {
                            // 家长等级佣金尚未设置时，按照最底一层的佣金等级计算
                            parentLevel = partnerManageMapper.getMaxParentLevel(product.getId());
                        }
                        if(i == 0) {
                            actualIncome += commission.getRealMoney() * parentLevel.getFirstCommissionRatio() / 10000;
                        }
                        else {
                            actualIncome += commission.getRealMoney() * parentLevel.getNextCommissionRatio() / 10000;
                        }
                    }
                    result += actualIncome;
                }
            }
        }
        return result;
    }

    @Override
    public Double getWithdrawingMoney(Integer partnerId) {
        return partnerManageMapper.getWithdrawingMoney(partnerId, "待审核");
    }

    @Override
    public Double getWithdrawedMoney(Integer partnerId) {
        return partnerManageMapper.getWithdrawedMoney(partnerId, "待审核");
    }

    @Override
    public String getPartnerTelephone(Integer weChatUserId) {
        return partnerManageMapper.getPartnerTelephone(weChatUserId);
    }
}
