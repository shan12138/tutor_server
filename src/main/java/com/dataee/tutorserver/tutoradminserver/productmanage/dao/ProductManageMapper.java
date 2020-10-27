package com.dataee.tutorserver.tutoradminserver.productmanage.dao;

import com.dataee.tutorserver.entity.Product;
import com.dataee.tutorserver.entity.ProductAttribute;
import com.dataee.tutorserver.tutoradminserver.productmanage.bean.UpdateProductRequestBean;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/4 18:36
 */
@Mapper
public interface ProductManageMapper {
    @Select("select * from product")
    List<Product> getProductList();

    @Insert("insert into product(product_name, status) values(#{productName}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void createProduct(Product newProduct);

    @Insert("insert into product_attribute(name, type, required, product_id) values(#{name}, #{type}, #{required}, #{productId})")
    void createProductAttribute(ProductAttribute productAttribute);

    @Select("select * from product_attribute where product_id = #{id}")
    List<ProductAttribute> getProductAttribute(int id);

    @Update("update product_attribute set name = #{name}, type = #{type}, required = #{required} where id = #{id}")
    void updateProductAttribute(UpdateProductRequestBean productAttribute);

    @Select("select * from product where id = #{id}")
    Product getProductById(int id);

    @Update("update product set product_name = #{name} where id = #{id}")
    void updateProduct(@Param("id") int id, @Param("name") String name);

    @Update("update product set status = #{status} where id = #{id}")
    void changeProductState(@Param("id") int id, @Param("status") String status);

    @Select("select * from product_attribute where id = #{id}")
    ProductAttribute getProductAttributeById(Integer id);

    @Delete("delete from product_attribute where id = #{id}")
    void deleteProductAttribute(int id);
}
