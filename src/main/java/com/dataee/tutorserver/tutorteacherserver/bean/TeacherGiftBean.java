package com.dataee.tutorserver.tutorteacherserver.bean;

import com.dataee.tutorserver.entity.Teacher;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherGiftBean {
    private  Integer id;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date invitationSuccessDate;
    private Integer invitationTeacherId;
    private Integer  teacherId;
    private String status;



}
