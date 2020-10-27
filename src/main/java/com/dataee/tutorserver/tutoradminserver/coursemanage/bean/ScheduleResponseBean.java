package com.dataee.tutorserver.tutoradminserver.coursemanage.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/24 22:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleResponseBean {
    private List<Schedule> scheduleList;
    private String remarks;
    private Integer week;
}
