package com.dataee.tutorserver.tutoradminserver.patriarchmanage.bean;

import com.dataee.tutorserver.entity.ParentLevel;
import com.dataee.tutorserver.entity.Partner;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 修改邀请信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateInvitationPage {
    private List<Partner> partners;
    private List<ParentLevel> parentLevel;
}
