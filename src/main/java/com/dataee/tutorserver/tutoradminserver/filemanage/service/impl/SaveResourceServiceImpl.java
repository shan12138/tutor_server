package com.dataee.tutorserver.tutoradminserver.filemanage.service.impl;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.commonservice.IPDFConvertToImageService;
import com.dataee.tutorserver.commons.commonservice.ISavePDFService;
import com.dataee.tutorserver.commons.errorInfoenum.ServiceExceptionsEnum;
import com.dataee.tutorserver.commons.exceptions.IllegalParameterException;
import com.dataee.tutorserver.commons.exceptions.SQLOperationException;
import com.dataee.tutorserver.entity.MessageInformation;
import com.dataee.tutorserver.tutoradminserver.coursemanage.dao.CourseMngMapper;
import com.dataee.tutorserver.tutoradminserver.filemanage.bean.ResourceRequestBean;
import com.dataee.tutorserver.tutoradminserver.filemanage.dao.ResourceMapper;
import com.dataee.tutorserver.tutoradminserver.messagemanage.dao.MessageManageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author JinYue
 * @CreateDate 2019/6/9 23:43
 */
@Service("saveResourceService")
@Transactional(rollbackFor = Exception.class)
public class SaveResourceServiceImpl<T extends ResourceRequestBean> implements ISavePDFService<T> {
    private Logger logger = LoggerFactory.getLogger(SaveResourceServiceImpl.class);
    @Autowired
    private MessageManageMapper messageManageMapper;
    @Autowired
    private CourseMngMapper courseMngMapper;
    @Autowired
    private ResourceMapper resourceMapper;
    @Autowired
    private IPDFConvertToImageService pdfConvertToImageService;

    @Override
    public Integer saveFile(T file) throws BaseServiceException, IllegalParameterException {
        Integer resourceId = saveFilePDF(file);
        List<String> paths = pdfConvertToImageService.pdfRendering(file.getPdfAddress());
        if (resourceId != null && paths != null) {
            saveFileImage(resourceId, paths);
        } else {
            logger.error("saveCourseService:{}", "课件存储异常！");
            throw new BaseServiceException(ServiceExceptionsEnum.OBJECT_OPERATE_EXCEPTION);
        }
        return resourceId;
    }

    @Override
    public Integer saveFilePDF(T fileInfo) throws BaseServiceException {
        //保存一个状态,查重
        int count = resourceMapper.insertResource(fileInfo);
        if (count == 1) {
            return fileInfo.getResourceId();
        } else {
            throw new SQLOperationException();
        }
    }

    @Override
    public void saveFileImage(Integer pdfId, List<String> paths) throws SQLOperationException {
        int size = paths.size();
        int count = resourceMapper.insertResourceImage(pdfId, paths);
        if (size != count) {
            logger.info("saveContractImage: {}", "数据插入异常，预计插入量 " + size + "实际插入量 " + count);
            throw new SQLOperationException();
        }
        //    修改PDF的状态
        resourceMapper.updateResourceUploadState(pdfId);
    }
}
