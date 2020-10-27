package com.dataee.tutorserver.tutorteacherserver.service.impl;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.commons.commonservice.IOSSService;
import com.dataee.tutorserver.commons.errorInfoenum.ServiceExceptionsEnum;
import com.dataee.tutorserver.tutorteacherserver.bean.TeacherCoursewareResponseBean;
import com.dataee.tutorserver.tutorteacherserver.dao.TeacherCoursewareMapper;
import com.dataee.tutorserver.tutorteacherserver.service.ITeacherCoursewareService;
import com.dataee.tutorserver.utils.PageInfoUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;
import java.util.List;

/**
 * @author JinYue
 * @CreateDate 2019/6/29 15:08
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TeacherCoursewareServiceImpl implements ITeacherCoursewareService {
    private Logger logger = LoggerFactory.getLogger(TeacherCoursewareServiceImpl.class);
    @Autowired
    private IOSSService ossService;
    @Autowired
    private TeacherCoursewareMapper teacherCoursewareMapper;


    /**
     * 增加判断条件‘教学计划’
     *
     * @param lessonId
     * @param page
     * @return
     * @throws BaseServiceException
     */
    @Override
    public TeacherCoursewareResponseBean getCoursewareInfo(int lessonId, Page page) throws BaseServiceException {
        //获取课件id
        Integer coursewareId = teacherCoursewareMapper.getCoursewareId(lessonId);
        if (coursewareId == null) {
            throw new BaseServiceException(ServiceExceptionsEnum.FILE_NOT_EXIT);
        }
        //获取到课件名
        String coursewareName = teacherCoursewareMapper.getCoursewareName(coursewareId);
        //获取到图片并转换成地址
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<String> imageList = teacherCoursewareMapper.getCoursewareImage(coursewareId);
        PageInfo<String> pageInfo = new PageInfo<>(imageList);
        NewPageInfo<String> newPageInfo = PageInfoUtil.read(pageInfo);
        List<String> urlList = ossService.getURLList(newPageInfo.getList());
        newPageInfo.setList(urlList);
        TeacherCoursewareResponseBean teacherCourseware = new TeacherCoursewareResponseBean(coursewareId, coursewareName, newPageInfo);
        return teacherCourseware;
    }

    @Override
    public void read(int coursewareId) {
        teacherCoursewareMapper.updateReadByCoursewareId(coursewareId);
    }

    @Override
    public String downloadCourseware(Integer coursewareId)throws BaseServiceException {
        //获取压缩包名称
        String fileName = teacherCoursewareMapper.getCoursewareAddressName(coursewareId);
        if (fileName == null || fileName == "") {
            throw new BaseServiceException(ServiceExceptionsEnum.FILE_NOT_EXIT);
        }
        //生成连接地址
        URL url = ossService.getURL(fileName);
        return url.toString();
    }
}
