package com.dataee.tutorserver.tutoradminserver.electricsalemanage.service.impl;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.commons.exceptions.SQLOperationException;
import com.dataee.tutorserver.entity.IntentionCustomer;
import com.dataee.tutorserver.tutoradminserver.electricsalemanage.dao.ElectricSaleManageMapper;
import com.dataee.tutorserver.tutoradminserver.electricsalemanage.service.IElectricSaleManageService;
import com.dataee.tutorserver.utils.PageInfoUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/29 21:02
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ElectricSaleManageServiceImpl implements IElectricSaleManageService {
    @Autowired
    private ElectricSaleManageMapper electricSaleManageMapper;

    @Override
    public NewPageInfo getIntentionCustomer(Page page) {
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<IntentionCustomer> intentionCustomers = electricSaleManageMapper.getIntentionCustomer();
        PageInfo pageInfo = new PageInfo(intentionCustomers);
        NewPageInfo newPageInfo = PageInfoUtil.read(pageInfo);
        return newPageInfo;
    }

    @Override
    public IntentionCustomer getIntentionCustomerDetailInfo(Integer id) {
        return electricSaleManageMapper.getIntentionCustomerDetailInfo(id);
    }

    @Override
    public void changeCustomer(IntentionCustomer intentionCustomer) throws BaseServiceException {
        int number = electricSaleManageMapper.changeIntentionCustomer(intentionCustomer);
        if (number != 1) {
            throw new SQLOperationException();
        }
    }

    @Override
    public void createIntentionCustomer(IntentionCustomer intentionCustomer) throws BaseServiceException {
        int number = electricSaleManageMapper.createIntentionCustomer(intentionCustomer);
        if (number != 1) {
            throw new SQLOperationException();
        }
    }

    @Override
    public void changeCustomerState(Integer id) {
        electricSaleManageMapper.changeCustomerState(id);
    }

    @Override
    public NewPageInfo getCourseIntentionCustomer(Page page,String queryCondition,String parentSex,String tutorSex) {
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<IntentionCustomer> intentionCustomers = electricSaleManageMapper.getCourseIntentionCustomer( queryCondition, parentSex, tutorSex);
        PageInfo pageInfo = new PageInfo(intentionCustomers);
        NewPageInfo newPageInfo = PageInfoUtil.read(pageInfo);
        return newPageInfo;
    }

    @Override
    public NewPageInfo queryIntentionCustomer(String queryCondition,
                                              String parentSex, String teacherSex, String state, Page page) {
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<IntentionCustomer> intentionCustomers = electricSaleManageMapper.queryIntentionCustomer(queryCondition,
                parentSex, teacherSex,state);
        PageInfo pageInfo = new PageInfo(intentionCustomers);
        NewPageInfo newPageInfo = PageInfoUtil.read(pageInfo);
        return newPageInfo;
    }

}
