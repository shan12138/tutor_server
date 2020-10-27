package com.dataee.tutorserver.tutorpartnerserver.wechatmanage.dao;

import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.dataee.tutorserver.entity.WeChatUser;
import org.apache.ibatis.annotations.*;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/11 17:08
 */
@Mapper
public interface WeChatUserManageMapper {
    @Select("select * from we_chat_user where open_id = #{openid}")
    WeChatUser getWeChatUserByOpenId(String openid);

    @Update("update we_chat_user set session_key = #{sessionKey} where we_chat_user_id = #{id}")
    void updateSessionKey(@Param("id") Integer id, @Param("sessionKey") String sessionKey);

//    @Insert("insert into we_chat_user(open_id, session_key, telephone, country_code, user_id) values(" +
//            "#{openid}, #{sessionKey}, #{phoneNumber}, #{countryCode}, #{userId})")
//    void createWeChatUser(@Param("openid") String openid,
//                          @Param("sessionKey") String sessionKey,
//                          @Param("phoneNumber") String phoneNumber,
//                          @Param("countryCode") String countryCode,
//                          @Param("userId") String userId);

    @Insert("insert into we_chat_user(open_id, session_key, user_id) values(" +
            "#{openid}, #{sessionKey}, #{userId})")
    void createWeChatUser1(@Param("openid") String openid,
                           @Param("sessionKey") String sessionKey,
                           @Param("userId") String userId);

    @Update("update we_chat_user set telephone = #{phoneNumber}, country_code = #{countryCode} where open_id = #{openid}")
    void updateTelephone(@Param("openid") String openid,
                         @Param("phoneNumber") String phoneNumber,
                         @Param("countryCode") String countryCode);
}
