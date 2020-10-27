package com.dataee.tutorserver.userserver.bean;

import com.dataee.tutorserver.entity.Address;
import com.dataee.tutorserver.entity.Teacher;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Author 杨少聪
 * @Date 2019/5/15
 * @Description: com.dataee.tutorserver.userServer.bean
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTodayCourseResponseBean {
    @NotNull
    private Integer courseId;
    @NotNull
    private String courseName;
    @NotNull
    private String courseTime;
    @NotNull
    private Address address;
    @NotNull
    private Teacher teacher;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    @NotNull
    private Date remarkCheckInTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    @NotNull
    private  Date remarkCheckOutTime;


}
