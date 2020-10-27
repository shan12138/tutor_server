package com.dataee.tutorserver.tutorteacherserver.service;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.tutorteacherserver.bean.TeacherCoursewareResponseBean;

/**
 * @author JinYue
 * @CreateDate 2019/6/29 15:04
 */
public interface ITeacherCoursewareService {
    /**
     * 通过lessonId和teacherId查找
     *
     * @param lessonId
     * @return
     */
    TeacherCoursewareResponseBean getCoursewareInfo(int lessonId, Page page) throws BaseServiceException;

    /**
     * 通过资源id标记已读文件
     *
     * @param coursewareId
     */
    void read(int coursewareId);

    String downloadCourseware(Integer coursewareId) throws BaseServiceException;
}
