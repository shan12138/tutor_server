package com.dataee.tutorserver.tutoradminserver.productmanage.controller;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.entity.Product;
import com.dataee.tutorserver.entity.ProductAttribute;
import com.dataee.tutorserver.tutoradminserver.productmanage.bean.CreateProductRequestBean;
import com.dataee.tutorserver.tutoradminserver.productmanage.bean.UpdateProductRequestBean;
import com.dataee.tutorserver.tutoradminserver.productmanage.service.IProductManageService;
import com.dataee.tutorserver.utils.ResultUtil;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/4 18:35
 */
@Api(value = "产品管理模块")
@RestController
@RequestMapping("/")
public class ProductManageController {
    @Autowired
    private IProductManageService productManageService;

    @ApiOperation(value = "管理员获取产品列表")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Product.class, responseContainer = "List")
    })
    @GetMapping("/productList")
    public ResponseEntity getRoleList(@RequestParam Integer pageNo, @RequestParam Integer limit) {
        Page page = new Page(limit, pageNo);
        return ResultUtil.success(productManageService.getProductList(page));
    }

    @ApiOperation(value = "新建产品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "product", value = "新建产品信息体", required = true,
                    dataType = "string", paramType = "name")
    })
    @PostMapping("/product")
    @RequiresPermissions("product:create")
    public ResponseEntity createProduct(@RequestParam String name) {
        productManageService.createProduct(name);
        return ResultUtil.success();
    }

    @ApiOperation(value = "修改产品名称")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "产品id", required = true,
                    dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "name", value = "名称", required = true,
                    dataType = "String", paramType = "query")
    })
    @PutMapping("/product/name/{id}")
    @RequiresPermissions("product:update")
    public ResponseEntity updateProduct(@PathVariable("id") int id, @RequestParam String name) throws BaseServiceException {
        productManageService.updateProduct(id, name);
        return ResultUtil.success();
    }

    @ApiOperation(value = "禁用/启用产品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "产品id", required = true,
                    dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "status", value = "禁用/启用", required = true,
                    dataType = "String", paramType = "query"),
    })
    @PutMapping("/product/status/{id}")
    @RequiresPermissions("product:disableEnable")
    public ResponseEntity changeProductState(@PathVariable("id") int id, @RequestParam String status) throws BaseServiceException {
        productManageService.changeProductState(id, status);
        return ResultUtil.success();
    }

    @ApiOperation(value = "获取产品的字段列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "产品id", required = true,
                    dataType = "int", paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = ProductAttribute.class, responseContainer = "List")
    })
    @RequiresPermissions("product/attribute:list")
    @GetMapping("/product/attribute/{id}")
    public ResponseEntity getProductAttribute(@PathVariable("id") int id) {
        return ResultUtil.success(productManageService.getProductAttribute(id));
    }

    @ApiOperation(value = "新增产品的某个字段")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productAttribute", value = "新增产品信息体", required = true,
                    dataType = "ProductAttribute", paramType = "body")
    })
    @PostMapping("/product/attribute")
    @RequiresPermissions("product/attribute:create")
    public ResponseEntity createProductAttribute(@RequestBody ProductAttribute productAttribute)
            throws BaseServiceException {
        productManageService.createProductAttribute(productAttribute);
        return ResultUtil.success();
    }

    @ApiOperation(value = "修改产品的某个字段")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productAttribute", value = "修改产品信息体", required = true,
                    dataType = "UpdateProductRequestBean", paramType = "body")
    })
    @PutMapping("/product/attribute")
    @RequiresPermissions("product/attribute:update")
    public ResponseEntity updateProductAttribute(@RequestBody UpdateProductRequestBean productAttribute)
            throws BaseServiceException {
        productManageService.updateProductAttribute(productAttribute);
        return ResultUtil.success();
    }

    @ApiOperation(value = "删除产品的某个字段")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "产品字段id", required = true,
                    dataType = "int", paramType = "path")
    })
    @DeleteMapping("/product/attribute/{id}")
    @RequiresPermissions("product/attribute:delete")
    public ResponseEntity deleteProductAttribute(@PathVariable("id") int id)
            throws BaseServiceException {
        productManageService.deleteProductAttribute(id);
        return ResultUtil.success();
    }
}
