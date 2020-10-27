package com.dataee.tutorserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/5 9:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private Integer id;
    private String productName;
    private String status;
    private List<ProductAttribute> productAttributeList;

    public Product(String productName, String status) {
        this.productName = productName;
        this.status = status;
    }
}
