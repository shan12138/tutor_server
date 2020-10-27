package com.dataee.tutorserver.tutorpatriarchserver.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 课程评价bean
 *
 * @author JinYue
 * @CreateDate 2019/6/3 20:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Remarks {
    @NotNull
    private Integer questionId;
    private String question;
    @NotBlank
    private String answer;
}
