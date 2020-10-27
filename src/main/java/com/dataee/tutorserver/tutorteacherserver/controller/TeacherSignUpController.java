package com.dataee.tutorserver.tutorteacherserver.controller;

import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.errorInfoenum.ControllerExceptionEnum;
import com.dataee.tutorserver.commons.exceptions.IllegalParameterException;
import com.dataee.tutorserver.entity.Leisure;
import com.dataee.tutorserver.tutorteacherserver.bean.TeacherOtherInfoEndorsementRequestBean;
import com.dataee.tutorserver.tutorteacherserver.bean.TeacherOtherInfoStudentCardRequestBean;
import com.dataee.tutorserver.tutorteacherserver.service.ITeacherCenterService;
import com.dataee.tutorserver.tutorteacherserver.service.ITeacherSignUpService;
import com.dataee.tutorserver.tutorteacherserver.service.impl.TeacherSignUpServiceImpl;
import com.dataee.tutorserver.utils.ResultUtil;
import com.dataee.tutorserver.utils.SecurityUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 审核状态下进一步上传个人信息
 *
 * @author JinYue
 * @CreateDate 2019/5/10 11:44
 */
@RestController
@RequestMapping("/teacher/otherInfo")
@RequiresRoles("teacher")
public class TeacherSignUpController {
    private final Logger logger = LoggerFactory.getLogger(TeacherSignUpController.class);
    @Autowired
    private ITeacherSignUpService teacherSignUpService;
    @Autowired
    private ITeacherCenterService teacherCenterService;

    public void setTeacherSignUpService(TeacherSignUpServiceImpl teacherSignUpService) {
        this.teacherSignUpService = teacherSignUpService;
    }


    /**
     * 上传学生证和中国银行银行卡账号信息（第五步）
     *
     * @param studentCard 上传的学生证的相关信息
     * @return
     */
    @PutMapping("/studentCard")
    public ResponseEntity putStudentCard(@RequestBody @Valid TeacherOtherInfoStudentCardRequestBean studentCard) throws BaseControllerException, BaseServiceException {
        teacherSignUpService.addStudentCardInfo(studentCard, SecurityUtil.getPersonId());
        Integer teacherId = SecurityUtil.getPersonId();
        if (teacherId != null) {
            teacherCenterService.updateTeacherState(teacherId);
        } else {
            throw new IllegalParameterException();
        }
        return ResultUtil.success();
    }



    /**
     * 上传头像信息
     *
     * @param headPortrait 头像的资源地址
     * @return
     */
    @PutMapping("/headportrait")
    public ResponseEntity putHeadportrait(@RequestParam(value = "headPortrait") String headPortrait) throws BaseServiceException, BaseControllerException {
        teacherSignUpService.addHeadportrait(headPortrait, SecurityUtil.getPersonId());
        return ResultUtil.success();
    }

    /**
     * 上传我的代言信息（第三步信息）
     *
     * @param endorsementInfo
     * @return
     */
    @PutMapping("/endorsement")
    public ResponseEntity putEndorsement(@RequestBody TeacherOtherInfoEndorsementRequestBean endorsementInfo)
            throws BaseServiceException, BaseControllerException {
        teacherSignUpService.addEndorsementInfo(endorsementInfo, SecurityUtil.getPersonId());
        return ResultUtil.success();
    }

    /**
     * 添加课余可用时间（第四步）
     *
     * @param leisuresInfo
     * @return
     * @throws BaseServiceException
     */
    @PostMapping("/leisureTime")
    public ResponseEntity putLeisureTime(@RequestBody List<Leisure> leisuresInfo)
            throws BaseServiceException, BaseControllerException {
        teacherSignUpService.addLeisureTimeInfo(leisuresInfo, SecurityUtil.getPersonId());
        return ResultUtil.success();
    }


    /**
     * 修改课余时间
     *
     * @param leisures
     * @return
     */
    @ApiOperation("修改课余时间，需要上传state 0 是删除 1是增加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "leisures", value = "课余时间列表", required = true,
                    dataType = "Leisure", collectionFormat = "List", paramType = "body")
    })
    @PutMapping("/leisureTime")
    public ResponseEntity uppdateLeisureTime(@RequestBody @Valid @NotNull(message = "信息不能为空") List<Leisure> leisures) throws BaseControllerException, BaseServiceException {
        Integer teacherId = SecurityUtil.getPersonId();
        if (teacherId == null) {
            throw new BaseControllerException(ControllerExceptionEnum.UNKNOWN_USER);
        }
        teacherSignUpService.updateLeisureTimeInfo(leisures, teacherId);
        return ResultUtil.success();
    }

}
