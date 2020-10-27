package com.dataee.tutorserver.tutorteacherserver.controller;


import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.entity.TeacherInvitation;
import com.dataee.tutorserver.tutorteacherserver.bean.TeacherInvite;
import com.dataee.tutorserver.tutorteacherserver.bean.TeacherInviteCount;
import com.dataee.tutorserver.tutorteacherserver.service.ITeacherInviteService;
import com.dataee.tutorserver.utils.ResultUtil;
import com.dataee.tutorserver.utils.SecurityUtil;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@Api(value = "管理员端的教师管理模块")
@RequiresRoles("teacher")
@RestController
@RequestMapping("/")
public class TeacherInviteController {
    @Autowired
    private ITeacherInviteService teacherInviteService;

    /**
     * 老师邀请老师
     */
    @ApiOperation(value = "教员端邀请教员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teacherInvite", value = "报名信息体", required = true,
                    dataType = "TeacherInvite", paramType = "body")
    })
    @PutMapping("/teacher/invite")
    public ResponseEntity teacherInvite(@RequestBody @Valid TeacherInvitation teacherInvitation )
            throws BaseControllerException, BaseServiceException {
        Integer teacherId = SecurityUtil.getPersonId();
        teacherInviteService.teacherInviteTeacher(teacherInvitation,teacherId);
        return ResultUtil.success();
    }

    /**
     * 受邀老师的列表
     */
    @ApiOperation(value = "教师获取受邀老师的列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit", value = "需要获取的信息的条数", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "需要获取的页（从1开始）", required = true, dataType = "Integer", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = TeacherInvite.class, responseContainer = "List")
    })
    @GetMapping("/teacher/inviteList")
    public ResponseEntity getInviteList(@RequestParam Integer limit, @RequestParam Integer pageNo) throws BaseControllerException {
        Page page = new Page(limit, pageNo);
        Integer teacherId = SecurityUtil.getPersonId();
        return ResultUtil.success(teacherInviteService.getTeachersById(teacherId,page));
    }
    /**
     * 受邀老师的数量
     */
    @ApiOperation(value = "教师获取受邀老师的数量")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = TeacherInviteCount.class, responseContainer = "List")
    })
    @GetMapping("/teacher/inviteCount")
    public ResponseEntity getInviteCount() throws BaseControllerException {
        Integer teacherId = SecurityUtil.getPersonId();
        return ResultUtil.success(teacherInviteService.getInvitedCount(teacherId));
    }
}