package com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.bean;

import com.dataee.tutorserver.entity.Appointment;
import com.dataee.tutorserver.entity.Node;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherConsumeRequestBean {
    private String teacherConsumeName;
    private String  pdfAddress;
}
