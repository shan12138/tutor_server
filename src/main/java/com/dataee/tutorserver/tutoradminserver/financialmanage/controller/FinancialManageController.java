package com.dataee.tutorserver.tutoradminserver.financialmanage.controller;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.entity.ParentInvitation;
import com.dataee.tutorserver.entity.WithdrawalsRecord;
import com.dataee.tutorserver.tutoradminserver.financialmanage.service.IFinancialManageService;
import com.dataee.tutorserver.utils.ResultUtil;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/12 13:39
 */
@Api("财务管理模块")
@RestController
@RequestMapping("/")
public class FinancialManageController {
    @Autowired
    private IFinancialManageService financialManageService;

    @ApiOperation(value = "获得提现申请列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "页码", required = true,
                    dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "个数", required = true,
                    dataType = "int", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = WithdrawalsRecord.class, responseContainer = "List")
    })
    @GetMapping("/withdrawList")
    @RequiresPermissions("withdraw:list")
    public ResponseEntity getWithdrawList(@RequestParam Integer pageNo, @RequestParam Integer limit,
                                          @RequestParam(required = false) String keyWord, @RequestParam(required = false) Integer partnerId,
                                          @RequestParam(required = false) String state) {
        Page page = new Page(limit, pageNo);
        return ResultUtil.success(financialManageService.getWithdrawList(page, keyWord, partnerId, state));
    }

    @ApiOperation(value = "线上发放提现")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "提现申请id", required = true,
                    dataType = "int", paramType = "path"),
    })
    @PostMapping("/withdraw/online/{id}")
    @RequiresPermissions("withdraw/online:create")
    public ResponseEntity onlineDistribution(@PathVariable("id") int id) throws BaseServiceException {
        financialManageService.onlineDistribution(id);
        return ResultUtil.success();
    }

    @ApiOperation(value = "线下发放提现")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "提现申请id", required = true,
                    dataType = "int", paramType = "path"),
    })
    @PostMapping("/withdraw/offline/{id}")
    @RequiresPermissions("withdraw/offline:create")
    public ResponseEntity offlineDistribution(@PathVariable("id") int id) throws BaseServiceException {
        financialManageService.offlineDistribution(id);
        return ResultUtil.success();
    }

    @ApiOperation(value = "获得邀请家长赠礼列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "页码", required = true,
                    dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "个数", required = true,
                    dataType = "int", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = ParentInvitation.class, responseContainer = "List")
    })
    @GetMapping("/invitationGift/parent")
    @RequiresPermissions("invitationGift/parent:list")
    public ResponseEntity getInvitationGiftParentList(@RequestParam Integer pageNo, @RequestParam Integer limit,
                                                      @RequestParam(required = false) String keyWord, @RequestParam(required = false) Integer parentId,
                                                      @RequestParam(required = false) String state) {
        Page page = new Page(limit, pageNo);
        return ResultUtil.success(financialManageService.getInvitationGiftParentList(page, keyWord, parentId, state));
    }

    @ApiOperation(value = "线下发放家长赠礼")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "赠礼id", required = true,
                    dataType = "int", paramType = "path"),
    })
    @PutMapping("invitationGift/parent/{id}")
    @RequiresPermissions("invitationGift/parent:update")
    public ResponseEntity invitationParentGiftDistribution(@PathVariable("id") int id) throws BaseServiceException {
        financialManageService.invitationParentGiftDistribution(id);
        return ResultUtil.success();
    }
}
