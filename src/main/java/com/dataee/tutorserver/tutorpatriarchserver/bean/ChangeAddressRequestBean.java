package com.dataee.tutorserver.tutorpatriarchserver.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/5 16:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeAddressRequestBean {
    @NotNull(message = "地址ID不能为空")
    private Integer addressId;
   // @NotNull(message = "课程ID不能为空")
    private  Integer  courseId;
    @NotBlank(message = "地区不能为空")
    private String newRegion;
    @NotBlank(message = "详细地址不能为空")
    private String newAddressDetail;
    private String oldRegion;
    private String oldAddressDetail;
}
