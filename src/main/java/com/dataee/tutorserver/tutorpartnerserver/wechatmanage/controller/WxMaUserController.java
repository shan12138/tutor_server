package com.dataee.tutorserver.tutorpartnerserver.wechatmanage.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.UserPrincipals;
import com.dataee.tutorserver.commons.errorInfoenum.ControllerExceptionEnum;
import com.dataee.tutorserver.config.WxMaConfiguration;
import com.dataee.tutorserver.entity.WeChatUser;
import com.dataee.tutorserver.tutorpartnerserver.wechatmanage.bean.WeXinBean;
import com.dataee.tutorserver.tutorpartnerserver.wechatmanage.service.IWeChatUserManageService;
import com.dataee.tutorserver.userserver.bean.LoginResponseBean;
import com.dataee.tutorserver.userserver.service.IUserService;
import com.dataee.tutorserver.utils.EncodeUtil;
import com.dataee.tutorserver.utils.ResultUtil;
import com.dataee.tutorserver.utils.SecurityUtil;
import io.swagger.annotations.*;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/11 10:38
 */
@Api("微信授权模块")
@RestController
@RequestMapping("/wx/user")
public class WxMaUserController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${wx.miniapp.configs[0].appid}")
    private String appid;

    public final String DEFAULT_PASSWORD = "123456";
    public final String WE_CHAT_USER_ROLE = "we_chat_user";

    @Autowired
    private IWeChatUserManageService weChatUserManageService;
    @Autowired
    private IUserService userService;

    @ApiOperation(value = "微信授权登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "code", required = true,
                    dataType = "string", paramType = "query"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = WxMaJscode2SessionResult.class)
    })
    @PostMapping("/login")
    public ResponseEntity login(@RequestParam("code") String code) throws BaseControllerException, BaseServiceException {
        if (StringUtils.isBlank(code)) {
            throw new BaseControllerException(ControllerExceptionEnum.EMPTY_CODE);
        }
        final WxMaService wxService = WxMaConfiguration.getMaService(appid);
        try {
            WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(code);
            this.logger.info(session.getSessionKey());
            this.logger.info(session.getOpenid());
            WeChatUser weChatUser = weChatUserManageService.getWeChatUserByOpenId(session.getOpenid());
            UsernamePasswordToken token;
            LoginResponseBean loginResponseBean = new LoginResponseBean();
            if(weChatUser != null) {
                if(weChatUser.getTelephone() != null && !weChatUser.getTelephone().equals("")) {
                    this.logger.info("已绑定过手机号的登录");
                    weChatUserManageService.updateSessionKey(weChatUser.getWeChatUserId(), session.getSessionKey());
                    token = new UsernamePasswordToken(weChatUser.getTelephone(), DEFAULT_PASSWORD, WE_CHAT_USER_ROLE);
                    loginResponseBean.setName(weChatUser.getTelephone());
                }
                else {
                    this.logger.info("未绑定过手机号的登录");
                    weChatUserManageService.updateSessionKey(weChatUser.getWeChatUserId(), session.getSessionKey());
                    token = new UsernamePasswordToken(weChatUser.getOpenId(),
                            DEFAULT_PASSWORD, WE_CHAT_USER_ROLE);
                    loginResponseBean.setName(weChatUser.getOpenId());
                }
            }
            else {
                this.logger.info("初次登录，创建用户");
                String userId = UUID.randomUUID().toString();
                String encodeedPassword = EncodeUtil.encodePassword(DEFAULT_PASSWORD, userId);
                userService.registerUser(userId, session.getOpenid(), encodeedPassword, WE_CHAT_USER_ROLE);
                weChatUserManageService.registWeChatUser(session, userId,
                        DEFAULT_PASSWORD, WE_CHAT_USER_ROLE);
                token = new UsernamePasswordToken(session.getOpenid(), DEFAULT_PASSWORD, WE_CHAT_USER_ROLE);
                loginResponseBean.setName(session.getOpenid());
            }
            Subject currentUser = SecurityUtil.currentSubject();
            currentUser.login(token);
            UserPrincipals principals = SecurityUtil.getPrincipal();
            logger.info("{}, {}", principals.toString(), "aaaaaaaaaaaaaaaaaaaaa");
            loginResponseBean.setState(principals.getState());
            if (SecurityUtils.getSubject() != null) {
                SecurityUtils.getSubject().getSession().setTimeout(15552000000L);
            }
            return ResultUtil.success(loginResponseBean);
        } catch (WxErrorException e) {
            this.logger.error(e.getMessage(), e);
            throw new BaseControllerException(ControllerExceptionEnum.UNKNOWN_ERROR);
        }
    }

    @ApiOperation(value = "获取个人信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sessionKey", value = "sessionKey", required = true,
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "encryptedData", value = "encryptedData", required = true,
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "iv", value = "iv", required = true,
                    dataType = "string", paramType = "query"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = WxMaUserInfo.class)
    })
    @GetMapping("/info")
    public ResponseEntity info(String sessionKey, String encryptedData, String iv) {
        final WxMaService wxService = WxMaConfiguration.getMaService(appid);
        // 解密用户信息
        WxMaUserInfo userInfo = wxService.getUserService().getUserInfo(sessionKey, encryptedData, iv);
        return ResultUtil.success(userInfo);
    }

    @ApiOperation(value = "获取用户手机号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sessionKey", value = "sessionKey", required = true,
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "encryptedData", value = "encryptedData", required = true,
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "iv", value = "iv", required = true,
                    dataType = "string", paramType = "query"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = String.class)
    })
    @GetMapping("/phone")
    public ResponseEntity phone(String sessionKey, String encryptedData, String iv) {
        final WxMaService wxService = WxMaConfiguration.getMaService(appid);
        // 解密
        WxMaPhoneNumberInfo phoneNoInfo = wxService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);
        return ResultUtil.success(phoneNoInfo);
    }

    @ApiOperation(value = "绑定手机号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "code", required = true,
                    dataType = "WeXinBean", paramType = "body"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = String.class)
    })
    @PutMapping("/phone")
    public ResponseEntity bindingPhone(@RequestBody WeXinBean data) throws BaseControllerException, BaseServiceException {
        String code = data.getCode();
        String iv = data.getIv();
        String encryptedData = data.getEncryptedData();
        if (StringUtils.isBlank(code)) {
            throw new BaseControllerException(ControllerExceptionEnum.EMPTY_CODE);
        }
        final WxMaService wxService = WxMaConfiguration.getMaService(appid);
        try {
            WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(code);
            this.logger.info(session.getSessionKey());
            this.logger.info(session.getOpenid());
            // 解密用户信息
            WxMaPhoneNumberInfo phoneNoInfo = wxService.getUserService().getPhoneNoInfo(session.getSessionKey(), encryptedData, iv);
            WeChatUser weChatUser = weChatUserManageService.getWeChatUserByOpenId(session.getOpenid());
            if(weChatUser.getTelephone() != null && !weChatUser.getTelephone().equals("")) {
                throw new BaseControllerException(ControllerExceptionEnum.BIND_TELEPHONE_EXISTS);
            } else {
                userService.updateTelephone(weChatUser.getUserId(), phoneNoInfo.getPhoneNumber());
                LoginResponseBean loginResponseBean =
                        weChatUserManageService.updateTelephone(session, phoneNoInfo);
                return ResultUtil.success(loginResponseBean);
            }
        } catch (WxErrorException e) {
            this.logger.error(e.getMessage(), e);
            throw new BaseControllerException(ControllerExceptionEnum.UNKNOWN_ERROR);
        }
    }

    @ApiOperation(value = "检验用户是否存在(" +
            "若存在：绑定手机号按钮不显示" +
            "存在时登陆：直接进行登录" +
            "若不存在：显示绑定手机号按钮" +
            "不存在时登陆：创建user相关，绑定手机号操作后登录)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "code", required = true,
                    dataType = "string", paramType = "query"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = String.class)
    })
    @GetMapping("/check")
    public ResponseEntity checkUserExist(@RequestParam("code") String code) throws BaseControllerException {
        if (StringUtils.isBlank(code)) {
            throw new BaseControllerException(ControllerExceptionEnum.EMPTY_CODE);
        }
        final WxMaService wxService = WxMaConfiguration.getMaService(appid);
        try {
            WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(code);
            this.logger.info(session.getSessionKey());
            this.logger.info(session.getOpenid());
            WeChatUser weChatUser = weChatUserManageService.getWeChatUserByOpenId(session.getOpenid());
            if(weChatUser == null) {
                // 未绑定手机号创建过用户
                return ResultUtil.success(false);
            } else {
                // 绑定过手机号创建了用户
                return ResultUtil.success(true);
            }
        } catch (WxErrorException e) {
            this.logger.error(e.getMessage(), e);
            throw new BaseControllerException(ControllerExceptionEnum.UNKNOWN_ERROR);
        }
    }
}
