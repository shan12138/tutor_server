package com.dataee.tutorserver.tutoradminserver.coursemanage.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/24 10:25
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class CourseHourRecordSqlEntity {
    private Integer id;
    private Integer courseId;
    private Double totalClassHour;
    private Double consumeClassHour;
    private Integer price;
    private Boolean isFree;
    private Double  freeClassHour;
    private Double discount;
    private Date  createdAt;
    private Integer  state;

    public CourseHourRecordSqlEntity(Integer courseId, Double totalClassHour, double consumeClassHour, Integer price, Boolean isFree, Double discount, Date createdAt) {
        this.courseId = courseId;
        this.totalClassHour = totalClassHour;
        this.consumeClassHour = consumeClassHour;
        this.price = price;
        this.isFree = isFree;
        this.discount = discount;
        this.createdAt = createdAt;

    }
}
