package com.dataee.tutorserver.tutoradminserver.coursemanage.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/24 10:12
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class CourseSqlEntity {
    private Integer courseId;
    private Integer addressId;
    private String subject;
    private String grade;
    private String courseName;
    private Integer teacherId;
    private Integer parentId;
    private Integer studentId;
    private Integer courseHourRecordId;
    private Integer productId;
    private Integer headAdminId;
    private Integer courseAdminId;
    private Integer studyAdminId;
}
