package com.dataee.tutorserver.tutorteacherserver.service.impl;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.commons.commonservice.IOSSService;
import com.dataee.tutorserver.commons.commonservice.IZipService;
import com.dataee.tutorserver.commons.errorInfoenum.ServiceExceptionsEnum;
import com.dataee.tutorserver.entity.StudyResource;
import com.dataee.tutorserver.tutorteacherserver.bean.StudyCenterResponseBean;
import com.dataee.tutorserver.tutorteacherserver.dao.TeacherStudyMapper;
import com.dataee.tutorserver.tutorteacherserver.service.ITeacherStudyService;
import com.dataee.tutorserver.utils.PageInfoUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.bytebuddy.asm.Advice;
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
 * @CreateDate 2019/5/23 2:50
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class TeacherStudyServiceImpl implements ITeacherStudyService {
    private final Logger logger = LoggerFactory.getLogger(TeacherStudyServiceImpl.class);

    @Autowired
    private TeacherStudyMapper teacherStudyMapper;

    @Autowired
    private IOSSService ossService;

    @Autowired
    private IZipService zipService;


    @Override
    public NewPageInfo<StudyCenterResponseBean> getStudyResourceByPersonId(String classification, Integer personId, Page page) {
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<StudyCenterResponseBean> studyResourceList = teacherStudyMapper.queryStudyResourcesByPersonId(personId, classification);
        PageInfo pageInfo = new PageInfo(studyResourceList);
        NewPageInfo<StudyCenterResponseBean> newPageInfo = PageInfoUtil.read(pageInfo);
        return newPageInfo;
    }

    @Override
    public NewPageInfo getStudyResourceById(Integer studyResourceId, Page page) throws BaseServiceException {
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<String> addressList = teacherStudyMapper.queryStudyResourceById(studyResourceId);
        PageInfo pageInfo = new PageInfo(addressList);
        NewPageInfo newPageInfo = PageInfoUtil.read(pageInfo);
        List<String> urlList = ossService.getURLList(newPageInfo.getList());
        newPageInfo.setList(urlList);
        return newPageInfo;
    }


}
