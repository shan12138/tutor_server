package com.dataee.tutorserver.tutorpatriarchserver.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/12/16 17:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParentInvitationNum {
    private Integer inviteParentNum;
    private Integer signingNum;
    private String inviteCode;
}
