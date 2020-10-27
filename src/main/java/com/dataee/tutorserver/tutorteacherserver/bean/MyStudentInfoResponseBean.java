package com.dataee.tutorserver.tutorteacherserver.bean;

/**
 * @author JinYue
 * @CreateDate 2019/5/16 11:53
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyStudentInfoResponseBean {
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
     * 课程列表
     */
    private List<CourseInfo> courseInfoList;
}

