package com.dataee.tutorserver.tutoradminserver.teachermanage.service.impl;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.commons.exceptions.SQLOperationException;
import com.dataee.tutorserver.entity.MessageInformation;
import com.dataee.tutorserver.entity.Product;
import com.dataee.tutorserver.entity.Teacher;
import com.dataee.tutorserver.tutoradminserver.messagemanage.bean.InfoChangeVerifyRequestBean;
import com.dataee.tutorserver.tutoradminserver.messagemanage.dao.MessageManageMapper;
import com.dataee.tutorserver.tutoradminserver.patriarchmanage.bean.ClassAndHour;
import com.dataee.tutorserver.tutoradminserver.statemanage.dao.StateManageMapper;
import com.dataee.tutorserver.tutoradminserver.teachermanage.bean.PlatformInfoChangeRequestBean;
import com.dataee.tutorserver.tutoradminserver.teachermanage.bean.TeacherListResponseBean;
import com.dataee.tutorserver.tutoradminserver.teachermanage.dao.TeacherManageMapper;
import com.dataee.tutorserver.tutoradminserver.teachermanage.service.ITeacherManageService;
import com.dataee.tutorserver.tutorteacherserver.dao.TeacherInviteMapper;
import com.dataee.tutorserver.utils.PageInfoUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/10 23:29
 */
@Service
public class TeacherManageServiceImpl implements ITeacherManageService {
    @Autowired
    private TeacherManageMapper teacherManageMapper;
    @Autowired
    private StateManageMapper stateManageMapper;
    @Autowired
    private MessageManageMapper messageManageMapper;
    @Autowired
    private TeacherInviteMapper teacherInviteMapper;
    @Override
    public NewPageInfo getTeacherAuthEdList(Page page) {
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<TeacherListResponseBean> teacherListResponseBeanList = teacherManageMapper.getTeacherAuthEdList();
        PageInfo pageInfo = new PageInfo(teacherListResponseBeanList);
        NewPageInfo newPageInfo = PageInfoUtil.read(pageInfo);
        return newPageInfo;
    }

    @Override
    public NewPageInfo getTeacherAuthIngList(Page page,String queryCondition, String state,String sex) {
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<TeacherListResponseBean> teacherListResponseBeanList = teacherManageMapper.getTeacherAuthIngList( queryCondition,  state, sex );


        PageInfo pageInfo = new PageInfo(teacherListResponseBeanList);
        NewPageInfo newPageInfo = PageInfoUtil.read(pageInfo);
        return newPageInfo;
    }

    @Override
    public Teacher getTeacherDetail(String id) {
        return teacherManageMapper.getTeacherDetailInfo(id);
    }

//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void acceptTeacherBasicInfo(InfoChangeVerifyRequestBean infoChangeVerifyRequestBean) throws BaseServiceException {
//        try {
//            this.setTeacherState(infoChangeVerifyRequestBean, 5, "基本报名申请信息审核通过");
//        } catch (Exception e) {
//            throw new SQLOperationException();
//        }
//    }
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void denyTeacherBasicInfo(InfoChangeVerifyRequestBean infoChangeVerifyRequestBean) throws BaseServiceException {
//        try {
//            this.setTeacherState(infoChangeVerifyRequestBean, 1, "基本报名申请信息审核未通过");
//        } catch (Exception e) {
//            throw new SQLOperationException();
//        }
//    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void acceptTeacherNextInfo(InfoChangeVerifyRequestBean infoChangeVerifyRequestBean) throws BaseServiceException {
        try {
            this.setTeacherState(infoChangeVerifyRequestBean, 4, "进一步报名申请信息审核通过");
            String telephone = teacherInviteMapper.getTelephoneById(infoChangeVerifyRequestBean.getId());
            teacherInviteMapper.updateTeacherInviteState(telephone,"已转正");
        } catch (Exception e) {
            throw new SQLOperationException();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void denyTeacherNextInfo(InfoChangeVerifyRequestBean infoChangeVerifyRequestBean) throws BaseServiceException {
        try {
            this.setTeacherState(infoChangeVerifyRequestBean, 3, "进一步报名申请信息审核未通过");
            String telephone = teacherInviteMapper.getTelephoneById(infoChangeVerifyRequestBean.getId());
            teacherInviteMapper.updateTeacherInviteState(telephone,"已拒绝");
        } catch (Exception e) {
            throw new SQLOperationException();
        }
    }

    @Override
    public NewPageInfo queryTeacher(String queryCondition, String state, String sex,Integer start,Integer end, Page page) {
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<TeacherListResponseBean> teacherListResponseBeanList = teacherManageMapper.queryTeacher(queryCondition, state, sex,start,end);
        PageInfo pageInfo = new PageInfo(teacherListResponseBeanList);
        NewPageInfo newPageInfo = PageInfoUtil.read(pageInfo);
        return newPageInfo;
    }

    @Override
    public String getTeacherIdOfCourse(String teacherId) {
        return teacherManageMapper.getTeacherIdOfCourse(teacherId);
    }

    /**
     * 管理员审核信息进行反馈通知教师的统一处理
     *
     * @param infoChangeVerifyRequestBean
     * @param state
     * @param content
     * @throws BaseServiceException
     */
    @Transactional(rollbackFor = Exception.class)
    public void setTeacherState(InfoChangeVerifyRequestBean infoChangeVerifyRequestBean, Integer state, String content) throws BaseServiceException {
        int number = stateManageMapper.changeDataState("teacher", state, "teacher_id", infoChangeVerifyRequestBean.getId());
        if (number != 1) {
            throw new SQLOperationException();
        }
        MessageInformation messageInformation = new MessageInformation(
                infoChangeVerifyRequestBean.getId(), 20, content,
                infoChangeVerifyRequestBean.getRemark()
        );
        number = messageManageMapper.addToMessageTable(messageInformation);
        if (number != 1) {
            throw new SQLOperationException();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changePlatformInfo(PlatformInfoChangeRequestBean platformChangeInfo) {
        Integer teacherId = platformChangeInfo.getTeacherId();
        //先将之前的授课范围置成无效
        teacherManageMapper.changeTeachingAreaState(teacherId);
        //再插入新的授课范围
        for (String teachingArea : platformChangeInfo.getTeachingArea()) {
            if (teachingArea != null && !teachingArea.equals("")) {
                String grade = teachingArea.split("-")[0];
                String subject = teachingArea.split("-")[1];
                teacherManageMapper.insertTeachingArea(teacherId, grade, subject);
            }
        }
        //再修改老师对应的产品
        if(platformChangeInfo.getProducts()!=null&&platformChangeInfo.getProducts().size()!=0) {
            teacherManageMapper.changeTeacherProduct(teacherId);
            for (Integer productId:platformChangeInfo.getProducts()){
                teacherManageMapper.insertTeacherOfProduct(teacherId,productId);
            }
        }
        //再修改面试结果
        teacherManageMapper.changeInterviewResult(teacherId, platformChangeInfo.getInterviewResult());
    }

    @Override
    public void changePlatformIntroduce(String platformIntroduce, Integer teacherId) {
        teacherManageMapper.changePlatformIntroduce(platformIntroduce, teacherId);
    }
}
