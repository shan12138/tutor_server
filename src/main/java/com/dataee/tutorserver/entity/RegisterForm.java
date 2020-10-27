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
public class RegisterForm {
    private Integer id;
    private Node node;
    private Date time;
    @NotBlank(message = "学生姓名不能为空")
    private String  studentName;
    @NotNull(message = "性别不能为空")
    private Integer gender;
    @NotBlank(message = "学校不能为空")
    private String school;
    @NotBlank(message = "年级不能为空")
    private String  grade;
    @NotBlank(message = "补习科目不能为空")
    private String tutorialSubject;
    @NotNull(message = "分数不能为空")
    private String score;
    @NotNull(message = "排名不能为空")
    private Integer ranking;
    @NotBlank(message = "学生性格不能为空")
    private String studentCharacter;
    @NotBlank(message = "学习态度不能为空")
    private String habitAttitude;
    @NotBlank(message = "家庭情况不能为空")
    private String familySituation;
    @NotBlank(message = "学习负责人不能为空")
    private String learnResponsibility;
    @NotBlank(message = "备注不能为空")
    private String  remark;
    @NotBlank(message = "试听课类型不能为空")
    private String  type;
   // @NotBlank(message = "试听课时间不能为空")
    private Date    courseTime;
    @NotBlank(message = "试听课地址不能为空")
    private String   address;
    @NotBlank(message = "学科不能为空")
    private String  subject;
    @NotBlank(message = "后期上课不能为空")
    private String  laterClass;
    @NotNull(message = "老师性别不能为空")
    private Integer  teacherGender;
    @NotBlank(message = "老师性格不能为空")
    private String  teacherCharacter;
    @NotBlank(message = "老师其他不能为空")
    private String  teacherOther;
    @NotBlank(message = "课程顾问不能为空")
    private  String courseConsultant;
    @NotNull(message = "节点id不能为空")
    private Integer nodeId;

}
