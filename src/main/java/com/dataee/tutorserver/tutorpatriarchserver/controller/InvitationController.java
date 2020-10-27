package com.dataee.tutorserver.tutorpatriarchserver.controller;

import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.commons.errorInfoenum.ControllerExceptionEnum;
import com.dataee.tutorserver.entity.ParentInvitation;
import com.dataee.tutorserver.entity.Student;
import com.dataee.tutorserver.tutorpatriarchserver.bean.ParentInvitationNum;
import com.dataee.tutorserver.tutorpatriarchserver.bean.ParentInvitationRequestBean;
import com.dataee.tutorserver.tutorpatriarchserver.service.IInvitationService;
import com.dataee.tutorserver.utils.ResultUtil;
import com.dataee.tutorserver.utils.SecurityUtil;
import com.google.gson.JsonObject;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/7 10:25
 */
@Api("家长端邀请家长模块")
@RestController
@RequestMapping("/")
@RequiresRoles("parent")
public class InvitationController {
    @Autowired
    private IInvitationService invitationService;

    @ApiOperation(value = "邀请家长")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentInvitation", value = "邀请家长信息体", required = true,
                    dataType = "ParentInvitationRequestBean", paramType = "body")
    })
    @PostMapping(value = "/parent/invitation")
    public ResponseEntity inviteParent(@RequestBody ParentInvitationRequestBean parentInvitation) throws BaseControllerException {
        // 获取当前用户的parentId
        Integer parentId = SecurityUtil.getPersonId();
        invitationService.inviteParent(parentId, parentInvitation);
        return ResultUtil.success();
    }

    @ApiOperation(value = "家长端邀请家长信息列表(邀请的)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit", value = "需要获取的信息的条数", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "需要获取的页（从1开始）", required = true, dataType = "Integer", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Student.class, responseContainer = "List")
    })
    @GetMapping(value = "/parent/invitation")
    public ResponseEntity getInvitationList(@RequestParam Integer limit, @RequestParam Integer pageNo) throws BaseControllerException {
        Integer parentId = SecurityUtil.getPersonId();
        Page page = new Page(limit, pageNo);
        NewPageInfo<ParentInvitation> invitationList = invitationService.getInvitationList(parentId, page);
        return ResultUtil.success(invitationList);
    }

    @ApiOperation(value = "家长端邀请家长信息列表(已经注册的)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit", value = "需要获取的信息的条数", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "需要获取的页（从1开始）", required = true, dataType = "Integer", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Student.class, responseContainer = "List")
    })
    @GetMapping(value = "/parent/register/invitation")
    public ResponseEntity getInvitationRegisterList(@RequestParam Integer limit, @RequestParam Integer pageNo) throws BaseControllerException {
        Integer parentId = SecurityUtil.getPersonId();
        Page page = new Page(limit, pageNo);
        NewPageInfo<ParentInvitation> invitationList = invitationService.getInvitationRegisterList(parentId, page);
        return ResultUtil.success(invitationList);
    }

    @ApiOperation("获取邀请家长的个数")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = ParentInvitationNum.class)
    })
    @GetMapping("parent/invite/num")
    public ResponseEntity getParentInvitationNum() throws BaseControllerException {
        Integer parentId = SecurityUtil.getPersonId();
        ParentInvitationNum parentInvitationNum = invitationService.getParentInvitationNum(parentId);
        return ResultUtil.success(parentInvitationNum);
    }
}
