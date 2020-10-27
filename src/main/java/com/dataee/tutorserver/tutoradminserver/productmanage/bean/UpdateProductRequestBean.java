package com.dataee.tutorserver.tutoradminserver.productmanage.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/5 10:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductRequestBean {
    private Integer id;
    private String name;
    private String type;
    private Boolean required;
}
