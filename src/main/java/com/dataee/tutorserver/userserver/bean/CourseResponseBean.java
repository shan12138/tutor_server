package com.dataee.tutorserver.userserver.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author JinYue
 * @CreateDate 2019/6/30 15:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseResponseBean {
    private Integer courseId;
    private String courseName;
}
