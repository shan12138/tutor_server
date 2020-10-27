package com.dataee.tutorserver.tutoradminserver.teachermanage.bean;

import com.dataee.tutorserver.entity.Partner;
import com.dataee.tutorserver.entity.Teacher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherInvitedBean {
    private  Integer id;
    private  String teacherName;
    private  String invitePersonTel;
    private  String telephone;
    private  String invitePerson;
    private  String  status;
}
