package com.dataee.tutorserver.tutoradminserver.electricsalemanage.controller;

import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.entity.IntentionCustomer;
import com.dataee.tutorserver.tutoradminserver.electricsalemanage.service.IElectricSaleManageService;
import com.dataee.tutorserver.utils.ResultUtil;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Author ChenShanShan
 * 课程管理员查看推送的意向客户
 * @CreateDate 2019/6/22 17:12
 */
@RequiresRoles(value = {"superAdmin", "electricitySalesman"}, logical = Logical.OR)
@RequiresPermissions("auth:ed")
@RestController
@RequestMapping("/courseAdmin")
public class ElectricSaleCourseManageController {
    @Autowired
    private IElectricSaleManageService electricSaleManageService;

    @ApiOperation("查看意向客户列表")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = IntentionCustomer.class, responseContainer = "List")
    })
    @GetMapping("/intentionCustomer")
    public ResponseEntity getCourseIntentionCustomer(@RequestParam Integer pageNo, @RequestParam Integer limit,
                                                     @RequestParam("queryCondition")String queryCondition, @RequestParam("parentSex")String parentSex,
                                                     @RequestParam("tutorSex")String tutorSex


                                                                                                            ) {
        Page page = new Page(limit, pageNo);
        return ResultUtil.success(electricSaleManageService.getCourseIntentionCustomer(page,queryCondition,parentSex,tutorSex));
    }

    @ApiOperation("查看意向客户详情信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true,
                    dataType = "Integer", paramType = "path"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = IntentionCustomer.class)
    })
    @GetMapping("/intentionCustomer/detail/{id}")
    public ResponseEntity getIntentionCustomerDetailInfo(@PathVariable("id") Integer id) {
        return ResultUtil.success(electricSaleManageService.getIntentionCustomerDetailInfo(id));
    }
}
