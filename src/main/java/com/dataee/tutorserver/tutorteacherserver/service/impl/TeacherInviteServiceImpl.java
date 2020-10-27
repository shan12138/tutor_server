package com.dataee.tutorserver.tutorteacherserver.service.impl;

import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.commons.errorInfoenum.ServiceExceptionsEnum;
import com.dataee.tutorserver.entity.Course;
import com.dataee.tutorserver.entity.Partner;
import com.dataee.tutorserver.entity.Teacher;
import com.dataee.tutorserver.entity.TeacherInvitation;
import com.dataee.tutorserver.tutorminiprogressserver.dao.InvitedTeacherMapper;
import com.dataee.tutorserver.tutorminiprogressserver.service.InvitedTeacherService;
import com.dataee.tutorserver.tutorteacherserver.bean.TeacherInvite;
import com.dataee.tutorserver.tutorteacherserver.bean.TeacherInviteCount;
import com.dataee.tutorserver.tutorteacherserver.dao.TeacherInviteMapper;
import com.dataee.tutorserver.tutorteacherserver.service.ITeacherInviteService;
import com.dataee.tutorserver.utils.PageInfoUtil;
import com.dataee.tutorserver.utils.SecurityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TeacherInviteServiceImpl implements ITeacherInviteService {
    @Autowired
    private TeacherInviteMapper teacherInviteMapper;

    @Autowired
    private InvitedTeacherMapper invitedTeacherMapper;

    @Override
    public Teacher getTeacherByTeacherId(Integer teacherId) {
        return teacherInviteMapper.getTeacherByTeacherId(teacherId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertTeacherInvite(TeacherInvitation teacherInvitation) throws BaseServiceException, BaseControllerException {
        String telephone = teacherInvitation.getTelephone();
        int count= teacherInviteMapper.getTeacherById(telephone);
        if(count>0){
          throw new BaseServiceException(ServiceExceptionsEnum.INVITE_TEACHER_EXIST);
        }else {
             //根据手机号去查家长的信息
            Teacher teacher = teacherInviteMapper.getFormTeacherById(telephone);
            if(teacher!=null){
                List<Course> courseList = teacherInviteMapper.getCourseList(teacher.getTeacherId());
                if(!courseList.isEmpty()){
                    throw new BaseServiceException(ServiceExceptionsEnum.FORM_TEACHER_EXIST);
                }
            }
            Integer personId = SecurityUtil.getPersonId();
            Integer partnerId = invitedTeacherMapper.getPartnerId(personId);
            Partner partner = invitedTeacherMapper.getPartnerByPartnerId(partnerId);
            if(teacher!=null){
                teacherInvitation.setInvitedTeacher(teacher);
                teacherInvitation.setPartner(partner);
                teacherInvitation.setPartnerCode(partner.getInviteCode());
                if(teacher.getState()==1){
                    teacherInvitation.setStatus("尚未申请");
                }else if(teacher.getState()==2){
                    teacherInvitation.setStatus("申请中");
                }else if(teacher.getState()==3){
                    teacherInvitation.setStatus("已拒绝");
                }else if(teacher.getState()==4){
                    teacherInvitation.setStatus("已转正");
                }
                teacherInviteMapper.insertTeacherInvite(teacherInvitation);
                //把合伙人邀请的信息添加到表
                teacherInviteMapper.editTeacherInviteInfo(null,1,partner.getPartnerId(),telephone);
            }
            //没有注册
            else{
                throw new BaseServiceException(ServiceExceptionsEnum.NO_REGISTER);
            }
        }
    }

    @Override
    public NewPageInfo<TeacherInvite> getTeachersById(Integer teacherId, Page page) {
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<TeacherInvite> teacherInvites = teacherInviteMapper.getTeachersById(teacherId);
        PageInfo pageInfo = new PageInfo(teacherInvites);
        NewPageInfo newPageInfo = PageInfoUtil.read(pageInfo);
        return newPageInfo;

    }

    @Override
    public TeacherInviteCount getInvitedCount(Integer teacherId) {
        TeacherInviteCount inviteList =new TeacherInviteCount();
        Integer inviteTeacherCount = teacherInviteMapper.getInviteTeacherCountById(teacherId);
        Integer inviteSuccessTeacherCount = teacherInviteMapper.getInviteSuccessTeacherCountById(teacherId);
        inviteList.setInvitedCount(inviteTeacherCount);
        inviteList.setInvitedSuccessCount(inviteSuccessTeacherCount);
        return inviteList;
    }

    @Override
    public void teacherInviteTeacher(TeacherInvitation teacherInvitation, Integer personId)throws BaseServiceException {
        Teacher currTeacher = teacherInviteMapper.getTeacherByTeacherId(personId);
        String telephone = teacherInvitation.getTelephone();
        int count= teacherInviteMapper.getTeacherById(telephone);
        if(count>0){
            throw new BaseServiceException(ServiceExceptionsEnum.INVITE_TEACHER_EXIST);
        }else {
            //根据手机号去查老师的信息
            Teacher teacher = teacherInviteMapper.getFormTeacherById(telephone);
            if(teacher!=null){
                List<Course> courseList = teacherInviteMapper.getCourseList(teacher.getTeacherId());
                if(!courseList.isEmpty()){
                    throw new BaseServiceException(ServiceExceptionsEnum.FORM_TEACHER_EXIST);
                }
            }
            if(teacher!=null){
                //这个老师没有被邀请，就不能邀请别人
//                if(currTeacher.getPartner()==null){
//                    throw new BaseServiceException(ServiceExceptionsEnum.NO_INVITED);
//                }
                teacherInvitation.setInvitedTeacher(teacher);
                teacherInvitation.setTeacher(currTeacher);
                teacherInvitation.setTeacherInvitationCode(currTeacher.getInviteCode());
                if(teacher.getState()==1){
                    teacherInvitation.setStatus("尚未申请");
                }else if(teacher.getState()==2){
                    teacherInvitation.setStatus("申请中");
                }else if(teacher.getState()==3){
                    teacherInvitation.setStatus("已拒绝");
                }else if(teacher.getState()==4){
                    teacherInvitation.setStatus("已转正");
                }
                teacherInviteMapper.insertTeacherInvite(teacherInvitation);
                //把合伙人邀请的信息添加到表
                Integer teacherLevel = currTeacher.getTeacherLevel();
                Integer invitedTeacherId = currTeacher.getTeacherId();
                //被其他老师和合伙人邀请的
                if(teacherLevel!=null){
                    Integer partnerId  = currTeacher.getPartner().getPartnerId();
                    teacherInviteMapper.editTeacherInviteInfo(invitedTeacherId,teacherLevel+1,partnerId,telephone);
                }else {
                    teacherInviteMapper.editTeacherInviteInfo(invitedTeacherId,null,null,telephone);
                }

            }else{
                throw new BaseServiceException(ServiceExceptionsEnum.NO_REGISTER);
            }
        }
    }
}
