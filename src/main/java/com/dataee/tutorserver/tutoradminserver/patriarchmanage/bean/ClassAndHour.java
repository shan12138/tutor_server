package com.dataee.tutorserver.tutoradminserver.patriarchmanage.bean;

import com.dataee.tutorserver.userserver.bean.GetCourseTeacherListResponseBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 课程和课时信息
 *
 * @Author ChenShanShan
 * @CreateDate 2019/5/10 16:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassAndHour extends GetCourseTeacherListResponseBean {
    private Double totalClassHour;
    private Double consumeClassHour;
    private Double restClassHour;
    private Double currentConsumeClassHour;
}
