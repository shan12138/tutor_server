package com.dataee.tutorserver.tutoradminserver.patriarchmanage.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 修改家长等级
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateInvitation {
    private Integer parentId;
    private  Integer partnerId;
    private  Integer parentLevel;
}
