package com.dataee.tutorserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/5 9:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductAttribute {
    private Integer id;
    private String name;
    private String type;
    private Boolean required;
    private Integer productId;
    private Product product;
}
