package com.dataee.tutorserver.tutorteacherserver.service.impl;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.errorInfoenum.ServiceExceptionsEnum;
import com.dataee.tutorserver.commons.exceptions.SQLOperationException;
import com.dataee.tutorserver.entity.Leisure;
import com.dataee.tutorserver.tutorteacherserver.bean.TeacherOtherInfoEndorsementRequestBean;
import com.dataee.tutorserver.tutorteacherserver.bean.TeacherOtherInfoStudentCardRequestBean;
import com.dataee.tutorserver.tutorteacherserver.dao.TeacherSignUpMapper;
import com.dataee.tutorserver.tutorteacherserver.service.ITeacherSignUpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JinYue
 * @CreateDate 2019/5/10 11:44
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TeacherSignUpServiceImpl implements ITeacherSignUpService {
    private final Logger logger = LoggerFactory.getLogger(TeacherSignUpServiceImpl.class);
    @Autowired
    private TeacherSignUpMapper teacherSignUpMapper;

    public void setTeacherSignUpMapper(TeacherSignUpMapper teacherSignUpMapper) {
        this.teacherSignUpMapper = teacherSignUpMapper;
    }

    @Override
    public void addStudentCardInfo(TeacherOtherInfoStudentCardRequestBean studentCardBean, Integer personId) throws BaseServiceException {
        //    分成两不，这样以后修改功能还可以用
        //    上传学生证信息
        String studentNumber = studentCardBean.getStudentNumber();
        String studentCardPicture = studentCardBean.getStudentCardPicture();
        teacherSignUpMapper.updateStudentCardByPersonId(studentNumber, studentCardPicture, personId);

        //    上传支付宝信息
        String aliPayAccount = studentCardBean.getAliPayAccount();
        String aliPayPicture = studentCardBean.getAliPayPicture();
        String openBankName = studentCardBean.getOpenBankName();
        teacherSignUpMapper.updateAliPayByPersonId(aliPayAccount, aliPayPicture, openBankName, personId);

    }

    @Override
    public void addHeadportrait(String resourceAddress, Integer personId) throws BaseServiceException {
        teacherSignUpMapper.updateHeadPortraitBypPersonId(resourceAddress, personId);
    }

    @Override
    public void addEndorsementInfo(TeacherOtherInfoEndorsementRequestBean endorsementBean, Integer personId) throws BaseServiceException {
        //    同理拆开
        //    家教经验
        String tutorExperience = endorsementBean.getTutorExperience();
        if (tutorExperience != null) {
            teacherSignUpMapper.updateTutorExperienceByPersonId(endorsementBean.getTutorExperience(), personId);
        }
        //    获得的荣誉
        String honour = endorsementBean.getHonour();
        if (honour != null) {
            teacherSignUpMapper.updateHonourByPersonId(honour, personId);
        }
        //    自我评价
        String evaluation = endorsementBean.getEvaluation();
        if (evaluation != null) {
            teacherSignUpMapper.updateEvaluationByPersonId(endorsementBean.getEvaluation(), personId);
        }
    }

    @Override
    public void addLeisureTimeInfo(List<Leisure> lersureList, Integer personId) throws BaseServiceException {
        //空余时间新增和修改是不一样的
        teacherSignUpMapper.addLeisureTimeTableByPersonId(lersureList, personId);
    }

    private void deleteLeisureTimeInfo(List<Leisure> lersureList, Integer personId) throws BaseServiceException {
        //空余时间新增和修改是不一样的
        Integer id;
        for (Leisure leisure : lersureList) {
            id = teacherSignUpMapper.queryLeisureId(leisure, personId);
            if (id != null) {
                int count = teacherSignUpMapper.deleteLeisureTimeTableByPersonId(id);
                if (count != 1) {
                    throw new SQLOperationException();
                }
            } else {
                logger.info("删除课余时间失败");
                throw new BaseServiceException(ServiceExceptionsEnum.FAIL_SAVE_LEISURE);
            }
        }
    }

    @Override
    public void updateLeisureTimeInfo(List<Leisure> leisures, Integer personId) throws BaseServiceException {
        //    剥离删除和新增的数据
        List<Leisure> deleteLeisure = new ArrayList<>();
        List<Leisure> addLeisure = new ArrayList<>();
        leisures.forEach(leisure -> {
            if (leisure.getState() == 0) {
                deleteLeisure.add(leisure);
            } else {
                addLeisure.add(leisure);
            }
        });
        try {
            //    新增的课余时间
            if (addLeisure != null) {
                addLeisureTimeInfo(addLeisure, personId);
            }
            //    删除的课余时间
            if (deleteLeisure != null) {
                deleteLeisureTimeInfo(deleteLeisure, personId);
            }
        } catch (Exception e) {
            throw new BaseServiceException(ServiceExceptionsEnum.FAIL_SAVE_LEISURE);
        }
    }
}
