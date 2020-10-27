package com.dataee.tutorserver.tutoradminserver.coursemanage.bean;

import com.dataee.tutorserver.entity.Administrator;
import com.dataee.tutorserver.entity.Student;
import com.dataee.tutorserver.entity.Teacher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * @author JinYue
 * @CreateDate 2019/7/2 15:52
 */
@Data
@AllArgsConstructor
public class CurrentCourseResponseBean {
    private Integer courseId;
    private Integer lessonId;
    private String courseName;
    private Student student;
    private Teacher teacher;
    private Integer lessonNumber;
    /**
     * 排课主任
     */
    private Administrator courseAdmin;
    /**
     * 班主任
     */
    private Administrator headAdmin;
    /**
     * 学管师
     */
    private Administrator studyAdmin;


    private Date courseTime;
    private String resourceType;
    /**
     * 2是未上传
     */
    private Integer uploadState;

    public CurrentCourseResponseBean() {
        this.resourceType = "未上传";
        this.uploadState = 2;
    }
}
