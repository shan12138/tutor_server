package com.dataee.tutorserver.tutoradminserver.filemanage.service;

import com.dataee.tutorserver.tutoradminserver.filemanage.bean.CoursewareRequestBean;
import com.dataee.tutorserver.tutoradminserver.filemanage.bean.TeachingResourceRequestBean;

/**
 * @author JinYue
 * @CreateDate 2019/6/29 13:08
 */
public interface ISaveResourceManageService {

    /**
     * 保存教学资源
     *
     * @param teachingResource
     */
    void saveTeachingResource(TeachingResourceRequestBean teachingResource);

    /**
     * 保存课件
     *
     * @param courseware
     */
    void saveCourseware(CoursewareRequestBean courseware);
}
