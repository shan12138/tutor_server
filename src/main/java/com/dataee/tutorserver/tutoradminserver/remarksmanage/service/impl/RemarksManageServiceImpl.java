package com.dataee.tutorserver.tutoradminserver.remarksmanage.service.impl;

import com.dataee.tutorserver.entity.TeacherLabel;
import com.dataee.tutorserver.tutoradminserver.remarksmanage.bean.ParentRecord;
import com.dataee.tutorserver.tutoradminserver.remarksmanage.dao.RemarksManageMapper;
import com.dataee.tutorserver.tutoradminserver.remarksmanage.service.IRemarksManageService;
import com.dataee.tutorserver.tutorpatriarchserver.bean.Remarks;
import com.dataee.tutorserver.tutorpatriarchserver.bean.RemarksListBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/6/8 23:36
 */
@Service
public class RemarksManageServiceImpl implements IRemarksManageService {
    @Autowired
    private RemarksManageMapper remarksManageMapper;

    @Override
    public List<Remarks> getTeacherRecord(Integer lessonId) {
        return remarksManageMapper.getTeacherRecord(lessonId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTeacherRecord(RemarksListBean remarksList) {
        int lessonId = remarksList.getLessonId();
        for (Remarks remarks : remarksList.getRemarksList()) {
            remarksManageMapper.updateTeacherRecord(lessonId, remarks.getAnswer(), remarks.getQuestionId());
        }
    }

    @Override
    public Integer changeTeacherRecordState(Integer lessonId) {
        return remarksManageMapper.changeTeacherRecordState(lessonId);
    }

    @Override
    public ParentRecord getParentRecord(Integer lessonId) {
        ParentRecord parentRecord = new ParentRecord();
        List<Remarks> remarksList = remarksManageMapper.getParentRecord(lessonId);
        //获取家长对于教员的问题的回答
        parentRecord.setRemarksList(remarksList);
        //获取管理员给教员每堂课评分的标签的数据
        TeacherLabel label = remarksManageMapper.getLabel(lessonId);
        if (label != null)
            parentRecord.setTeacherLabel(label);
        return parentRecord;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateParentRecord(ParentRecord parentRecord) {
        int lessonId = parentRecord.getLessonId();
        for (Remarks remarks : parentRecord.getRemarksList()) {
            //修改问题的答案
            remarksManageMapper.updateParentRecord(lessonId, remarks.getQuestionId(), remarks.getAnswer());
        }
        TeacherLabel teacherLabel = parentRecord.getTeacherLabel();
        teacherLabel.setLessonId(lessonId);
        Integer lessonLabelId = remarksManageMapper.getLessonLabelId(lessonId);
        if (lessonLabelId == null) {
            remarksManageMapper.insertLessonLabel(teacherLabel);
        } else {
            //修改修改课程的教师的标签评分
            remarksManageMapper.updateLessonLabel(teacherLabel);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void publishParentRecord(ParentRecord parentRecord) {
        int lessonId = parentRecord.getLessonId();
        for (Remarks remarks : parentRecord.getRemarksList()) {
            //修改修改问题的答案
            remarksManageMapper.updateParentRecord(lessonId, remarks.getQuestionId(), remarks.getAnswer());
        }
        int teacherId = remarksManageMapper.getTeacherId(lessonId);
        TeacherLabel teacherLabel = parentRecord.getTeacherLabel();
        teacherLabel.setLessonId(lessonId);
        teacherLabel.setTeacherId(teacherId);
        Integer lessonLabelId = remarksManageMapper.getLessonLabelId(lessonId);
        if (lessonLabelId == null) {
            remarksManageMapper.insertLessonLabel(teacherLabel);
        } else {
            //修改修改课程的教师的标签评分
            remarksManageMapper.updateLessonLabel(teacherLabel);
        }
        Integer id = remarksManageMapper.getTeacherLabelId(teacherId);
        if (id == null) {
            //是第一次教员的评分，因此需要insert
            remarksManageMapper.insertTeacherLabel(teacherId);
        }
        //修改教师的总体标签评分
        remarksManageMapper.updateTeacherLabel(teacherLabel);
        remarksManageMapper.changeParentRecordState(lessonId);
    }
}
