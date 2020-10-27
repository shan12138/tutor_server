package com.dataee.tutorserver.tutoradminserver.coursemanage.bean;

import com.dataee.tutorserver.commons.bean.TimeBean;
import com.dataee.tutorserver.entity.Leisure;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Date;

/**
 * @author JinYue
 * @CreateDate 2019/5/24 23:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonBean {
    private Integer id;
    @NotNull(message = "未选择日期")
    @Min(value = 0, message = "日期范围有误")
    @Max(value = 6, message = "日期信息有误")
    private Integer day;
    @NotNull(message = "未选择日期")
    @Min(value = 1, message = "日期范围有误")
    @Max(value = 3, message = "日期信息有误")
    private Integer time;

    @Min(value = 0, message = "不清楚的课程选择")
    @Max(value = 1, message = "不清楚的课程选择")
    private Integer state;
    /**
     * 上课时间段
     */
    private TimeBean courseTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date remarkCheckInTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private  Date remarkCheckOutTime;
}
