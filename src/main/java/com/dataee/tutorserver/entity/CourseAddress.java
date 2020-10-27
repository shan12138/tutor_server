package com.dataee.tutorserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseAddress {
    private Integer addressId;
    private Integer parentId;
    private String region;
    private String addressDetail;
}


