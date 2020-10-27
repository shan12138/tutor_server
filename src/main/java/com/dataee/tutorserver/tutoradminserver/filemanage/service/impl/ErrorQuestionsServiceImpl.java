package com.dataee.tutorserver.tutoradminserver.filemanage.service.impl;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.commons.commonservice.IOSSService;
import com.dataee.tutorserver.commons.commonservice.IZipService;
import com.dataee.tutorserver.commons.errorInfoenum.ServiceExceptionsEnum;
import com.dataee.tutorserver.tutoradminserver.filemanage.bean.ErrorQuestionResponseBean;
import com.dataee.tutorserver.tutoradminserver.filemanage.dao.ErrorQuestionsMapper;
import com.dataee.tutorserver.tutoradminserver.filemanage.service.IErrorQuestionsService;
import com.dataee.tutorserver.userserver.dao.ErrorQuestionMapper;
import com.dataee.tutorserver.utils.PageInfoUtil;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;
import java.util.List;

/**
 * @author JinYue
 * @CreateDate 2019/5/14 21:59
 */
@Service
@Transactional(rollbackFor = BaseServiceException.class)
public class ErrorQuestionsServiceImpl implements IErrorQuestionsService {
    private final Logger logger = LoggerFactory.getLogger(ErrorQuestionsServiceImpl.class);

    @Autowired
    private ErrorQuestionsMapper errorQuestionsMapper;
    @Autowired
    private ErrorQuestionMapper errorQuestionMapper;
    @Autowired
    private IOSSService ossService;
    @Autowired
    private IZipService zipService;


    @Override
    public NewPageInfo<ErrorQuestionResponseBean> getAllErrorQuestions(Page page,
                                                                       String studentName,
                                                                       String queryCondition,
                                                                       Integer lessonNumber,
                                                                       String essentialContent
                                                                       ) {
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<ErrorQuestionResponseBean> errorQuestionList = errorQuestionsMapper.queryErrorQuestionsList(studentName,queryCondition,lessonNumber,essentialContent);
        System.out.printf("Error question list: %d\n", errorQuestionList.size());
        NewPageInfo newPageInfo = PageInfoUtil.read(errorQuestionList);
        return newPageInfo;
    }

    @Override
    public ErrorQuestionResponseBean getErrorQuestion(int id) throws BaseServiceException {
        ErrorQuestionResponseBean errorQuestion = errorQuestionsMapper.queryErrorQuestionById(id);
        if (errorQuestion != null) {
            List<String> urlList = ossService.getURLList(errorQuestion.getQuestionPicture());
            errorQuestion.setQuestionPicture(urlList);
        } else {
            errorQuestion = new ErrorQuestionResponseBean();
        }
        return errorQuestion;
    }

    @Override
    public void printed(int id, boolean isPrint) {
        errorQuestionsMapper.updatePrintStatus(id, isPrint);
    }

    @Override
    public void coursed(int id, boolean isCourse) {
        errorQuestionsMapper.updateCourseStatus(id, isCourse);
    }

    @Override
    public String downloadErrorQuestionPackage(Integer errorquestionId) throws BaseServiceException {
        //获取压缩包名称
        String fileName = errorQuestionsMapper.queryErrorQuestionPackageName(errorquestionId);
        //获取到错题图片
        List<String> imageList = errorQuestionMapper.queryErrorQuestionImages(errorquestionId);
        if (imageList == null || imageList.size() == 0) {
            throw new BaseServiceException(ServiceExceptionsEnum.FILE_NOT_EXIT);
        }
        //生成压缩文件包
        String zipPath = zipService.zipPackage(fileName, imageList);
        logger.debug("downloadErrorQuestionPackage:", zipPath);
        //生成连接地址
        URL url = ossService.getURL(zipPath.substring(1));
        return url.toString();
    }
}
