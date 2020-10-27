package com.dataee.tutorserver.tutoradminserver.adminmanage.controller;

import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.bean.UserPrincipals;
import com.dataee.tutorserver.entity.Permission;
import com.dataee.tutorserver.entity.Role;
import com.dataee.tutorserver.tutoradminserver.adminmanage.bean.AdminLoginResponseBean;
import com.dataee.tutorserver.tutoradminserver.rolemanage.service.IRoleManageService;
import com.dataee.tutorserver.userserver.bean.LoginInfoRequestBean;
import com.dataee.tutorserver.utils.RandomStringUtil;
import com.dataee.tutorserver.utils.ResultUtil;
import com.dataee.tutorserver.utils.SecurityUtil;
import io.swagger.annotations.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JinYue
 * @CreateDate 2019/5/21 10:35
 */
@Api(value = "部分具体的角色", tags = "管理员登录")
@RestController
@RequestMapping("/admin")
public class AdminLoginController {
    private final Logger logger = LoggerFactory.getLogger(AdminLoginController.class);
    private final String ADMIN = "admin";

    @Autowired
    private IRoleManageService roleManageService;

    @ApiOperation(value = "用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginInfoRequestBean", value = "用户信息", required = true, dataType = "LoginInfoRequestBean", paramType = "body"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = AdminLoginResponseBean.class)
    })
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Validated LoginInfoRequestBean loginInfoRequestBean)
            throws BaseControllerException, AuthenticationException {
        String username = loginInfoRequestBean.getUsername();
        String password = loginInfoRequestBean.getPassword();
        UsernamePasswordToken token =
                new UsernamePasswordToken(username, password, ADMIN);
        Subject currentUser = SecurityUtil.currentSubject();
        currentUser.login(token);
        //临时配置session时间，暂时配置时间为6个月
        if (SecurityUtils.getSubject() != null) {
            SecurityUtils.getSubject().getSession().setTimeout(15552000000L);
        }
        UserPrincipals admin = SecurityUtil.getPrincipal();
        List<Permission> permissions = new ArrayList<>();
        if(admin.getRoles().size() != 0) {
            Role role = admin.getRoles().get(0);
            permissions = roleManageService.getPermissionsOfCurrentUser(role.getRoleId());
        }
        if (admin != null) {
            return ResultUtil.success(new AdminLoginResponseBean(admin.getId(),admin.getPersonName(), admin.getRoles(), permissions));
        } else {
            return ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR, "登录异常请重试！");
        }

    }

    /**
     * 退出
     *
     * @return Message
     */
    @PostMapping("/logout")
    public ResponseEntity logout() {
        Subject currentUser = SecurityUtil.currentSubject();
        currentUser.logout();
        return ResultUtil.success();
    }

    @ApiOperation("获取admin角色")
    @GetMapping("/role")
    public ResponseEntity getRole() throws BaseControllerException {
        UserPrincipals principals = SecurityUtil.getPrincipal();
        List<Permission> permissions = new ArrayList<>();
        if(principals.getRoles().size() != 0) {
            Role role = principals.getRoles().get(0);
            permissions = roleManageService.getPermissionsOfCurrentUser(role.getRoleId());
        }
        if (permissions != null) {
            return ResultUtil.success(permissions);
        } else {
            return ResultUtil.error(HttpStatus.NOT_IMPLEMENTED, "请重新登录");
        }
    }
}
