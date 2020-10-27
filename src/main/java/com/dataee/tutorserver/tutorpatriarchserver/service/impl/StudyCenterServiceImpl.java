package com.dataee.tutorserver.tutorpatriarchserver.service.impl;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.commons.commonservice.IOSSService;
import com.dataee.tutorserver.commons.errorInfoenum.ServiceExceptionsEnum;
import com.dataee.tutorserver.tutorpatriarchserver.dao.StudyCenterMapper;
import com.dataee.tutorserver.tutorpatriarchserver.service.IStudyCenterService;
import com.dataee.tutorserver.tutorteacherserver.bean.StudyCenterResponseBean;
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
 * @CreateDate 2019/6/10 23:41
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class StudyCenterServiceImpl implements IStudyCenterService {
    private final Logger logger = LoggerFactory.getLogger(StudyCenterServiceImpl.class);
    @Autowired
    private IOSSService ossService;

    @Autowired
    private StudyCenterMapper studyCenterMapper;


    @Override
    public NewPageInfo<StudyCenterResponseBean> getTeachingResourceByKeyword(Integer parentId, String keyword, Page page) {
        PageHelper.startPage(page.getPage(), page.getLimit());
        //获得所有的courseId
        List<StudyCenterResponseBean> teachingResourceList = studyCenterMapper.getTeachingResourceListByIdAndKeyword(parentId, keyword);
        PageInfo pageInfo = new PageInfo(teachingResourceList);
        NewPageInfo<StudyCenterResponseBean> newPageInfo = PageInfoUtil.read(pageInfo);
        return newPageInfo;
    }

    @Override
    public NewPageInfo<String> getTeachingResourceImageById(Integer id, Page page) throws BaseServiceException {
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<String> teachingResourceImageAddressList = studyCenterMapper.queryTeachingResourceImageById(id);
        PageInfo pageInfo = new PageInfo(teachingResourceImageAddressList);
        NewPageInfo<String> newPageInfo = PageInfoUtil.read(pageInfo);
        List<String> urlList = ossService.getURLList(newPageInfo.getList());
        newPageInfo.setList(urlList);
        return newPageInfo;
    }

    @Override
    public String downloadCourseware(Integer id) throws BaseServiceException {
        //获取压缩包名称
        String fileName = studyCenterMapper.getCoursewareAddressName(id);
        //获取到课件图片
       // List<String> imageList = teacherCoursewareMapper.getCoursewareAddressName(coursewareId);
        if (fileName == null || fileName == "") {
            throw new BaseServiceException(ServiceExceptionsEnum.FILE_NOT_EXIT);
        }
        //生成连接地址
        URL url = ossService.getURL(fileName);
        return url.toString();
    }
}
