package com.dataee.tutorserver.tutorteacherserver.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/6/5 23:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreBean {
    @NotBlank(message = "科目不能为空")
    private String subject;
    @NotBlank(message = "年级不能为空")
    private String grade;
    @NotNull(message = "成绩不能为空")
    private Double score;
    private Integer teacherId;
    private String time;
}
