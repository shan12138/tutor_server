package com.dataee.tutorserver.userserver.controller;

import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.UserPrincipals;
import com.dataee.tutorserver.commons.commonservice.IShortMessageService;
import com.dataee.tutorserver.commons.commonservice.impl.ShortMessageServiceImpl;
import com.dataee.tutorserver.commons.errorInfoenum.ControllerExceptionEnum;
import com.dataee.tutorserver.entity.User;
import com.dataee.tutorserver.userserver.bean.UpdatePasswordRequestBean;
import com.dataee.tutorserver.userserver.service.IUserService;
import com.dataee.tutorserver.userserver.service.impl.UserServiceImpl;
import com.dataee.tutorserver.utils.EncodeUtil;
import com.dataee.tutorserver.utils.ResultUtil;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * 找回账号
 *
 * @author JinYue
 * @CreateDate 2019/5/23 20:09
 */
@RestController
@RequestMapping("/{role:teacher|parent}/account")
public class FindAccountController {
    private final Logger logger = LoggerFactory.getLogger(FindAccountController.class);

    @Autowired
    private IUserService userService;

    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Autowired
    private IShortMessageService shortMessageService;

    public void setShortMessageService(ShortMessageServiceImpl shortMessageService) {
        this.shortMessageService = shortMessageService;
    }

    /**
     * 忘记密码获取手机验证码
     *
     * @param role
     * @param phone
     * @param session
     * @return
     * @throws BaseControllerException
     * @throws BaseServiceException
     */
    @ApiOperation("忘记密码，获取手机验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "role", value = "用户身份（教师或者学生）", dataType = "String", paramType = "path", required = true),
            @ApiImplicitParam(name = "phone", value = "手机号", dataType = "String", paramType = "path", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok")
    })
    @GetMapping("/{phone:\\d+}")
    public ResponseEntity getVerCode(@PathVariable String role, @PathVariable String phone, HttpSession session) throws BaseControllerException, BaseServiceException {
        //    判断该用户是否
        User user = userService.findByName(phone, role);
        if (user != null) {
            //    发送验证码
            String verCode = shortMessageService.sendMessage(phone);
            session.setAttribute("PHONE", phone);
            session.setAttribute("VERIFICATION_CODE", verCode);
            return ResultUtil.success();
        } else {
            throw new BaseControllerException(ControllerExceptionEnum.UNKNOWN_USER);
        }
    }

    /**
     * 重置密码
     *
     * @param role
     * @param passwordRequestBean
     * @param session
     * @return
     * @throws BaseControllerException
     * @throws BaseServiceException
     */
    @ApiOperation("重置密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "role", value = "用户身份（教师或者学生）", dataType = "String", paramType = "path", required = true),
            @ApiImplicitParam(name = "passwordRequestBean", value = "修改后的密码", dataType = "UpdatePasswordRequestBean", paramType = "body", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok")
    })
    @PutMapping("/password")
    public ResponseEntity setNewPassword(@PathVariable String role, @RequestBody @Valid UpdatePasswordRequestBean passwordRequestBean, HttpSession session) throws BaseControllerException, BaseServiceException {
        //获取手机号
        String phone = (String) session.getAttribute("PHONE");
        if (phone == null || phone.equals("")) {
            throw new BaseControllerException(ControllerExceptionEnum.UNKNOWN_PHONE);
        }
        //获取到USerId
        UserPrincipals principals = userService.getPrincipals(phone, role);
        if (principals == null) {
            throw new BaseControllerException(ControllerExceptionEnum.UNKNOWN_USER);
        }
        String userId = principals.getUserId();
        //    明文加密
        String encodPassword = EncodeUtil.encodePassword(passwordRequestBean.getPassword(), userId);
        //    设置新密码
        userService.updatePassword(principals.getId(), encodPassword);
        return ResultUtil.success();
    }
}
