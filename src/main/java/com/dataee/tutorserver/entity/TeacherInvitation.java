package com.dataee.tutorserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 老师邀请
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherInvitation {
    private  Integer id;
    private  String teacherName;
    private  String telephone;
    private  String teacherInvitationCode;
    private  String partnerCode;
    //谁邀请的这个老师
    private  Teacher teacher;
    //被邀请的老师的id
    private  Teacher invitedTeacher;
    private Partner partner;
    private String status;
}
