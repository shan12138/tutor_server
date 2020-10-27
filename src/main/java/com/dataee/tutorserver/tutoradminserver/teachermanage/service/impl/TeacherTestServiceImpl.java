package com.dataee.tutorserver.tutoradminserver.teachermanage.service.impl;

import com.dataee.tutorserver.tutoradminserver.teachermanage.bean.TeacherTestExcelBean;
import com.dataee.tutorserver.tutoradminserver.teachermanage.dao.TeacherTestMapper;
import com.dataee.tutorserver.tutoradminserver.teachermanage.service.ITeacherTestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JinYue
 * @CreateDate 2019/6/16 12:09
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TeacherTestServiceImpl implements ITeacherTestService {
    private Logger logger = LoggerFactory.getLogger(TeacherTestServiceImpl.class);

    @Autowired
    private TeacherTestMapper teacherTestMapper;


    @Async
    @Override
    public void saveTeacherTests(List<Object> teacherTestList) {
        List<TeacherTestExcelBean> teacherTests = listConvertToObject(teacherTestList);
        int count = teacherTestMapper.insertTeacherTest(teacherTests);
        logger.info("导入教师测试题共：{}", count);
    }

    public List<TeacherTestExcelBean> listConvertToObject(List<Object> teacherTestList) {
        List<TeacherTestExcelBean> teacherTests = new ArrayList<>();
        for (Object object : teacherTestList) {
            if (object instanceof TeacherTestExcelBean) {
                teacherTests.add((TeacherTestExcelBean) object);
            }
        }
        return teacherTests;
    }
}
