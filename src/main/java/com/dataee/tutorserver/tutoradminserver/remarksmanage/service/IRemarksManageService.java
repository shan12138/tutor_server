package com.dataee.tutorserver.tutoradminserver.remarksmanage.service;

import com.dataee.tutorserver.tutoradminserver.remarksmanage.bean.ParentRecord;
import com.dataee.tutorserver.tutorpatriarchserver.bean.Remarks;
import com.dataee.tutorserver.tutorpatriarchserver.bean.RemarksListBean;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/6/8 23:36
 */
public interface IRemarksManageService {
    List<Remarks> getTeacherRecord(Integer id);

    void updateTeacherRecord(RemarksListBean remarksList);

    Integer changeTeacherRecordState(Integer lessonId);

    ParentRecord getParentRecord(Integer id);

    void updateParentRecord(ParentRecord parentRecord);

    void publishParentRecord(ParentRecord parentRecord);
}
