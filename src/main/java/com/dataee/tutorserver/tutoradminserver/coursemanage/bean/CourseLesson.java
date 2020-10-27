package com.dataee.tutorserver.tutoradminserver.coursemanage.bean;

import com.dataee.tutorserver.entity.Teacher;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 排课的信息列表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseLesson {
    private Integer id;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date remarkCheckInTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private  Date remarkCheckOutTime;

    private String  teacherName;
    /**
     * 已开课和未开课
     */
    private  String state;
}
