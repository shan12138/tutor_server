package com.dataee.tutorserver.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.formula.functions.T;

import java.util.Date;

/**
 * 邀请老师赠礼
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvitationTeacherGift {
    private  Integer id;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private  Date invitationSuccessDate;
    private  Teacher  invitationTeacher;
    private  Teacher  teacher;
    private  String status;

}
