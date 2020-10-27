package com.dataee.tutorserver.tutorpatriarchserver.service.impl;

import com.dataee.tutorserver.entity.Teacher;
import com.dataee.tutorserver.tutorpatriarchserver.dao.ParentTeacherMapper;
import com.dataee.tutorserver.tutorpatriarchserver.service.IParentTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/6 22:12
 */
@Service
public class ParentTeacherServiceImpl implements IParentTeacherService {
    @Autowired
    private ParentTeacherMapper parentTeacherMapper;

    @Override
    public Teacher getTeacherDetailInfo(String teacherId) {
        return parentTeacherMapper.getTeacherDetailInfo(teacherId);
    }
}
