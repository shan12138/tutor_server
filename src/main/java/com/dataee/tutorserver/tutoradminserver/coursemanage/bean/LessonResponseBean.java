package com.dataee.tutorserver.tutoradminserver.coursemanage.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/17 20:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonResponseBean {
    private Integer id;
    private String date;
    private String checkInTime;
    private String checkOutTime;
    private Integer teacherId;
    private String teacherName;
    private Double classTime;
    private Integer lessonNumber;
    private String courseTime;
    private Integer parentRecordConfirmed;
    private Integer teacherRecordConfirmed;
    private Integer uploadState;
    private Integer time;

    public LessonResponseBean(Integer teacherId, Integer lessonNumber, String courseTime,
                              Integer parentRecordConfirmed, Integer teacherRecordConfirmed,
                              Integer uploadState, Integer time) {
        this.teacherId = teacherId;
        this.lessonNumber = lessonNumber;
        this.courseTime = courseTime;
        this.parentRecordConfirmed = parentRecordConfirmed;
        this.teacherRecordConfirmed = teacherRecordConfirmed;
        this.uploadState = uploadState;
        this.time = time;
    }
}
