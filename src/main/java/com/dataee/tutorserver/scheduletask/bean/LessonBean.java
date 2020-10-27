package com.dataee.tutorserver.scheduletask.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/6/29 16:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonBean {
    private Integer teacherId;
    private Integer lessonId;
    private String courseTime;
    private Integer courseId;
}
