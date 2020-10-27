package com.dataee.tutorserver.tutoradminserver.coursemanage.bean;

import com.dataee.tutorserver.entity.Administrator;
import com.dataee.tutorserver.entity.CourseHourRecord;
import com.dataee.tutorserver.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/17 20:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDetailResponseBean {
    private String address;
    private String studentName;
    private String teacherName;
    private String parentName;
    private String courseName;
    private List<CourseHourRecord> courseHourRecord;
    private String productName;
    //排课主任
    private Administrator courseAdmin;
    //班主任
    private Administrator headAdmin;

    private List<LessonResponseBean> lesson;
}
