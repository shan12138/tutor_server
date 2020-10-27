package com.dataee.tutorserver.userserver.controller;

import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.commonservice.IShortMessageService;
import com.dataee.tutorserver.commons.commonservice.impl.ShortMessageServiceImpl;
import com.dataee.tutorserver.commons.errorInfoenum.ControllerExceptionEnum;
import com.dataee.tutorserver.commons.errorInfoenum.ServiceExceptionsEnum;
import com.dataee.tutorserver.entity.Teacher;
import com.dataee.tutorserver.entity.User;
import com.dataee.tutorserver.tutorteacherserver.bean.TeacherInviteRegister;
import com.dataee.tutorserver.tutorteacherserver.bean.TeacherIsRegister;
import com.dataee.tutorserver.tutorteacherserver.dao.TeacherInviteMapper;
import com.dataee.tutorserver.userserver.bean.RegisterRequestBean;
import com.dataee.tutorserver.userserver.service.IUserService;
import com.dataee.tutorserver.userserver.service.impl.UserServiceImpl;
import com.dataee.tutorserver.utils.EncodeUtil;
import com.dataee.tutorserver.utils.ResultUtil;
import io.swagger.annotations.*;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

/**
 * 用户注册请求
 *
 * @author JinYue
 */
@RestController
@RequestMapping("/{role:teacher|parent|partner}")
public class RegisterController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IUserService userService;

    @Autowired
    private TeacherInviteMapper teacherInviteMapper;

    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Autowired
    private IShortMessageService shortMessageService;

    public void setShortMessageService(ShortMessageServiceImpl shortMessageService) {
        this.shortMessageService = shortMessageService;
    }

    /**
     * 验证码校验
     *
     * @param verCode
     * @param session
     * @return
     * @throws BaseControllerException
     */
    @ApiOperation(value = "验证码校验")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "verCode", value = "验证码", required = true, dataType = "String", paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok")
    })
    @GetMapping("/verification/{verCode}")
    public ResponseEntity matcher(@PathVariable @NotBlank(message = "验证码不能为空") String verCode, HttpSession session)
            throws BaseControllerException {
        String verificationCode = (String) session.getAttribute("VERIFICATION_CODE");
        if (verificationCode == null || verificationCode.length() == 0) {
            throw new BaseControllerException(ControllerExceptionEnum.INCORRECT_VERIFICATION_CODE);
        }
        logger.info("code:{}", verificationCode);
        if (verCode.equals(verificationCode)) {
            return ResultUtil.success();
        } else {
            throw new BaseControllerException(ControllerExceptionEnum.INCORRECT_VERIFICATION_CODE);
        }
    }

    /**
     * 新用户设置密码
     *
     * @param role
     * @param registerInfo
     * @return
     */
    @ApiOperation(value = "验证码校验")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "role", value = "用户身份（教师或家长）", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "registerInfo", value = "验证码", required = true, dataType = "RegisterRequestBean", paramType = "body")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok")
    })
    @PostMapping("/user")
    public ResponseEntity register(@PathVariable String role, @RequestBody @Valid RegisterRequestBean registerInfo, HttpSession session)
            throws BaseControllerException, BaseServiceException {
        String username = (String) session.getAttribute("PHONE");
        if (username == null) {
            throw new BaseControllerException(ControllerExceptionEnum.UNKNOWN_PHONE);
        }
        String userId = UUID.randomUUID().toString();
        String encodeedPassword = EncodeUtil.encodePassword(registerInfo.getPassword(), userId);
        // 创建教师对象
        userService.registerUser(userId, username, encodeedPassword, role);
        return ResultUtil.success();
    }

    /**
     * 获取手机验证码
     *
     * @param role
     * @param phone
     * @param session
     * @return
     * @throws BaseServiceException
     */
    @ApiOperation(value = "验证码校验")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "role", value = "用户身份（教师或家长）", required = true, dataType = "String", paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok")
    })
    @GetMapping("/{phone:\\d+}/verCode")
    public ResponseEntity getVerificationCode(@PathVariable String role, @PathVariable String phone, HttpSession session) throws BaseServiceException {
        //检查用户在该角色下是否存在
        System.out.println("66666666666666");
        Integer count = userService.findRegisterUser(phone, role);
        if (count != 0) {
            throw new BaseServiceException(ServiceExceptionsEnum.USER_EXIST);
        }
        //发送短信
        String verificationCode = shortMessageService.sendMessage(phone);
        session.setAttribute("PHONE", phone);
        session.setAttribute("VERIFICATION_CODE", verificationCode);
        return ResultUtil.success();
    }
}
