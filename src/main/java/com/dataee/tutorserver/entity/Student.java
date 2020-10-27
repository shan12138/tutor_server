package com.dataee.tutorserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 学生类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student extends User {

    /**
     * ID编号
     */
    private Integer studentId;

    /**
     * 学生名称
     */
    private String studentName;

    /**
     * 0女1男
     * 性别
     */
    private Integer sex;

    /**
     * 年级
     */
    private String grade;

    /**
     * 学校
     */
    private String school;

    /**
     * 身份证
     */
    private String idCard;

    /**
     * 薄弱学科
     */
    private List<String> weakDiscipline;

    private Integer uploadState;

    //合同编号
    private String number;

    private Integer contractId;

    //合同名称
    private String contractName;

    /**
     * 状态
     */
    private Integer state;

}