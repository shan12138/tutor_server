package com.dataee.tutorserver.tutoradminserver.patriarchmanage.controller;

import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.entity.ParentInvitation;
import com.dataee.tutorserver.entity.Student;
import com.dataee.tutorserver.tutoradminserver.patriarchmanage.service.IParentInvitationService;
import com.dataee.tutorserver.tutorpatriarchserver.bean.ParentInvitationRequestBean;
import com.dataee.tutorserver.utils.ResultUtil;
import com.dataee.tutorserver.utils.SecurityUtil;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/7 10:28
 */
@Api("管理员端咨询师查看家长邀请页面模块")
@RestController
@RequestMapping("/")
public class ParentInvitationController {
    @Autowired
    private IParentInvitationService parentInvitationService;

    @ApiOperation(value = "咨询师查看分配给自己已匹配过家长账号的被邀请家长信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit", value = "需要获取的信息的条数", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "需要获取的页（从1开始）", required = true, dataType = "Integer", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Student.class, responseContainer = "List")
    })
    @RequiresPermissions("parent/invitation:list")
    @GetMapping(value = "consultant/parent/invitation")
    public ResponseEntity getInvitationList(@RequestParam Integer limit, @RequestParam Integer pageNo) throws BaseControllerException {
        Integer adminId = SecurityUtil.getPersonId();
        Page page = new Page(limit, pageNo);
        NewPageInfo<ParentInvitation> invitationList = parentInvitationService.getInvitationList(adminId, page);
        return ResultUtil.success(invitationList);
    }

    @ApiOperation(value = "修改家长邀请状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要修改状态的家长列表中的id", required = true,
                    dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "state", value = "状态(跟进中，已预约，已签约)", required = true,
                    dataType = "String", paramType = "query")
    })
    @RequiresPermissions("parent/invitation:update")
    @PostMapping(value = "/parent/invitation/{id}")
    public ResponseEntity updateInviteParentAdminState(@PathVariable("id") Integer id, @RequestParam String state) {
        parentInvitationService.updateInviteParentAdminState(id, state);
        return ResultUtil.success();
    }
}
