package com.dataee.tutorserver.tutoradminserver.financialmanage.service.impl;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.commons.errorInfoenum.ServiceExceptionsEnum;
import com.dataee.tutorserver.entity.ParentInvitation;
import com.dataee.tutorserver.entity.WithdrawalsRecord;
import com.dataee.tutorserver.tutoradminserver.financialmanage.dao.FinancialManageMapper;
import com.dataee.tutorserver.tutoradminserver.financialmanage.service.IFinancialManageService;
import com.dataee.tutorserver.utils.PageInfoUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/12 13:41
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FinancialManageServiceImpl implements IFinancialManageService {
    @Autowired
    private FinancialManageMapper financialManageMapper;

    @Override
    public NewPageInfo getWithdrawList(Page page, String keyWord, Integer partnerId, String state) {
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<WithdrawalsRecord> withdrawalsRecords = financialManageMapper.getWithdrawList(keyWord, partnerId, state);
        PageInfo pageInfo = new PageInfo(withdrawalsRecords);
        NewPageInfo newPageInfo = PageInfoUtil.read(pageInfo);
        return newPageInfo;
    }

    @Override
    public void onlineDistribution(int id) throws BaseServiceException {
        WithdrawalsRecord withdrawalsRecord = financialManageMapper.getWithDrawById(id);
        if(withdrawalsRecord == null) {
            throw new BaseServiceException(ServiceExceptionsEnum.WITHDRAW_NOT_FOUND);
        }
        financialManageMapper.onlineDistribution(id, "线上已发放");
        financialManageMapper.createWithdrawBill("流出", "合伙人提现", withdrawalsRecord.getWithdrawalsMoney(),
                 "平台", "合伙人", withdrawalsRecord.getPartnerId());
        // TODO: 调用支付平台接口并且在对应合伙人的钱包中数据变动未做
    }

    @Override
    public void offlineDistribution(int id) throws BaseServiceException {
        WithdrawalsRecord withdrawalsRecord = financialManageMapper.getWithDrawById(id);
        if(withdrawalsRecord == null) {
            throw new BaseServiceException(ServiceExceptionsEnum.WITHDRAW_NOT_FOUND);
        }
        financialManageMapper.onlineDistribution(id, "线下已发放");
        financialManageMapper.createWithdrawBill("流出", "合伙人提现", withdrawalsRecord.getWithdrawalsMoney(),
                "平台", "合伙人", withdrawalsRecord.getPartnerId());
    }

    @Override
    public NewPageInfo getInvitationGiftParentList(Page page, String keyWord, Integer parentId, String state) {
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<ParentInvitation> parentInvitations = financialManageMapper.getInvitationGiftParentList(keyWord, parentId, state);
        PageInfo pageInfo = new PageInfo(parentInvitations);
        NewPageInfo newPageInfo = PageInfoUtil.read(pageInfo);
        return newPageInfo;
    }

    @Override
    public void invitationParentGiftDistribution(int id) {
        financialManageMapper.invitationParentGiftDistribution(id, "已得礼品");
    }
}
