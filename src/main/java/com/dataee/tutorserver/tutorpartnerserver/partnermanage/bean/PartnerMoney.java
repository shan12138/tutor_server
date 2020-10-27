package com.dataee.tutorserver.tutorpartnerserver.partnermanage.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/13 13:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartnerMoney {
    /**
     * 可提现金额 = 所得的实际佣金（家长端实际消耗课程的课时佣金+邀请教员的佣金）- 申请提现的金额
     */
    private Double canWithdrawNum;
    /**
     * 申请提现的金额
     */
    private Double withdrawingNum;
}
