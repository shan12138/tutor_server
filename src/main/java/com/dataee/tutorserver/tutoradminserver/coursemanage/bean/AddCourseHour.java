package com.dataee.tutorserver.tutoradminserver.coursemanage.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**'
 * 续课时
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddCourseHour {
    private Integer courseId;
    private Double  addCourseHour;
    private Double  discount;
}
