package com.dataee.tutorserver.userserver.bean;

import com.dataee.tutorserver.entity.ContractPdf;
import com.dataee.tutorserver.entity.CourseHourRecord;
import com.dataee.tutorserver.entity.Lesson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 将家长端的教员列表和课程列表复用
 *
 * @Author ChenShanShan
 * @CreateDate 2019/5/6 16:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCourseTeacherListResponseBean {
    private Integer id;
    private Integer courseId;
    private String  idCard;
    private Integer teacherId;
    private String courseName;
    private String teacherName;
    private String studentName;
    private String  pdfAddress;
    private String  signedPdfAddress;
    private ContractPdf contractPdf;
    List<CourseHourRecord> courseHourRecords;
    List<Lesson> lessons;
}
