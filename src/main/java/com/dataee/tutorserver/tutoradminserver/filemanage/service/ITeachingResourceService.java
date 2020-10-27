package com.dataee.tutorserver.tutoradminserver.filemanage.service;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;

/**
 * @author JinYue
 * @CreateDate 2019/6/10 20:50
 */
public interface ITeachingResourceService {
    /**
     * 获取资源文件列表
     *
     * @param page
     * @return
     */
    NewPageInfo getTeachingResourceList(Page page,String studentName,String teacher,String courseName,String type,String headTeacher,String courseAdmin );

    /**
     * 删除指定的资源文件
     *
     * @param id
     */
    void deleteTeachingResourceById(Integer id) throws BaseServiceException;


    /**
     * 获取文件地址
     *
     * @param id
     * @return
     */
    String getTeachingResourceAddressById(Integer id) throws BaseServiceException;
}
