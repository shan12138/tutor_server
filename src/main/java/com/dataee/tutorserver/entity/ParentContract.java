package com.dataee.tutorserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 合同
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParentContract {
    private Integer id;
    private String  sn;
    private String  bName;
    private String  bSex;
    private String  idCardNumber;
    private String  grade;
    private String  school;
    private String  partyAgentName;
    private String  telephone;
    private String  contactAddress;
    private String  subjectName;
    private String  otherSubject;
    private String  productName;
    private Double  totalCourseHour;
    private double  price;
    private double  totalMoney;
    private String  upperTotalMoney;
    private String  partyAgentIdCard;
    private Integer  startYear;
    private Integer  startMonth;
    private Integer  startDay;
    private Integer  endYear;
    private Integer  endMonth;
    private Integer  endDay;
    private String  signImage;
    private Integer isSign;
}
