package com.dataee.tutorserver.tutorpatriarchserver.service;

import com.dataee.tutorserver.entity.RemarkQuestion;
import com.dataee.tutorserver.tutorpatriarchserver.bean.RemarksListBean;

import java.util.List;

/**
 * @author JinYue
 * @CreateDate 2019/6/3 20:12
 */
public interface IParentRemarksService {
    List<RemarkQuestion> getRemarkQuestions();

    void createParentRemarks(RemarksListBean remarksList);
}
