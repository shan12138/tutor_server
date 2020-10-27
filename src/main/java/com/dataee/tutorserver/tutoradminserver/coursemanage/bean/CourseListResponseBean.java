package com.dataee.tutorserver.tutoradminserver.coursemanage.bean;

import com.dataee.tutorserver.entity.Administrator;
import com.dataee.tutorserver.entity.Parent;
import com.dataee.tutorserver.entity.Student;
import com.dataee.tutorserver.entity.Teacher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author 杨少聪
 * @Date 2019/5/17
 * @Description: com.dataee.tutorserver.tutoradminserver.coursemanage.bean
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class CourseListResponseBean {
    private Integer id;
    private Integer courseId;
    private String studentName;
    private String subject;
    private String courseName;
    private String grade;
    private String  parentName;
    private String headTeacher;
    private String account;
    private String scheduleAdmin;
    private String studyAdmin;
    private String teacherName;
    private String telephone;
    private String productName;

   /* private Student student;
    private Parent parent;
    private Teacher teacher;
    //排课主任
    private Administrator courseAdmin;
    //班主任
    private Administrator headAdmin;
    //学管师
    private Administrator studyAdmin;*/
/*    private Integer state;*/
}
