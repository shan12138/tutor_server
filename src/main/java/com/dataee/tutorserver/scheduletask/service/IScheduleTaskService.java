package com.dataee.tutorserver.scheduletask.service;

import com.dataee.tutorserver.entity.MessageInformation;
import com.dataee.tutorserver.scheduletask.bean.LessonBean;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/6/29 16:15
 */
public interface IScheduleTaskService {
    List<LessonBean> getAllNotReadResourceLessons();

    void insertIntoNewMessage(MessageInformation messageInformation);

    Integer getIsRead(Integer lessonId);
}
