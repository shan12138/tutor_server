package com.dataee.tutorserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/11 10:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeChatUser {
    private Integer weChatUserId;
    private String source;
    private String openId;
    private String unionId;
    private String sessionKey;
    private String avatarUrl;
    private String nickName;
    private String gender;
    private String country;
    private String province;
    private String city;
    private String language;
    private String telephone;
    private String countryCode;
    private Boolean subscribe;
    private String userId;
    private User user;
    private Integer state;
}
