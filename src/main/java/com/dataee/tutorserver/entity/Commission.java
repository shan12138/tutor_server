package com.dataee.tutorserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 佣金列表类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Commission {

    /**
     * ID编号
     */
    private Integer id;

    private  Partner partner;

    private  Lesson lesson;

    private  double money;

    private  CourseHourRecord courseHourRecord;

    /**
     * 状态
     */
    private Integer state;

}