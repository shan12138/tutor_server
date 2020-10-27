package com.dataee.tutorserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccompanyRegisterForm {
    private Integer id;
    private Date time;
    @NotBlank(message = "学生姓名不能为空")
    private String studentName;
    @NotNull(message = "学生性别不能为空")
    private Integer gender;
    @NotBlank(message = "学生学校不能为空")
    private String school;
    @NotBlank(message = "学生年级不能为空")
    private String grade;
    @NotBlank(message = "学生学习情况不能为空")
    private String studySituation;
    @NotBlank(message = "学生性格不能为空")
    private String studentCharacter;
    @NotBlank(message = "备注不能为空")
    private String remark;
    @NotBlank(message = "试听类型不能为空")
    private String auditionType;
    @NotBlank(message = "试听时间和科目不能为空")
    private String timeSubject;
    @NotBlank(message = "试听课地址和次数不能为空")
    private String addressNum;
    @NotBlank(message = "期望老师不能为空")
    private String expectTeacher;
    @NotBlank(message = "课程顾问不能为空")
    private String courseConsultant;
    private Integer nodeId;
}
