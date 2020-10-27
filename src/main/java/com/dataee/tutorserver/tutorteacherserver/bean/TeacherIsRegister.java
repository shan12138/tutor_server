package com.dataee.tutorserver.tutorteacherserver.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherIsRegister {
    private Integer invitedTeacherId;
    private Integer teacherId;
    private Integer partnerId;
}
