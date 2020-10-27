package com.dataee.tutorserver.userserver.service.impl;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.commons.exceptions.SQLOperationException;
import com.dataee.tutorserver.entity.ErrorQuestion;
import com.dataee.tutorserver.userserver.bean.CourseResponseBean;
import com.dataee.tutorserver.userserver.bean.ErrorQuestionRequestBean;
import com.dataee.tutorserver.userserver.bean.LessonNumberResponseBean;
import com.dataee.tutorserver.userserver.dao.CourseMapper;
import com.dataee.tutorserver.userserver.dao.ErrorQuestionMapper;
import com.dataee.tutorserver.userserver.dao.UserMapper;
import com.dataee.tutorserver.userserver.service.IErrorQuestionService;
import com.dataee.tutorserver.utils.PageInfoUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import net.bytebuddy.asm.Advice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author JinYue
 * @CreateDate 2019/5/7 0:41
 */
@Service
public class ErrorQuestionServiceImpl implements IErrorQuestionService {
    private final Logger logger = LoggerFactory.getLogger(ErrorQuestionServiceImpl.class);
    @Autowired
    private ErrorQuestionMapper errorQuestionMapper;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CourseMapper courseMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveErrorQuestions(ErrorQuestionRequestBean errorQuestion, Integer personId, String role) throws SQLOperationException {
        if (errorQuestion.getRemarks() == null) {
            errorQuestion.setRemarks("无");
        }
        Integer roleId = userMapper.queryRoleIdByRole(role);
        try {
            if (errorQuestion.getId() != null) {
                //   删除之前的错题重建新的错题
                Integer delectCount = errorQuestionMapper.deleteErrorQuestion(errorQuestion.getId());
                if (delectCount != 1) {
                    throw new SQLOperationException();
                }
                Integer delectCountImage = errorQuestionMapper.deleteErrorQuestionImages(errorQuestion.getId());
            }
            //    存入错题的文字信息并获取主键ID
            errorQuestionMapper.addErrorQuestion(errorQuestion, personId, roleId);
            //    将错题地址存入数据库
            List<String> errorQuestionsImage = errorQuestion.getErrorQuestions();
            if (errorQuestionsImage != null && errorQuestionsImage.size() != 0) {
                errorQuestionMapper.addErrorQuestionImages(errorQuestion.getId(), errorQuestionsImage);
            }
        } catch (Exception e) {
            throw new SQLOperationException();
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public NewPageInfo<ErrorQuestion> getErrorQuestionsByPersonId(Integer personId, String role, Page page) throws BaseServiceException {
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<ErrorQuestion> resourceAddress = errorQuestionMapper.queryErrorQuestionsByPersonId(personId, role);
        PageInfo pageInfo = new PageInfo(resourceAddress);
        NewPageInfo<ErrorQuestion> newPageInfo = PageInfoUtil.read(pageInfo);
        return newPageInfo;
    }

    @Override
    public ErrorQuestion getErrorQuestionByQuestionId(int errorQuestionId) {
        ErrorQuestion errorQuestion = errorQuestionMapper.queryErrorQuestionByQuestionId(errorQuestionId);
        return errorQuestion;
    }

    @Override
    public List<CourseResponseBean> getCourseName(int personId, String role) {
        return courseMapper.queryCouseNameByPersonIdAndPersonRole(personId, role);
    }

    @Override
    public List<LessonNumberResponseBean> getLessonNumber(int courseId) {
        return courseMapper.queryLessonNumberByCourseId(courseId);
    }
}
