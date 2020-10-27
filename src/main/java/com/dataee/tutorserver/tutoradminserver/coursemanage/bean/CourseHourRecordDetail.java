package com.dataee.tutorserver.tutoradminserver.coursemanage.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseHourRecordDetail {
    private  String courseName;
    private  Double totalClassHour;
    private  Double buyClassHour;
    private  Double consumeClassHour;
}
