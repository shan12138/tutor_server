package com.dataee.tutorserver.tutorpatriarchserver.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 家长注册时填写的子女信息
 *
 * @Author ChenShanShan
 * @CreateDate 2019/5/10 17:54
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollChildInfoRequestBean {
    @NotBlank(message = "学生姓名不能为空")
    private String studentName;
    @NotNull(message = "学生性别不能为空")
    private Integer sex;
    @NotBlank(message = "学生年级不能为空")
    private String grade;
    @NotBlank(message = "学校不能为空")
    private String school;
    @NotBlank(message = "学生身份证号不能为空")
    private String idCard;
    private List<String> weakDiscipline;
    private Integer parentId;
    private Integer studentId;
}
