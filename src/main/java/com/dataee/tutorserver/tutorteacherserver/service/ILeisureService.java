package com.dataee.tutorserver.tutorteacherserver.service;

import com.dataee.tutorserver.entity.Leisure;

import java.util.List;

/**
 * @author JinYue
 * @CreateDate 2019/5/24 9:07
 */
public interface ILeisureService {
    /**
     * 获取教师的Id
     *
     * @param teacherId
     * @return
     */
    List<Leisure> getLeisureById(Integer teacherId);

    Integer getTeacherOfCourse(Integer courseId);
}
