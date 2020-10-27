package com.dataee.tutorserver.tutorteacherserver.service.impl;

import com.dataee.tutorserver.entity.RemarkQuestion;
import com.dataee.tutorserver.tutorpatriarchserver.bean.Remarks;
import com.dataee.tutorserver.tutorpatriarchserver.bean.RemarksListBean;
import com.dataee.tutorserver.tutorteacherserver.dao.TeacherRemarksMapper;
import com.dataee.tutorserver.tutorteacherserver.service.ITeacherRemarksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/6/8 23:11
 */
@Service
public class TeacherRemarksServiceImpl implements ITeacherRemarksService {
    @Autowired
    private TeacherRemarksMapper teacherRemarksMapper;

    @Override
    public List<RemarkQuestion> getRemarkQuestions() {
        return teacherRemarksMapper.getRemarkQuestions();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createTeacherRemarks(RemarksListBean remarksList) {
        int lessonId = remarksList.getLessonId();
        teacherRemarksMapper.changeRecordState(lessonId);
        for (Remarks remarks : remarksList.getRemarksList()) {
            teacherRemarksMapper.insertNewRemarks(lessonId, remarks.getQuestionId(), remarks.getAnswer());
        }
    }
}
