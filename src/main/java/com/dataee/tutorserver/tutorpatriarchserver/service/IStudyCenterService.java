package com.dataee.tutorserver.tutorpatriarchserver.service;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.tutorteacherserver.bean.StudyCenterResponseBean;

/**
 * @author JinYue
 * @CreateDate 2019/6/10 23:40
 */
public interface IStudyCenterService {
    /**
     * 获取课件的列表
     *
     * @param parentId
     * @param keyword
     * @return
     */
    NewPageInfo<StudyCenterResponseBean> getTeachingResourceByKeyword(Integer parentId, String keyword, Page page);


    /**
     * 获取课件的图片列表
     *
     * @param id
     * @return
     */
    NewPageInfo<String> getTeachingResourceImageById(Integer id, Page page) throws BaseServiceException;

    String downloadCourseware(Integer id)throws BaseServiceException;
}
