package com.dataee.tutorserver.tutoradminserver.filemanage.bean;

import com.dataee.tutorserver.entity.Person;
import com.dataee.tutorserver.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

/**
 * @author JinYue
 * @CreateDate 2019/6/30 10:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorQuestionResponseBean {

    /**
     * ID编号
     */
    private Integer id;

    /**
     * 题目概要
     */
    private String essentialContent;

    /**
     * 学生编号
     */
   private String sn;
    /**
     * 合同编号
     */
    private  Integer contractId;
    /**
     * 学生姓名
     */
    private String studentName;


    /**
     * 科目
     */
    private String subject;

    /**
     * 年级
     */
    private String grade;

    /**
     * 课次
     */
    private Integer lessonNumber;

    /**
     * 所属用户
     */
    private Person person;
    /**
     * 上传时间
     */
    private Date time;

    /**
     * 备注
     */
    private String remark;
    /**
     * 题目照片
     */
    private List<String> questionPicture;
    /**
     * 是否打印
     */
    private boolean print;
    /**
     * 是否排课
     */
    private boolean course;
    /**
     *老师姓名
     */
    private String  teacherName;
    /**
     * 家长姓名
     */
    private String  parentName;
}
