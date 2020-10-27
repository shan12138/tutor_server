package com.dataee.tutorserver.userserver.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author JinYue
 * @CreateDate 2019/6/30 15:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonNumberResponseBean {
    private Integer lessonId;
    private Integer lessonNumber;
}
