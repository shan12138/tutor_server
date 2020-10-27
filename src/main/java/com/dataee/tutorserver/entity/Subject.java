package com.dataee.tutorserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 科目类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subject {

    /**
     * id编号
     */
    @NotNull(message = "科目名称不能为空")
    private Integer id;

    /**
     * 学科名称
     */
    @NotBlank(message = "科目名称不能为空")
    private String name;

    /**
     * 学科优先级
     */
    @NotNull(message = "科目优先级不能为空")
    private Integer priority;

    /**
     * 状态
     */
    private Integer state;

}