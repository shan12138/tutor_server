package com.dataee.tutorserver.tutoradminserver.coursemanage.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/24 23:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {
    private Integer day;
    private Integer time;
    private Integer state;
}
