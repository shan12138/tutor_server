package com.dataee.tutorserver.scheduletask.service.impl;

import com.dataee.tutorserver.entity.MessageInformation;
import com.dataee.tutorserver.scheduletask.bean.LessonBean;
import com.dataee.tutorserver.scheduletask.dao.ScheduleTaskMapper;
import com.dataee.tutorserver.scheduletask.service.IScheduleTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/6/29 16:14
 */
@Service
public class ScheduleTaskServiceImpl implements IScheduleTaskService {
    @Autowired
    private ScheduleTaskMapper scheduleTaskMapper;

    @Override
    public List<LessonBean> getAllNotReadResourceLessons() {
        return scheduleTaskMapper.getAllNotReadResourceLessons();
    }

    @Override
    public void insertIntoNewMessage(MessageInformation messageInformation) {
        scheduleTaskMapper.addToLessonMessageTable(messageInformation);
    }

    @Override
    public Integer getIsRead(Integer lessonId) {
        return scheduleTaskMapper.getIsRead(lessonId);
    }
}
