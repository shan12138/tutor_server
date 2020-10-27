package com.dataee.tutorserver.tutoradminserver.electricsalemanage.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/29 23:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IntentionInfoRequestBean {
    @NotNull(message = "ID不能为空")
    private Integer id;
    private String name;
    private String telephone;
    private Integer sex;
    private String position;
    private String permanentAddress;
    private String childBasicInfo;
    private String weakDiscipline;
    private Integer tutorSex;
    private String tutorDemand;
    private String isIntention;
}
