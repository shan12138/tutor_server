package com.dataee.tutorserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 年级类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Grade {

    /**
     * ID编号
     */
    @NotNull(message = "年级Id不能为空")
    private Integer id;

    /**
     * 年级名称
     */
    @NotBlank(message = "年级名称不能为空")
    private String name;

    /**
     * 年级优先级
     */
    @NotNull(message = "年级优先级不能为空")
    private Integer priority;

    /**
     * 状态
     */
    private Integer state;

}