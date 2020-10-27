package com.dataee.tutorserver.tutorpatriarchserver.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/12 15:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletCourse {
    private Integer id;
    private Integer courseId;
    private String courseName;
    private Double price;
    private Double consumeClassHour;
    private Double balance;
}
