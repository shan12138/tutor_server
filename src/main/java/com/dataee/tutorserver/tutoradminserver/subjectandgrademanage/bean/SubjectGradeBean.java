package com.dataee.tutorserver.tutoradminserver.subjectandgrademanage.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/6/24 11:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectGradeBean {
    @NotNull(message = "科目Id不能为空")
    private Integer id;
    @NotNull(message = "科目优先级不能为空")
    private Integer priority;
}
