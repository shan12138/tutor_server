package com.dataee.tutorserver.tutorteacherserver.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author JinYue
 * @CreateDate 2019/5/16 15:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseInfo {
    private String courseName;
    private String address;
}
