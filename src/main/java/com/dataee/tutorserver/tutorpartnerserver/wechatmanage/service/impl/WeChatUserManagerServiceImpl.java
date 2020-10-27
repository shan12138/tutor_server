package com.dataee.tutorserver.tutorpartnerserver.wechatmanage.service.impl;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.bean.UserPrincipals;
import com.dataee.tutorserver.entity.WeChatUser;
import com.dataee.tutorserver.tutorpartnerserver.wechatmanage.dao.WeChatUserManageMapper;
import com.dataee.tutorserver.tutorpartnerserver.wechatmanage.service.IWeChatUserManageService;
import com.dataee.tutorserver.userserver.bean.LoginResponseBean;
import com.dataee.tutorserver.utils.SecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/11 17:07
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WeChatUserManagerServiceImpl implements IWeChatUserManageService {
    @Autowired
    private WeChatUserManageMapper weChatUserManageMapper;

    @Override
    public WeChatUser getWeChatUserByOpenId(String openid) {
        return weChatUserManageMapper.getWeChatUserByOpenId(openid);
    }

    @Override
    public void updateSessionKey(Integer id, String sessionKey) {
        weChatUserManageMapper.updateSessionKey(id, sessionKey);
    }

    @Override
    public LoginResponseBean updateTelephone(WxMaJscode2SessionResult session, WxMaPhoneNumberInfo phoneNoInfo)
            throws BaseControllerException {
        weChatUserManageMapper.updateTelephone(
                session.getOpenid(),
                phoneNoInfo.getPhoneNumber(),
                phoneNoInfo.getCountryCode());
        UsernamePasswordToken token =
                new UsernamePasswordToken(phoneNoInfo.getPhoneNumber(), "123456", "we_chat_user");
        Subject currentUser = SecurityUtil.currentSubject();
        currentUser.login(token);
        UserPrincipals principals = SecurityUtil.getPrincipal();
        LoginResponseBean loginResponseBean = new LoginResponseBean();
        loginResponseBean.setState(principals.getState());
        loginResponseBean.setName(phoneNoInfo.getPhoneNumber());
        if (SecurityUtils.getSubject() != null) {
            SecurityUtils.getSubject().getSession().setTimeout(15552000000L);
        }
        return loginResponseBean;
    }

    @Override
    public void registWeChatUser(WxMaJscode2SessionResult session, String userId, String default_password, String we_chat_user_role) {
        weChatUserManageMapper.createWeChatUser1(
                session.getOpenid(),
                session.getSessionKey(),
                userId);
    }
}
