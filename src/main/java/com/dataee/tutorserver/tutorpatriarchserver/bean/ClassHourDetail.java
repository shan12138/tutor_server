package com.dataee.tutorserver.tutorpatriarchserver.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassHourDetail {
    private  Double totalClassHour;
    private  Double consumeClassHour;
    private  Double restClassHour;
}
