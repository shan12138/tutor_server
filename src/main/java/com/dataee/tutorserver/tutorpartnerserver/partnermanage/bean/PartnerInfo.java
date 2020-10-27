package com.dataee.tutorserver.tutorpartnerserver.partnermanage.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/12 16:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartnerInfo {
    private String inviteCode;
    /**
     * 邀请人数
     */
    private Integer inviteParentNum;
    /**
     * 签约人数（家长购买了课程）
     */
    private Integer signingNum;
    /**
     * 总金额
     */
    private Double sumMoney;
    /**
     * 预计收益
     */
    private Double expectedReturn;
}
