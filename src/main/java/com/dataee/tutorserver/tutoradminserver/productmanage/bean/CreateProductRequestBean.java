package com.dataee.tutorserver.tutoradminserver.productmanage.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/5 9:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequestBean {
    private String name;
    private List<UpdateProductRequestBean> productAttributeList;
}
