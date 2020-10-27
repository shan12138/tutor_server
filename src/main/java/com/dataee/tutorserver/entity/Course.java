package com.dataee.tutorserver.entity;

import com.dataee.tutorserver.tutorpatriarchserver.bean.ClassHourDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 课程类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    /**
     * 自增ID编号
     */
    private Integer id;

    /**
     * ID编号
     */
    private Integer courseId;

    /**
     * 所属科目
     */
    private String subject;

    /**
     * 所属年级
     */
    private String grade;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 上课次数
     */
    private Integer classTimes;

    /**
     * 上课的家庭住址
     */
    private CourseAddress address;

    /**
     * 所属的教师
     */
    private Teacher teacher;

    /**
     * 所属的家长
     */
    private Parent parent;

    /**
     * 所属的学生
     */
    private Student student;
    /**
     * 课程的课时记录
     */
    private List<CourseHourRecord> courseHourRecord;

    private ClassHourDetail classHourDetail;

    /**
     * 课程的教案列表
     */
    private List<StudyPdf> studyPdf;

    /**
     * 课程的课堂记录
     */
    private List<Lesson> lesson;

    /**
     * 所属产品
     */
    private Product product;

    //排课主任
    private Administrator courseAdmin;
    //班主任
    private Administrator headAdmin;

    /**
     * 状态
     */
    private Integer state;

}