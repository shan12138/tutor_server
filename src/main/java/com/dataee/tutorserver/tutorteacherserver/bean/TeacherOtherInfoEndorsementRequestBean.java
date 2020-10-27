package com.dataee.tutorserver.tutorteacherserver.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 为我代言请求信息
 *
 * @author JinYue
 * @CreateDate 2019/5/10 19:53
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherOtherInfoEndorsementRequestBean {
    private String tutorExperience;
    private String honour;
    private String evaluation;
}
