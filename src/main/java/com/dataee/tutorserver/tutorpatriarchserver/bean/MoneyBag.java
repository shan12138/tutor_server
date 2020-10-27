package com.dataee.tutorserver.tutorpatriarchserver.bean;

import com.dataee.tutorserver.entity.CourseHourRecord;
import com.dataee.tutorserver.entity.Lesson;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyBag {
    private Integer courseId;
    private String courseName;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date curriculaTime;
    private Integer price;
    private Double consumeCourseHour;
    private Double balance;
}
