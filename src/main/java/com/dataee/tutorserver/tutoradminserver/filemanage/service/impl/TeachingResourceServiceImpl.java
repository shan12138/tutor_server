package com.dataee.tutorserver.tutoradminserver.filemanage.service.impl;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.commons.commonservice.IOSSService;
import com.dataee.tutorserver.commons.exceptions.SQLOperationException;
import com.dataee.tutorserver.tutoradminserver.filemanage.bean.ResourceListRequestBean;
import com.dataee.tutorserver.tutoradminserver.filemanage.dao.TeachingResourceMapper;
import com.dataee.tutorserver.tutoradminserver.filemanage.service.ITeachingResourceService;
import com.dataee.tutorserver.utils.PageInfoUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author JinYue
 * @CreateDate 2019/6/10 20:56
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TeachingResourceServiceImpl implements ITeachingResourceService {
    private Logger logger = LoggerFactory.getLogger(TeachingResourceServiceImpl.class);
    @Autowired
    private TeachingResourceMapper teachingResourceMapper;

    @Autowired
    private IOSSService ossService;

    @Override
    public NewPageInfo getTeachingResourceList(Page page,String studentName,String teacher,String courseName,String type,String headTeacher,String courseAdmin) {
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<ResourceListRequestBean> teachingResourceList = teachingResourceMapper.queryTeachingResources( studentName, teacher, courseName, type, headTeacher, courseAdmin);
        if (teachingResourceList == null) {
            teachingResourceList = new ArrayList<>();
        }
        PageInfo pageInfo = new PageInfo(teachingResourceList);
        NewPageInfo newPageInfo = PageInfoUtil.read(pageInfo);
        return newPageInfo;
    }


    @Override
    public void deleteTeachingResourceById(Integer id) throws BaseServiceException {
        String address = teachingResourceMapper.selectResourceById(id);
        if (address == null || "".equals(address)) {
            throw new IllegalArgumentException("当前的文件不存在");
        }
        ossService.deleteFile(address);
        int count = teachingResourceMapper.deleteResourceByResourceId(id);
        if (count != 1) {
            throw new SQLOperationException();
        }
    }

    @Override
    public String getTeachingResourceAddressById(Integer id) throws BaseServiceException {
        String address = teachingResourceMapper.selectResourceById(id);
        URL url = ossService.getURL(address);
        return url.toString();
    }
}
