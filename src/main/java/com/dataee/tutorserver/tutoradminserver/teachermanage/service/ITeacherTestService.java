package com.dataee.tutorserver.tutoradminserver.teachermanage.service;


import java.util.List;

/**
 * @author JinYue
 * @CreateDate 2019/6/16 12:08
 */
public interface ITeacherTestService {
    /**
     * 完成错题的导入
     *
     * @param teacherTestList
     * @return
     */
    void saveTeacherTests(List<Object> teacherTestList);
}
