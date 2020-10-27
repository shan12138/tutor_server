package com.dataee.tutorserver.tutorpartnerserver.partnermanage.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/13 9:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParentDetailInfo {
    private Integer courseId;
    private Integer parentId;
    private String courseName;
    private String parentName;
    private String telephone;
    private Double sumMoney;
    private Integer totalClassHour;
    private Integer consumeClassHour;
    private Double expectedReturn;
    private Double actualIncome;
    private List<Commission> commissionList;
}
