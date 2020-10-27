package com.dataee.tutorserver.tutoradminserver.teachermanage.controller;

import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.entity.TeacherInvitation;
import com.dataee.tutorserver.tutoradminserver.teachermanage.bean.InviteTeacherGift;
import com.dataee.tutorserver.tutoradminserver.teachermanage.bean.TeacherListResponseBean;
import com.dataee.tutorserver.tutoradminserver.teachermanage.service.TeacherInvitedService;
import com.dataee.tutorserver.utils.ResultUtil;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "管理员端的教师管理模块")
@RestController
@RequestMapping("/")
public class TeacherInvitedManageController {
    @Autowired
    private TeacherInvitedService teacherInvitedService;
    /**
     * 获取受邀教师列表
     *
     * @return
     */
    @ApiOperation(value = "管理员获取受邀教师列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前第几页", required = true,
                    dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "每页显示几条数据", required = true,
                    dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "queryCondition", value = "查询条件", required = false,
                    dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "telephone", value = "手机", required = false,
                    dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "partnerId", value = "所属人id", required = false,
                    dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "状态", required = false,
                    dataType = "String", paramType = "query"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = TeacherInvitation.class, responseContainer = "List")
    })
    @RequiresPermissions("partner/parent:list")
    @GetMapping("/admin/invited/teacherList")
    public ResponseEntity getInvitedTeacher(@RequestParam Integer pageNo, @RequestParam Integer limit,
                                            @RequestParam(required = false,value = "queryCondition") String queryCondition,
                                            @RequestParam(required = false,value = "telephone") String telephone,
                                            @RequestParam(required = false,value = "partnerId")Integer partnerId,
                                            @RequestParam(required = false,value = "status")String status) {
        Page page = new Page(limit, pageNo);
        return ResultUtil.success(teacherInvitedService.getTeacherInviteList(queryCondition, telephone, partnerId, status, page));
    }
    /**
     * 获取受邀教师列表
     *
     * @return
     */
    @ApiOperation(value = "管理员获取受邀教师赠礼列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前第几页", required = true,
                    dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "每页显示几条数据", required = true,
                    dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "queryCondition", value = "条件", required = false,
                    dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "invitePerson", value = "邀请人id", required = false,
                    dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "状态", required = false,
                    dataType = "String", paramType = "query"),

    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = InviteTeacherGift.class, responseContainer = "List")
    })
    @RequiresPermissions("invitationGift/teacher:list")
    @GetMapping("/admin/invitedGift/teacherList")
    public ResponseEntity getInvitedTeacher(@RequestParam Integer pageNo, @RequestParam Integer limit,
                                            @RequestParam(required = false,value = "queryCondition") String queryCondition,
                                            @RequestParam(required = false,value = "invitePerson") Integer invitePerson,
                                            @RequestParam(required = false,value = "status")String status) {
        Page page = new Page(limit, pageNo);
        return ResultUtil.success(teacherInvitedService.getTeacherGiftInviteList(queryCondition,invitePerson,status,page));
    }

    @ApiOperation(value = "修改邀请老师有礼状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要修改状态的邀请有礼列表中的id", required = true,
                    dataType = "int", paramType = "path"),
    })
    @RequiresPermissions("invitationGift/teacher:update")
    @PostMapping(value = "/teacher/invitation/{id}")
    public ResponseEntity updateInviteParentAdminState(@PathVariable("id") Integer id) {
        teacherInvitedService.updateInviteTeacherGiftStatus(id);
        return ResultUtil.success();
    }
}
