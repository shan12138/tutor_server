package com.dataee.tutorserver.tutorpatriarchserver.service;

import com.dataee.tutorserver.entity.Teacher;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/6 22:12
 */
public interface IParentTeacherService {
    Teacher getTeacherDetailInfo(String teacherId);
}
