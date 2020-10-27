package com.dataee.tutorserver.tutoradminserver.coursemanage.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/17 20:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceRecordRequestBean {
    @NotNull(message = "课时ID不能为空")
    private Integer courseHourRecordId;
    @NotNull(message = "课堂ID不能为空")
    private Integer lessonId;
    @NotNull(message = "课程ID不能为空")
    private Integer courseId;
    @NotBlank(message = "签到时间不能为空")
    private String checkInTime;
    @NotBlank(message = "签退时间不能为空")
    private String checkOutTime;
    @NotNull(message = "原始时长不能为空")
    private Double oldClassTime;
}
