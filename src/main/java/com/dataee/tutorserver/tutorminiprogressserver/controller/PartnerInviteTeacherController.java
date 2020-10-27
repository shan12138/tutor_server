package com.dataee.tutorserver.tutorminiprogressserver.controller;

import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.entity.Partner;
import com.dataee.tutorserver.entity.Teacher;
import com.dataee.tutorserver.entity.TeacherInvitation;
import com.dataee.tutorserver.tutorminiprogressserver.bean.InvitedTeacherCountAndMoney;
import com.dataee.tutorserver.tutorminiprogressserver.dao.InvitedTeacherMapper;
import com.dataee.tutorserver.tutorminiprogressserver.service.InvitedTeacherService;
import com.dataee.tutorserver.tutorteacherserver.bean.TeacherInvite;
import com.dataee.tutorserver.tutorteacherserver.service.ITeacherInviteService;
import com.dataee.tutorserver.utils.ResultUtil;
import com.dataee.tutorserver.utils.SecurityUtil;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 合伙人邀请老师
 */
@RestController
public class PartnerInviteTeacherController {
    @Autowired
    private InvitedTeacherService invitedTeacherService;

    @Autowired
    private  ITeacherInviteService teacherInviteService;

    /**
     * 老师邀请老师
     */
    @ApiOperation(value = "小程序端邀请教员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teacherInvite", value = "报名信息体", required = true,
                    dataType = "TeacherInvite", paramType = "body")
    })
    @PutMapping("/partner/invite")
    public ResponseEntity partnerTeacherInvite(@RequestBody @Valid TeacherInvitation teacherInvitation )
            throws BaseControllerException, BaseServiceException {
        teacherInviteService.insertTeacherInvite(teacherInvitation);
        return ResultUtil.success();
    }

    /**
     * 受邀老师的列表
     */
    @ApiOperation(value = "合伙人获取受邀老师的列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit", value = "需要获取的信息的条数", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "需要获取的页（从1开始）", required = true, dataType = "Integer", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = TeacherInvite.class, responseContainer = "List")
    })
    @GetMapping("/partner/inviteList")
    public ResponseEntity getInviteList(@RequestParam Integer limit, @RequestParam Integer pageNo) throws BaseControllerException {
        Page page = new Page(limit, pageNo);
        Integer id = SecurityUtil.getPersonId();
        Integer partnerId = invitedTeacherService.getPartnerId(id);
        return ResultUtil.success(invitedTeacherService.getTeachersById(partnerId,page));
    }

    /**
     * 合伙人获取邀请老师的钱和数量
     */
    @ApiOperation(value = "合伙人获取邀请老师的钱和数量")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = InvitedTeacherCountAndMoney.class, responseContainer = "List")
    })
    @GetMapping("/partner/inviteMoneyAndCount")
    public ResponseEntity getInviteMoneyAndCount() throws BaseControllerException {
        Integer id = SecurityUtil.getPersonId();
        Integer partnerId = invitedTeacherService.getPartnerId(id);
        return ResultUtil.success(invitedTeacherService.teacherCountAndMoney(partnerId));
    }
}
