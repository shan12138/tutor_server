package com.dataee.tutorserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Partner {
    private Integer partnerId;
    private String partnerName;
    private String telephone;
    private String inviteCode;
    private String alipayAccount;
    private String alipayName;
    private WeChatUser weChatUser;
    private Integer weChatUserId;

    public Partner(String partnerName, String telephone, Integer weChatUserId, String inviteCode) {
        this.partnerName = partnerName;
        this.telephone = telephone;
        this.weChatUserId = weChatUserId;
        this.inviteCode = inviteCode;
    }
}
