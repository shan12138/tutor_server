package com.dataee.tutorserver.tutoradminserver.productmanage.service;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.entity.ProductAttribute;
import com.dataee.tutorserver.tutoradminserver.productmanage.bean.CreateProductRequestBean;
import com.dataee.tutorserver.tutoradminserver.productmanage.bean.UpdateProductRequestBean;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/4 18:36
 */
public interface IProductManageService {
    NewPageInfo getProductList(Page page);

    void createProduct(String name);

    List<ProductAttribute> getProductAttribute(int id);

    void updateProductAttribute(UpdateProductRequestBean productAttribute) throws BaseServiceException;

    void updateProduct(int id, String name) throws BaseServiceException;

    void changeProductState(int id, String status) throws BaseServiceException;

    void deleteProductAttribute(int id) throws BaseServiceException;

    void createProductAttribute(ProductAttribute productAttribute) throws BaseServiceException;
}
