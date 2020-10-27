package com.dataee.tutorserver.tutorpatriarchserver.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/4 23:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAddressRequestBean {
    private Integer parentId;
    private Integer addressId;
    @NotBlank(message = "新地区不能为空")
    private String newRegion;
    @NotBlank(message = "新详细地址不能为空")
    private String newAddressDetail;
}
