package com.dataee.tutorserver.tutorpatriarchserver.service.impl;

import com.dataee.tutorserver.entity.RemarkQuestion;
import com.dataee.tutorserver.tutorpatriarchserver.bean.Remarks;
import com.dataee.tutorserver.tutorpatriarchserver.bean.RemarksListBean;
import com.dataee.tutorserver.tutorpatriarchserver.dao.ParentRemarksMapper;
import com.dataee.tutorserver.tutorpatriarchserver.service.IParentRemarksService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author JinYue
 * @CreateDate 2019/6/3 20:15
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ParentRemarksServiceImpl implements IParentRemarksService {
    private final Logger logger = LoggerFactory.getLogger(ParentRemarksServiceImpl.class);
    @Autowired
    private ParentRemarksMapper parentRemarksMapper;

    @Override
    public List<RemarkQuestion> getRemarkQuestions() {
        return parentRemarksMapper.getRemarkQuestions();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createParentRemarks(RemarksListBean remarksList) {
        int lessonId = remarksList.getLessonId();
        for (Remarks remarks : remarksList.getRemarksList()) {
            parentRemarksMapper.insertNewRemarks(lessonId, remarks.getQuestionId(), remarks.getAnswer());
        }
        parentRemarksMapper.changeRecordState(lessonId);
        parentRemarksMapper.insertLessonLabel(lessonId);
    }
}
