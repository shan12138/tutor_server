package com.dataee.tutorserver.tutorteacherserver.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/4/27 20:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDetailInfoRequestBean extends TeacherBasicInfoRequestBean {
    private String tutorExperience;
    private String honour;
    private String evaluation;
}
