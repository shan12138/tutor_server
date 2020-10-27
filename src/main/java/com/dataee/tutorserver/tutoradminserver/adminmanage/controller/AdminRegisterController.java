package com.dataee.tutorserver.tutoradminserver.adminmanage.controller;

import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.exceptions.SQLOperationException;
import com.dataee.tutorserver.tutoradminserver.adminmanage.bean.AdminRequestBean;
import com.dataee.tutorserver.tutoradminserver.adminmanage.service.IAdminRegisterService;
import com.dataee.tutorserver.tutoradminserver.adminmanage.service.impl.AdminRegisterServiceImpl;
import com.dataee.tutorserver.utils.EncodeUtil;
import com.dataee.tutorserver.utils.ResultUtil;
import com.dataee.tutorserver.utils.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author JinYue
 * @CreateDate 2019/5/14 2:57
 */
@Api(tags = "添加管理员")
@RestController
@RequestMapping("/admin")
public class AdminRegisterController {
    private final Logger logger = LoggerFactory.getLogger(AdminLoginController.class);
    @Autowired
    private IAdminRegisterService adminRegisterService;

    public void setAdminRegisterService(AdminRegisterServiceImpl adminRegisterService) {
        this.adminRegisterService = adminRegisterService;
    }

    @ApiOperation("添加管理员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "adminRequestBean", value = "管理员注册信息", dataType = "AdminRequestBean", paramType = "query")
    })
    @RequiresPermissions("staff:create")
    @PostMapping("/")
    public ResponseEntity addNewAdmin(@RequestBody @Valid AdminRequestBean adminRequestBean) throws BaseControllerException, SQLOperationException {
        String crtId = SecurityUtil.getUserId();
        String encodePassword = EncodeUtil.encodePassword(adminRequestBean.getPassword(), adminRequestBean.getAdminId());
        adminRequestBean.setPassword(encodePassword);
        adminRegisterService.addAddmin(crtId, adminRequestBean);
        return ResultUtil.success();
    }

}
