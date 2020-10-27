package com.dataee.tutorserver.tutorteacherserver.service.impl;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.commons.commonservice.IOSSService;
import com.dataee.tutorserver.commons.exceptions.SQLOperationException;
import com.dataee.tutorserver.entity.Question;
import com.dataee.tutorserver.entity.Teacher;
import com.dataee.tutorserver.entity.TeacherLabel;
import com.dataee.tutorserver.tutorteacherserver.bean.*;
import com.dataee.tutorserver.tutorteacherserver.dao.TeacherCenterMapper;
import com.dataee.tutorserver.tutorteacherserver.dao.TeacherInviteMapper;
import com.dataee.tutorserver.tutorteacherserver.service.ITeacherCenterService;
import com.dataee.tutorserver.utils.InviteCodeUtil;
import com.dataee.tutorserver.utils.PageInfoUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TeacherCenterServiceImpl implements ITeacherCenterService {
    @Value("${excellentTeacherSize}")
    private Integer excellentTeacherSize;

    @Autowired
    private TeacherCenterMapper teacherCenterMapper;

    @Autowired
    private IOSSService ossService;

    @Autowired
    private TeacherInviteMapper teacherInviteMapper;


    @Override
    public List<Question> getPaper(String grade, String subject) {
        return teacherCenterMapper.getPaper(grade, subject);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeTeaInfo(TeacherDetailInfoRequestBean teacherDetailInfoRequestBean) throws SQLOperationException {
        int number = teacherCenterMapper.changeTeaInfo(teacherDetailInfoRequestBean);
        if (number != 1) {
            throw new SQLOperationException();
        }
    }

    @Override
    public Teacher getTeacherInfo(int teacherId) {
        return teacherCenterMapper.getTeacherInfo(teacherId);
    }

    @Override
    public NewPageInfo<Teacher> queryTeacher(Page page) {
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<Teacher> teacherList = teacherCenterMapper.queryTeacher();
        PageInfo pageInfo = new PageInfo(teacherList);
        NewPageInfo<Teacher> newPageInfo = PageInfoUtil.read(pageInfo);
        newPageInfo.setPageNum(excellentTeacherSize);
        return newPageInfo;
}

    @Override
    public String getHeadPicture(Integer personId) {
        return teacherCenterMapper.getHeadPicture(personId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTeacherState(Integer teacherId) throws SQLOperationException {
        Integer count = teacherCenterMapper.updateTeacherStateById(teacherId);
        String telephone = teacherInviteMapper.getTelephoneById(teacherId);
        teacherInviteMapper.updateTeacherInviteState(telephone,"申请中");
        if (count != 1) {
            throw new SQLOperationException();
        }
    }

    @Override
    public void saveCourseScore(ScoreBean score) {
        teacherCenterMapper.saveCourseScore(score);
    }

    @Override
    public void saveTeachingArea(ScoreBean score) {
        teacherCenterMapper.saveTeachingArea(score);
    }

    @Override
    public Integer getTeachingAreaId(ScoreBean score) {
        return teacherCenterMapper.getTeachingAreaId(score);
    }

    @Override
    public NewPageInfo getScoreHistory(Integer teacherId, Page page) {
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<ScoreBean> scoreBeans = teacherCenterMapper.getScoreHistory(teacherId);
        PageInfo pageInfo = new PageInfo(scoreBeans);
        NewPageInfo newPageInfo = PageInfoUtil.read(pageInfo);
        return newPageInfo;
    }

    @Override
    public void writeFirstInformation(TeacherBasicInfoRequestBean teacherBasicInfoRequestBean) {
        teacherBasicInfoRequestBean.setOwnInviteCode(InviteCodeUtil.getlinkNo());
        teacherCenterMapper.writeFirstInformation(teacherBasicInfoRequestBean);
//        //获取正在注册的老师的手机号
//        String telephone = teacherInviteMapper.getTelephoneById(teacherBasicInfoRequestBean.getTeacherId());
//        //获取谁邀请这个老师的老师
//        Integer teacherId = teacherInviteMapper.getTeacherId(telephone);
//        if(teacherId!=null){
//            TeacherInviteRegister registerInfo = teacherInviteMapper.getInviteRegisterInfo(teacherId);
//
//            teacherBasicInfoRequestBean.setInvitedTeacherId(teacherId);
//            teacherBasicInfoRequestBean.setTeacherLevel(registerInfo.getTeacherLevel()+1);
//            teacherBasicInfoRequestBean.setPartnerId(registerInfo.getPartnerId());
//        }else if(teacherId==null){
//
//            Integer partnerId = teacherInviteMapper.getPartnerId(telephone);
//            teacherBasicInfoRequestBean.setOwnInviteCode(InviteCodeUtil.getlinkNo());
//            teacherBasicInfoRequestBean.setPartnerId(partnerId);
//            teacherBasicInfoRequestBean.setTeacherLevel(1);
//        }


        //老师填写完邀请码后，把邀请老师表的状态改为申请中
     //   teacherInviteMapper.updateTeacherInviteState(telephone,"申请中");
        //把邀请老师表的被邀请的老师的id补全
       // teacherInviteMapper.updateTeacherInvitedPerson(teacherBasicInfoRequestBean.getTeacherId(),telephone);

    }

    @Override
    public void writeSecondInformation(TeacherBasicInfoRequestBean teacherBasicInfoRequestBean) {

        teacherCenterMapper.writeSecondInformation(teacherBasicInfoRequestBean);
    }

    @Override
    public Teacher getFirstInformation(Integer teacherId) {
        return teacherCenterMapper.getFirstInformation(teacherId);
    }

    @Override
    public Teacher getSecondInformation(Integer teacherId) {
        return teacherCenterMapper.getSecondInformation(teacherId);
    }

    @Override
    public Teacher getThirdInformation(Integer teacherId) {
        return teacherCenterMapper.getThirdInformation(teacherId);
    }

    @Override
    public TeacherLabel getTeacherLabel(Integer teacherId) {
        return teacherCenterMapper.getTeacherLabel(teacherId);
    }


    @Override
    public BankAccountResponseBean getBankAccountInfo(Integer teacherId) throws BaseServiceException {
        BankAccountResponseBean bankAccount = teacherCenterMapper.queryBankAccount(teacherId);
        String orginAddress = "";
        if(bankAccount!=null && bankAccount.getBanckCardPicture()!=null){
            orginAddress = bankAccount.getBanckCardPicture();
        }
        if (orginAddress != null && !"".equals(orginAddress)) {
            bankAccount.setBanckCardPicture(ossService.getURL(orginAddress).toString());
        }
        return bankAccount;
    }

    @Override
    public StudentCardResponseBean getStudentCardPicture(Integer teacherId) throws BaseServiceException {
        String address = teacherCenterMapper.queryStudentCard(teacherId);
        StudentCardResponseBean studentCard = new StudentCardResponseBean();
        if (!(address == null || "".equals(address))) {
            studentCard.setStudentCardPicture(ossService.getURL(address).toString());
        } else {
            studentCard.setStudentCardPicture("");
        }
        return studentCard;
    }
}
