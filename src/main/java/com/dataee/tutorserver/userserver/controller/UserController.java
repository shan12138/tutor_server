package com.dataee.tutorserver.userserver.controller;

import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.commons.bean.UserPrincipals;
import com.dataee.tutorserver.commons.commonservice.IOSSService;
import com.dataee.tutorserver.commons.errorInfoenum.ControllerExceptionEnum;
import com.dataee.tutorserver.commons.exceptions.IllegalParameterException;
import com.dataee.tutorserver.entity.Product;
import com.dataee.tutorserver.entity.ProductAttribute;
import com.dataee.tutorserver.tutoradminserver.productmanage.service.IProductManageService;
import com.dataee.tutorserver.userserver.bean.LoginInfoRequestBean;
import com.dataee.tutorserver.userserver.bean.LoginResponseBean;
import com.dataee.tutorserver.userserver.bean.UpdatePasswordRequestBean;
import com.dataee.tutorserver.userserver.bean.UserBean;
import com.dataee.tutorserver.userserver.service.IPersonService;
import com.dataee.tutorserver.userserver.service.IUserService;
import com.dataee.tutorserver.userserver.service.impl.PersonServiceImpl;
import com.dataee.tutorserver.utils.EncodeUtil;
import com.dataee.tutorserver.utils.ResultUtil;
import com.dataee.tutorserver.utils.SecurityUtil;
import io.swagger.annotations.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * 用户模块控制器
 * 包含：登录、退出
 *
 * @author JinYue
 */
@RestController
@RequestMapping("/{role:teacher|parent|user}")
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IOSSService ossService;

    @Autowired
    private IPersonService personService;

    @Autowired
    private IProductManageService productManageService;

    public void setPersonService(PersonServiceImpl personService) {
        this.personService = personService;
    }

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    /**
     * 普通用户登录接口
     *
     * @param role
     * @param loginInfoRequestBean
     * @return
     * @throws BaseControllerException
     * @throws AuthenticationException
     */
    @ApiOperation(value = "普通用户登录接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginInfoRequestBean", value = "登录信息", required = true, dataType = "LoginInfoRequestBean", paramType = "body")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok")
    })
    @PostMapping("/login")
    public ResponseEntity login(@PathVariable String role, @RequestBody @Validated LoginInfoRequestBean loginInfoRequestBean)
            throws BaseControllerException, AuthenticationException {
        String username = loginInfoRequestBean.getUsername();
        String password = loginInfoRequestBean.getPassword();
        UsernamePasswordToken token =
                new UsernamePasswordToken(username, password, role);
        Subject currentUser = SecurityUtil.currentSubject();
        currentUser.login(token);
        UserPrincipals principals = SecurityUtil.getPrincipal();
        LoginResponseBean loginResponseBean = new LoginResponseBean();
        loginResponseBean.setState(principals.getState());
        String name = personService.getPersonName(role, principals.getPersonId());
        loginResponseBean.setName(name);
        if (SecurityUtils.getSubject() != null) {
            SecurityUtils.getSubject().getSession().setTimeout(15552000000L);
        }
        return ResultUtil.success(loginResponseBean);
    }

    @PostMapping("/logout")
    public ResponseEntity logout() {
        Subject currentUser = SecurityUtil.currentSubject();
        currentUser.logout();
        return ResultUtil.success();
    }

    /**
     * 用户修改密码
     *
     * @param requestBean
     * @return
     * @throws BaseControllerException
     * @throws BaseServiceException
     */
    @PostMapping("/updatePassword")
    public ResponseEntity updatePassword(@RequestBody @Validated UpdatePasswordRequestBean requestBean)
            throws BaseControllerException, BaseServiceException {
        String userId = SecurityUtil.getUserId();
        Integer id = SecurityUtil.getId();
        if (userId == null || id == null) {
            throw new BaseControllerException(ControllerExceptionEnum.NULL_USER_INFO);
        }
        //校验用户原密码是否正确
        userService.verifyPassword(userId, requestBean.getOldPassword());
        String encodePassword = EncodeUtil.encodePassword(requestBean.getPassword(), userId);
        userService.updatePassword(id, encodePassword);
        return ResultUtil.success();
    }

    @ApiOperation(value = "教员和家长获取轮播首页")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = URL.class, responseContainer = "List")
    })
    @GetMapping("/homePicture")
    public ResponseEntity getHomePicture() throws BaseServiceException {
        List<URL> urlList = new ArrayList<>();
        List<String> stringList = userService.getHomePictures();
        for (String s : stringList) {
            urlList.add(ossService.getURL(s));
        }
        return ResultUtil.success(urlList);
    }

    @ApiOperation(value = "获取家长或者教员当前的状态")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Integer.class)
    })
    @GetMapping("/state")
    public ResponseEntity getPersonId(@PathVariable String role, @RequestParam Integer personId) throws BaseServiceException, IllegalParameterException {
        if (personId == null) {
            throw new IllegalParameterException("请求参数异常");
        }
        Integer state = personService.getPersonState(personId, role);
        return ResultUtil.success(state);
    }

    @ApiOperation(value = "获取家长或者教员部分信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Integer.class)
    })
    @GetMapping("/info")
    public ResponseEntity getUserId(@PathVariable String role) throws BaseControllerException {
        Integer personId = SecurityUtil.getPersonId();
        UserBean userBean = personService.getUser(personId, role);
        return ResultUtil.success(userBean);
    }

    @ApiOperation(value = "获取产品列表")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Product.class, responseContainer = "List")
    })
    @GetMapping("/productList")
    public ResponseEntity getRoleList(@RequestParam Integer pageNo, @RequestParam Integer limit) {
        Page page = new Page(limit, pageNo);
        return ResultUtil.success(productManageService.getProductList(page));
    }

    @ApiOperation(value = "获取产品的字段列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "产品id", required = true,
                    dataType = "int", paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = ProductAttribute.class, responseContainer = "List")
    })
    @GetMapping("/product/attribute/{id}")
    public ResponseEntity getProductAttribute(@PathVariable("id") int id) {
        return ResultUtil.success(productManageService.getProductAttribute(id));
    }
}
