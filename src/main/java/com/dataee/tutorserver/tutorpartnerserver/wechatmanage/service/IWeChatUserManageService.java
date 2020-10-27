package com.dataee.tutorserver.tutorpartnerserver.wechatmanage.service;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.entity.WeChatUser;
import com.dataee.tutorserver.userserver.bean.LoginResponseBean;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/11 17:07
 */
public interface IWeChatUserManageService {
    WeChatUser getWeChatUserByOpenId(String openid);

    void updateSessionKey(Integer id, String sessionKey);

    LoginResponseBean updateTelephone(WxMaJscode2SessionResult session,
                            WxMaPhoneNumberInfo phoneNoInfo) throws BaseControllerException;

    void registWeChatUser(WxMaJscode2SessionResult session, String userId, String default_password, String we_chat_user_role);
}
