package com.dataee.tutorserver.tutorteacherserver.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/4/27 9:30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherBasicInfoRequestBean {
    private int teacherId;
    private String teacherName;
    private String ownInviteCode;
    private Integer sex;
    private String birthday;
    private String nativePlace;
    private String qq;
    private String emergencyContact;
    private String emergencyTelephone;
    private String address;
    private String aliPayAccount;
    private String college;
    private String major;
    private String jobNumber;
    private List<String> teachingArea;
    private String currentStatus;
}
