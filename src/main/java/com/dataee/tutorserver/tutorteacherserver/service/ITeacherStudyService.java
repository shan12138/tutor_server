package com.dataee.tutorserver.tutorteacherserver.service;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.entity.StudyResource;
import com.dataee.tutorserver.tutorteacherserver.bean.StudyCenterResponseBean;

import java.util.List;

/**
 * @author JinYue
 * @CreateDate 2019/5/23 2:50
 */
public interface ITeacherStudyService {
    /**
     * 获取资源列表
     *
     * @param classification
     * @param personId
     * @return
     */
    NewPageInfo<StudyCenterResponseBean> getStudyResourceByPersonId(String classification, Integer personId, Page page);

    NewPageInfo getStudyResourceById(Integer studyResourceId, Page page) throws BaseServiceException;

}
