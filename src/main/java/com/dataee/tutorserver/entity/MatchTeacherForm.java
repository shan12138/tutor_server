package com.dataee.tutorserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchTeacherForm {
    private Integer id;
    private Node node;
    private String sn;
    private Integer version;
    private String teacherName;
    private String teacherGender;
    private String school;
    private String  gradeMajor;
    private String contactModel;
    private String wechatNumber;
    private String email;
    private String emergencyContact1;
    private String emergencyContact2;
    private String characterHonerExperienceCourse;
    private String nearTimeArrange;
    private String dataIsComplete;
    private Double matchScore;
    private String toll;
    private String distanceTime;
    private String trainTime;
    private Date time;
    private String teacherConsumeName;
    private String  pdfAddress;
    private Integer isChange;
    private Integer nodeId;
    
}
