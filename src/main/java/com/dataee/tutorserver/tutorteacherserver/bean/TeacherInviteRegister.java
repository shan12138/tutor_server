package com.dataee.tutorserver.tutorteacherserver.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherInviteRegister {
    private  Integer teacherId;
    private  Integer invitedTeacherId;
    private  Integer teacherLevel;
    private  Integer partnerId;
}
