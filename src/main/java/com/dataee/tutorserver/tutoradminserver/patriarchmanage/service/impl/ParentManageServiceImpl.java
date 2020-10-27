package com.dataee.tutorserver.tutoradminserver.patriarchmanage.service.impl;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.commons.exceptions.SQLOperationException;
import com.dataee.tutorserver.entity.*;
import com.dataee.tutorserver.tutoradminserver.messagemanage.bean.InfoChangeVerifyRequestBean;
import com.dataee.tutorserver.tutoradminserver.messagemanage.dao.MessageManageMapper;
import com.dataee.tutorserver.tutoradminserver.patriarchmanage.bean.ClassAndHour;
import com.dataee.tutorserver.tutoradminserver.patriarchmanage.bean.UpdateInvitation;
import com.dataee.tutorserver.tutoradminserver.patriarchmanage.dao.ParentManageMapper;
import com.dataee.tutorserver.tutoradminserver.patriarchmanage.service.IParentManageService;
import com.dataee.tutorserver.tutoradminserver.statemanage.dao.StateManageMapper;
import com.dataee.tutorserver.tutorpatriarchserver.dao.ParentCenterMapper;
import com.dataee.tutorserver.userserver.bean.GetCourseTeacherListResponseBean;
import com.dataee.tutorserver.userserver.dao.CourseMapper;
import com.dataee.tutorserver.utils.PageInfoUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/10 15:22
 */
@Service
public class ParentManageServiceImpl implements IParentManageService {
    @Autowired
    private ParentManageMapper parentManageMapper;
    @Autowired
    private StateManageMapper stateManageMapper;
    @Autowired
    private MessageManageMapper messageManageMapper;
    @Autowired
    private ParentCenterMapper parentCenterMapper;
    @Autowired
    private CourseMapper courseMapper;

    @Override
    public NewPageInfo getParentList( Integer state,Page page) {
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<Parent> parents = parentManageMapper.getParentList(state);
        PageInfo pageInfo = new PageInfo(parents);
        NewPageInfo newPageInfo = PageInfoUtil.read(pageInfo);
        return newPageInfo;
    }

    @Override
    public Parent getParentDetail(String parentId) {
        return parentManageMapper.getParentDetail(parentId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void acceptParentDetail(InfoChangeVerifyRequestBean infoChangeVerifyRequestBean) throws BaseServiceException {
        try {
            this.setParentState(infoChangeVerifyRequestBean, 3, "申请报名信息审核通过");
        } catch (Exception e) {
            throw new SQLOperationException();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void denyParentDetail(InfoChangeVerifyRequestBean infoChangeVerifyRequestBean) throws BaseServiceException {
        try {
             this.setParentState(infoChangeVerifyRequestBean, 1, "申请报名信息审核未通过");
             parentManageMapper.changeAddressState(infoChangeVerifyRequestBean.getId());
             System.out.println("----------"+"地址状态改变成功");
        } catch (Exception e) {
            throw new SQLOperationException();
        }
    }

    @Override
    public NewPageInfo queryParent(String queryCondition, String state, String sex, Page page) {
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<Parent> parents = parentManageMapper.queryParent(queryCondition, state, sex);
        PageInfo pageInfo = new PageInfo(parents);
        NewPageInfo newPageInfo = PageInfoUtil.read(pageInfo);
        return newPageInfo;
    }

    @Override
    public List<ClassAndHour> getClassAndHourOfParent(String parentId) {
        return parentManageMapper.getClassAndHourOfParent(parentId);
    }

    @Override
    public NewPageInfo getAuthEdParentList(Page page,String studentName,String sex) {
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<Parent> parents = parentManageMapper.getAuthEdParentList(studentName,sex);
        PageInfo pageInfo = new PageInfo(parents);
        NewPageInfo newPageInfo = PageInfoUtil.read(pageInfo);
        return newPageInfo;
    }

    @Override
    public List<Student> getOwnChildren(String parentId) {
        List<Student> students = parentCenterMapper.getOwnChildren(Integer.parseInt(parentId));
        return students;
    }

    @Override
    public List<GetCourseTeacherListResponseBean> getCourseList(String tableId, String id) {
        List<GetCourseTeacherListResponseBean> courseTeacherListResponseBeanList = courseMapper.getCourseList(tableId,
                Integer.parseInt(id));
        return courseTeacherListResponseBeanList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeInvitation(UpdateInvitation updateInvitation) {
        parentManageMapper.changeInvitation(updateInvitation);
    }

    @Override
    public List<ParentLevel> getParentLevelList() {
        return parentManageMapper.getParentLevelList();
    }

    @Override
    public List<Partner> getPartnerList() {
        return parentManageMapper.getPartnerList();
    }

    /**
     * 管理员审核信息进行反馈通知家长的统一处理
     *
     * @param infoChangeVerifyRequestBean
     * @param state
     * @param content
     * @throws BaseServiceException
     */
    @Transactional(rollbackFor = Exception.class)
    public void setParentState(InfoChangeVerifyRequestBean infoChangeVerifyRequestBean, Integer state, String content) throws BaseServiceException {
        int number = stateManageMapper.changeDataState("parent", state, "parent_id", infoChangeVerifyRequestBean.getId());
        if (number != 1) {
            throw new SQLOperationException();
        }
        MessageInformation messageInformation = new MessageInformation(
                infoChangeVerifyRequestBean.getId(), 40, content,
                infoChangeVerifyRequestBean.getRemark()
        );
        number = messageManageMapper.addToMessageTable(messageInformation);
        if (number != 1) {
            throw new SQLOperationException();
        }
    }

}
