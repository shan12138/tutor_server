package com.dataee.tutorserver.tutoradminserver.electricsalemanage.controller;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.entity.IntentionCustomer;
import com.dataee.tutorserver.tutoradminserver.electricsalemanage.service.IElectricSaleManageService;
import com.dataee.tutorserver.tutoradminserver.electricsalemanage.service.impl.ElectricSaleManageServiceImpl;
import com.dataee.tutorserver.utils.ResultUtil;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 电销管理模块
 *
 * @Author ChenShanShan
 * @CreateDate 2019/5/29 21:01
 */
@RequiresRoles(value = {"superAdmin", "electricitySalesman"}, logical = Logical.OR)
@RequiresPermissions("auth:ed")
@RestController
@RequestMapping("/admin")
public class ElectricSaleManageController {
    private final Logger logger = LoggerFactory.getLogger(ElectricSaleManageController.class);
    @Autowired
    private IElectricSaleManageService electricSaleManageService;

    public void setElectricSaleManageService(ElectricSaleManageServiceImpl electricSaleManageService) {
        this.electricSaleManageService = electricSaleManageService;
    }

    private final String COMPLETEd = "已完成";
    private final String NOT_COMPLETED = "未完成";

    //@PostMapping("/electricTask")
    //public ResponseEntity importElectricTask(@RequestParam(value = "file") @NotNull MultipartFile file) throws BaseControllerException, IOException {
    //    if (file == null) {
    //        throw new IllegalParameterException("上传文件异常");
    //    }
    //    //处理文件
    //    //验证参数合法性
    //    String fullName = file.getOriginalFilename();
    //    String suffix = FileTypeUtil.getSuffix(fullName);
    //    if (FileTypeUtil.isExcel(suffix)) {
    //        List<SaleTaskExcelBean> saleTaskExcelBeanList = ExcelUtil.analysisExcel(SaleTaskExcelBean.class, file.getInputStream(), 1, 1);
    //        return ResultUtil.success(saleTaskExcelBeanList);
    //    } else {
    //        logger.info("不是excel");
    //        return ResultUtil.error(HttpStatus.BAD_REQUEST);
    //    }
    //}

    /**
     * 新增意向客户
     */
    @ApiOperation("新增意向客户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "intentionCustomer", value = "意向客户信息体", required = true,
                    dataType = "IntentionCustomer", paramType = "body"),
    })
    @PostMapping("/intentionCustomer")
    public ResponseEntity createIntentionCustomer(@RequestBody IntentionCustomer intentionCustomer) throws BaseServiceException {
        electricSaleManageService.createIntentionCustomer(intentionCustomer);
        return ResultUtil.success();
    }

    /**
     * 查看意向客户
     *
     * @return
     */
    @ApiOperation("查看意向客户列表")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = IntentionCustomer.class, responseContainer = "List")
    })
    @RequiresRoles(value = {"superAdmin", "electricitySalesman","parentAdmin"}, logical = Logical.OR)
    @GetMapping("/intentionCustomer")
    public ResponseEntity getIntentionCustomer(@RequestParam Integer pageNo, @RequestParam Integer limit) {
        Page page = new Page(limit, pageNo);
        return ResultUtil.success(electricSaleManageService.getIntentionCustomer(page));
    }

    /**
     * 查看意向客户详情信息
     *
     * @return
     */
    @ApiOperation("查看意向客户详情信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true,
                    dataType = "Integer", paramType = "path"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = IntentionCustomer.class)
    })
    @RequiresRoles(value = {"superAdmin", "electricitySalesman","parentAdmin"}, logical = Logical.OR)
    @GetMapping("/intentionCustomer/detail/{id}")
    public ResponseEntity getIntentionCustomerDetailInfo(@PathVariable("id") Integer id) {
        return ResultUtil.success(electricSaleManageService.getIntentionCustomerDetailInfo(id));
    }

    /**
     * 修改意向客户
     *
     * @return
     */
    @ApiOperation("编辑意向客户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "intentionCustomer", value = "修改信息体", required = true,
                    dataType = "IntentionCustomer", paramType = "body"),
    })
    @PutMapping("/intentionCustomer")
    public ResponseEntity changeCustomer(@RequestBody IntentionCustomer intentionCustomer) throws BaseServiceException {
        electricSaleManageService.changeCustomer(intentionCustomer);
        return ResultUtil.success();
    }

    @ApiOperation("推送意向客户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true,
                    dataType = "Integer", paramType = "path"),
    })
    @PutMapping("/intentionCustomer/{id}")
    public ResponseEntity changeCustomerState(@PathVariable Integer id) {
        electricSaleManageService.changeCustomerState(id);
        return ResultUtil.success();
    }

    @GetMapping("/intentionCustomer/queryCondition")
    public ResponseEntity queryAdmin(@RequestParam("queryCondition") String queryCondition,
                                     @RequestParam("parentSex") String parentSex,
                                     @RequestParam("teacherSex") String teacherSex,
                                     @RequestParam("state") String state,
                                     @RequestParam Integer pageNo, @RequestParam Integer limit) {
        Page page = new Page(limit, pageNo);
        return ResultUtil.success(electricSaleManageService.queryIntentionCustomer(queryCondition,
                parentSex, teacherSex, state, page));
    }
}
