package com.dataee.tutorserver.tutorteacherserver.service;

import com.dataee.tutorserver.entity.RemarkQuestion;
import com.dataee.tutorserver.tutorpatriarchserver.bean.RemarksListBean;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/6/8 23:11
 */
public interface ITeacherRemarksService {
    List<RemarkQuestion> getRemarkQuestions();

    void createTeacherRemarks(RemarksListBean remarksList);
}
