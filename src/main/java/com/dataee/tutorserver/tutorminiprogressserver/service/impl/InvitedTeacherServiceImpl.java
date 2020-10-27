package com.dataee.tutorserver.tutorminiprogressserver.service.impl;

import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.entity.Lesson;
import com.dataee.tutorserver.entity.Partner;
import com.dataee.tutorserver.entity.Teacher;
import com.dataee.tutorserver.tutorminiprogressserver.bean.InvitedTeacherCountAndMoney;
import com.dataee.tutorserver.tutorminiprogressserver.dao.InvitedTeacherMapper;
import com.dataee.tutorserver.tutorminiprogressserver.service.InvitedTeacherService;
import com.dataee.tutorserver.tutorteacherserver.bean.TeacherInvite;
import com.dataee.tutorserver.utils.PageInfoUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvitedTeacherServiceImpl implements InvitedTeacherService {
    @Autowired
    private InvitedTeacherMapper invitedTeacherMapper;
    @Override
    public NewPageInfo<TeacherInvite> getTeachersById(Integer partnerId, Page page) {
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<TeacherInvite> teacherInvites = invitedTeacherMapper.getTeachersById(partnerId);
        PageInfo pageInfo = new PageInfo(teacherInvites);
        NewPageInfo newPageInfo = PageInfoUtil.read(pageInfo);
        return newPageInfo;
    }

    @Override
    public Partner getPartnerByPartnerId(Integer partnerId) {
        return invitedTeacherMapper.getPartnerByPartnerId(partnerId);
    }

    @Override
    public InvitedTeacherCountAndMoney teacherCountAndMoney(Integer partnerId) {
        List<Teacher> teachers = invitedTeacherMapper.getTeachersByPartnerId(partnerId);
        double totalMoney =0.0;
        for (Teacher teacher:teachers){
            List<Lesson> lessons = invitedTeacherMapper.getLessonsByTeacherId(teacher.getTeacherId());
            if(lessons.size()>0 && lessons.get(0).getCheckInTime()!=null){
                Integer maxTeacherLevel = invitedTeacherMapper.getMaxTeacherLevel();
                if(teacher.getTeacherLevel()>maxTeacherLevel){
                    teacher.setTeacherLevel(maxTeacherLevel);
                }
                Integer money = invitedTeacherMapper.getTeacherLevelByLevel(teacher.getTeacherLevel());
                totalMoney+=  money;
            }
        }
        Integer countByPartnerId = invitedTeacherMapper.getCountByPartnerId(partnerId);
        InvitedTeacherCountAndMoney invitedTeacherCountAndMoney =new InvitedTeacherCountAndMoney();
        invitedTeacherCountAndMoney.setCount(countByPartnerId);
        invitedTeacherCountAndMoney.setMoney(totalMoney);
        return invitedTeacherCountAndMoney;
    }

    @Override
    public Integer getPartnerId(Integer id) {
        return invitedTeacherMapper.getPartnerId(id);
    }
}
