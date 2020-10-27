package com.dataee.tutorserver.tutoradminserver.businessmanage.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/8 13:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartnerResponseBean {
    private Integer partnerId;
    private String partnerName;
    private String telephone;
    private Integer inviteParentNum;
    private Integer inviteTeacherNum;
    private Double commissionSum;
    private Double withdrawNum;
    private String inviteCode;
    private String state;
}
