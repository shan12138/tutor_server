package com.dataee.tutorserver.tutorteacherserver.service;

import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.entity.Student;
import com.dataee.tutorserver.tutorteacherserver.bean.MyStudentInfoResponseBean;

import java.util.List;

/**
 * @author JinYue
 * @CreateDate 2019/5/16 11:47
 */
public interface IMyStudentsService {
    /**
     * 查询教师的学生列表
     *
     * @param teacherId
     * @return
     */
    NewPageInfo<Student> getMyStudentsList(Integer teacherId, Page page);

    /**
     * 获取指定学生的详细信息
     *
     * @param teacherId
     * @param studentId
     * @return
     */
    MyStudentInfoResponseBean getMyStudentInfoById(Integer teacherId, Integer studentId);

}
