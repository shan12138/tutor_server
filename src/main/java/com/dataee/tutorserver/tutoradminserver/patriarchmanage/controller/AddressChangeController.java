package com.dataee.tutorserver.tutoradminserver.patriarchmanage.controller;

import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.tutoradminserver.messagemanage.bean.InfoChangeVerifyRequestBean;
import com.dataee.tutorserver.tutoradminserver.patriarchmanage.bean.AddressChangeResponseBean;
import com.dataee.tutorserver.tutoradminserver.patriarchmanage.service.IAddressChangeService;
import com.dataee.tutorserver.utils.ResultUtil;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 审核家长端家庭地址
 *
 * @Author ChenShanShan
 * @CreateDate 2019/5/5 20:44
 */
@Api(value = "管理员端地址审核模块")
@RestController
@RequestMapping("/")
public class AddressChangeController {
    @Autowired
    private IAddressChangeService addressChangeService;

    /**
     * 获得所有地址变更信息（包括审核通过，未审核以及驳回的）
     *
     * @return
     */
    @ApiOperation(value = "管理员获得所有地址变更信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = AddressChangeResponseBean.class, responseContainer = "List")
    })
    @RequiresPermissions("addressChange:list")
    @GetMapping("/addressChangeInfo")
    public ResponseEntity getAllAddressChange(@RequestParam Integer pageNo, @RequestParam Integer limit) {
        Page page = new Page(limit, pageNo);
        return ResultUtil.success(addressChangeService.getAllAddressChange(page));
    }

    /**
     * 管理员创建通过和不通过家长地址变更的审核信息
     *
     * @param infoChangeVerifyRequestBean
     * @return
     * @throws BaseControllerException
     * @throws BaseServiceException
     */
    @ApiOperation(value = "管理员创建通过和不通过课程地址变更的审核信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "infoChangeVerifyRequestBean", value = "审核信息体", required = true,
                    dataType = "InfoChangeVerifyRequestBean", paramType = "body")
    })
    @RequiresPermissions("addressChange:update")
    @PostMapping("/addressVerifyInfo")
    public ResponseEntity verifyAddressChange(@RequestBody @Valid InfoChangeVerifyRequestBean
                                                      infoChangeVerifyRequestBean) throws BaseControllerException, BaseServiceException {
        if (infoChangeVerifyRequestBean.getAccepted() == 1) {
            addressChangeService.acceptAddressChange(infoChangeVerifyRequestBean);
        } else {
            addressChangeService.denyAddressChange(infoChangeVerifyRequestBean);
        }
        return ResultUtil.success();
    }

    /**
     * 查询地址变更信息
     *
     * @param teacher
     * @param studentName
     * @param state
     *
     * @return
     */
    @ApiOperation(value = "管理员查询地址变更信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryCondition", value = "查询条件", required = true,
                    dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "state", value = "查询地址状态", required = true,
                    dataType = "String", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = AddressChangeResponseBean.class, responseContainer = "List")
    })
    @GetMapping("addressChangeInfo/queryCondition")
    public ResponseEntity queryAddressChangeInfo(@RequestParam("teacher") String teacher,
                                                 @RequestParam("studentName") String studentName,
                                                 @RequestParam("state") String state,
                                                 @RequestParam Integer pageNo, @RequestParam Integer limit) {
        Page page = new Page(limit, pageNo);
        return ResultUtil.success(addressChangeService.queryAddressChangeInfo(teacher,studentName, state, page));
    }

}
