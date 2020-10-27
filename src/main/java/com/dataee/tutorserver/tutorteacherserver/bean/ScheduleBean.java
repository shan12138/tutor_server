package com.dataee.tutorserver.tutorteacherserver.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/26 3:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleBean {
    private Integer lessonId;
    private Integer courseId;
    private Integer day;
    private Integer time;
    private String courseName;
    private String address;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date remarkCheckInTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private  Date remarkCheckOutTime;

}
