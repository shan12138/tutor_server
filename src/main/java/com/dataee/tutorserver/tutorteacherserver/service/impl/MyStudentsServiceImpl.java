package com.dataee.tutorserver.tutorteacherserver.service.impl;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.entity.Student;
import com.dataee.tutorserver.tutorteacherserver.bean.MyStudentInfoResponseBean;
import com.dataee.tutorserver.tutorteacherserver.dao.MyStudentsMapper;
import com.dataee.tutorserver.tutorteacherserver.service.IMyStudentsService;
import com.dataee.tutorserver.utils.PageInfoUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author JinYue
 * @CreateDate 2019/5/16 12:00
 */
@Service
@Transactional(rollbackFor = {Exception.class, BaseServiceException.class}, readOnly = true)
public class MyStudentsServiceImpl implements IMyStudentsService {
    private final Logger logger = LoggerFactory.getLogger(MyStudentsServiceImpl.class);
    @Autowired
    private MyStudentsMapper myStudentsMapper;

    @Override
    public NewPageInfo<Student> getMyStudentsList(Integer teacherId, Page page) {
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<Student> students = myStudentsMapper.queryStudentsByTeacherId(teacherId);
        PageInfo pageInfo = new PageInfo(students);
        NewPageInfo<Student> newPageInfo = PageInfoUtil.read(pageInfo);
        return newPageInfo;
    }

    @Override
    public MyStudentInfoResponseBean getMyStudentInfoById(Integer teacherId, Integer studentId) {
        MyStudentInfoResponseBean student = myStudentsMapper.queryStudentByStudentId(studentId);
        student.setCourseInfoList(myStudentsMapper.queryCourseInfo(teacherId, studentId));
        return student;
    }
}
