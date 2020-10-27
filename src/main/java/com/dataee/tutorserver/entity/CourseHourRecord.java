package com.dataee.tutorserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 课时记录（一堂课对应多个课时记录）
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseHourRecord extends Course {

    /**
     * ID编号
     */
    private Integer id;

    /**
     * 所属课程
     */
    private Course course;

    /**
     * 总课时
     */
    private Double totalClassHour;
    /**
     * 消耗课时
     */
    private Double consumeClassHour;

    private Double isFree;

    private Integer price;

    private Double discount;

    private Date createdAt;
    /**
     * 状态
     */
    private Integer state;

}