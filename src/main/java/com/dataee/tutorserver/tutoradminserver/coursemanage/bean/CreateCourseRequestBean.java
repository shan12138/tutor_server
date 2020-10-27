package com.dataee.tutorserver.tutoradminserver.coursemanage.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/21 15:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCourseRequestBean {
    private Integer id;
    private Integer courseId;
    @NotBlank(message = "年级不能为空")
    private String grade;
    @NotBlank(message = "科目不能为空")
    private String subject;
    @NotNull(message = "家长ID不能为空")
    private Integer parentId;
    @NotNull(message = "学生ID不能为空")
    private Integer studentId;
    @NotNull(message = "总课时不能为空")
    private Double totalClassHour;
  //  @NotNull(message = "赠送课时不能为空")
    private Double freeClassHour;
    private Boolean isFree;
    @NotNull(message = "课时单价不能为空")
    private Integer price;
    @NotNull(message = "产品不能为空")
    private Integer productId;
    @NotNull(message = "折扣不能为空")
    private Double discount;
//    @NotNull(message = "家庭地址ID不能为空")
    private Integer addressId;
    @NotNull(message = "排课主任不能为空")
    private Integer courseAdminId;
    @NotNull(message = "班主任不能为空")
    private Integer headAdminId;
 //   @NotNull(message = "学管师不能为空")
   // private Integer studyAdminId;
}
