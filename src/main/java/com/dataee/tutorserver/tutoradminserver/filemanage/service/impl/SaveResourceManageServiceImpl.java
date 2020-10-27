package com.dataee.tutorserver.tutoradminserver.filemanage.service.impl;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.commonservice.ISavePDFService;
import com.dataee.tutorserver.commons.exceptions.IllegalParameterException;
import com.dataee.tutorserver.tutoradminserver.filemanage.bean.CoursewareRequestBean;
import com.dataee.tutorserver.tutoradminserver.filemanage.bean.ResourceRequestBean;
import com.dataee.tutorserver.tutoradminserver.filemanage.bean.TeachingResourceRequestBean;
import com.dataee.tutorserver.tutoradminserver.filemanage.dao.CoursewareMapper;
import com.dataee.tutorserver.tutoradminserver.filemanage.service.ISaveResourceManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author JinYue
 * @CreateDate 2019/6/29 13:27
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SaveResourceManageServiceImpl implements ISaveResourceManageService {
    private Logger logger = LoggerFactory.getLogger(SaveResourceManageServiceImpl.class);

    @Autowired
    @Qualifier("saveResourceService")
    private ISavePDFService<ResourceRequestBean> savePDFService;
    @Autowired
    private CoursewareMapper coursewareMapper;

    @Async
    @Override
    public void saveCourseware(CoursewareRequestBean courseware) {
        try {
            //查重
            coursewareMapper.deleteCoursewareByLessonId(courseware.getLessonId());
            Integer coursewareId = savePDFService.saveFile(courseware);
            //    保存课程主键 lesson_id
            coursewareMapper.updateLessonIdByCoursewareId(coursewareId, courseware.getLessonId());
        } catch (BaseServiceException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        } catch (IllegalParameterException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }

    }

    @Async
    @Override
    public void saveTeachingResource(TeachingResourceRequestBean teachingResource) {
        try {
            savePDFService.saveFile(teachingResource);
        } catch (BaseServiceException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        } catch (IllegalParameterException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }
}
