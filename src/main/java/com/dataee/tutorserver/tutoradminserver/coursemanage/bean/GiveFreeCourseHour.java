package com.dataee.tutorserver.tutoradminserver.coursemanage.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *赠送课时
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GiveFreeCourseHour {
    private Integer courseId;
    private double  giveFreeCourseHour;
}
