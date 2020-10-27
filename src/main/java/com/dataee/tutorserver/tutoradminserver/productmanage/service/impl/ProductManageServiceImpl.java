package com.dataee.tutorserver.tutoradminserver.productmanage.service.impl;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.commons.errorInfoenum.ServiceExceptionsEnum;
import com.dataee.tutorserver.entity.Product;
import com.dataee.tutorserver.entity.ProductAttribute;
import com.dataee.tutorserver.tutoradminserver.productmanage.bean.CreateProductRequestBean;
import com.dataee.tutorserver.tutoradminserver.productmanage.bean.UpdateProductRequestBean;
import com.dataee.tutorserver.tutoradminserver.productmanage.dao.ProductManageMapper;
import com.dataee.tutorserver.tutoradminserver.productmanage.service.IProductManageService;
import com.dataee.tutorserver.utils.PageInfoUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLOutput;
import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/4 18:36
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ProductManageServiceImpl implements IProductManageService {
    @Autowired
    private ProductManageMapper productManageMapper;

    @Override
    public NewPageInfo getProductList(Page page) {
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<Product> product = productManageMapper.getProductList();
        PageInfo pageInfo = new PageInfo(product);
        NewPageInfo newPageInfo = PageInfoUtil.read(pageInfo);
        return newPageInfo;
    }

    @Override
    public void createProduct(String name) {
        Product newProduct = new Product(name, "启用");
        productManageMapper.createProduct(newProduct);
    }

    @Override
    public List<ProductAttribute> getProductAttribute(int id) {
        return productManageMapper.getProductAttribute(id);
    }

    @Override
    public void updateProductAttribute(UpdateProductRequestBean productAttribute) throws BaseServiceException {
        ProductAttribute productAttribute1 = productManageMapper.getProductAttributeById(productAttribute.getId());
        if(productAttribute1 == null) {
            throw new BaseServiceException(ServiceExceptionsEnum.PRODUCT_ATTRIBUTE_NOT_FOUND);
        }
        productManageMapper.updateProductAttribute(productAttribute);
    }

    @Override
    public void updateProduct(int id, String name) throws BaseServiceException {
        Product product = productManageMapper.getProductById(id);
        if(product == null) {
            throw new BaseServiceException(ServiceExceptionsEnum.PRODUCT_NOT_FOUND);
        }
        productManageMapper.updateProduct(id, name);
    }

    @Override
    public void changeProductState(int id, String status) throws BaseServiceException {
        Product product = productManageMapper.getProductById(id);
        if(product == null) {
            throw new BaseServiceException(ServiceExceptionsEnum.PRODUCT_NOT_FOUND);
        }
        productManageMapper.changeProductState(id, status);
    }

    @Override
    public void deleteProductAttribute(int id) throws BaseServiceException {
        ProductAttribute productAttribute = productManageMapper.getProductAttributeById(id);
        if(productAttribute == null) {
            throw new BaseServiceException(ServiceExceptionsEnum.PRODUCT_ATTRIBUTE_NOT_FOUND);
        }
        productManageMapper.deleteProductAttribute(id);
    }

    @Override
    public void createProductAttribute(ProductAttribute productAttribute) throws BaseServiceException {
        System.out.println(productAttribute.toString());
        Product product = productManageMapper.getProductById(productAttribute.getProductId());
        if(product == null) {
            throw new BaseServiceException(ServiceExceptionsEnum.PRODUCT_NOT_FOUND);
        }
        productManageMapper.createProductAttribute(productAttribute);
    }
}
