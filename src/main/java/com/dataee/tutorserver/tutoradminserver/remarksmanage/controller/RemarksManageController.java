package com.dataee.tutorserver.tutoradminserver.remarksmanage.controller;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.exceptions.SQLOperationException;
import com.dataee.tutorserver.entity.RemarkQuestion;
import com.dataee.tutorserver.tutoradminserver.remarksmanage.bean.ParentRecord;
import com.dataee.tutorserver.tutoradminserver.remarksmanage.service.IRemarksManageService;
import com.dataee.tutorserver.tutorpatriarchserver.bean.Remarks;
import com.dataee.tutorserver.tutorpatriarchserver.bean.RemarksListBean;
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
 * 管理员端的课堂评价管理
 *
 * @Author ChenShanShan
 * @CreateDate 2019/6/8 23:35
 */
@RestController
@RequestMapping("/admin")
public class RemarksManageController {
    @Autowired
    private IRemarksManageService remarksManageService;

    @ApiOperation(value = "获取某一堂课的教师的评价")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "lesson的ID", required = true,
                    dataType = "Integer", paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Remarks.class, responseContainer = "List")
    })
    @GetMapping("/teacherRecord/{id}")
    public ResponseEntity getTeacherRecord(@PathVariable("id") Integer id) {
        return ResultUtil.success(remarksManageService.getTeacherRecord(id));
    }

    @ApiOperation(value = "保存教师的评价")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "remarksList", value = "修改教师评价的信息体", required = true,
                    dataType = "RemarksListBean", paramType = "body")
    })
    @PutMapping("/teacherRecord")
    public ResponseEntity changeTeacherRecord(@RequestBody @Valid RemarksListBean remarksList) {
        remarksManageService.updateTeacherRecord(remarksList);
        return ResultUtil.success();
    }

    @ApiOperation(value = "发布教师的评价")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "remarksList", value = "修改教师评价的信息体", required = true,
                    dataType = "RemarksListBean", paramType = "body")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Remarks.class, responseContainer = "List")
    })
    @RequiresPermissions("course/parentEvaluation:update")
    @PutMapping("/teacherRecord/state")
    public ResponseEntity publishTeacherRecord(@RequestBody @Valid RemarksListBean remarksList) throws BaseServiceException {
        remarksManageService.updateTeacherRecord(remarksList);
        Integer number = remarksManageService.changeTeacherRecordState(remarksList.getLessonId());
        if (number != 1) {
            throw new SQLOperationException();
        }
        return ResultUtil.success();
    }

    @ApiOperation(value = "获取某一堂课的家长的评价（带有教师标签的记录）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "lesson的ID", required = true,
                    dataType = "Integer", paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = RemarkQuestion.class, responseContainer = "List")
    })
    @GetMapping("/parentRecord/{id}")
    public ResponseEntity getParentRecord(@PathVariable("id") Integer id) {
        return ResultUtil.success(remarksManageService.getParentRecord(id));
    }

    @ApiOperation(value = "保存家长的评价和对教员的标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentRecord", value = "修改家长评价的信息体", required = true,
                    dataType = "ParentRecord", paramType = "body")
    })
    @PutMapping("/parentRecord")
    public ResponseEntity saveParentRecord(@RequestBody @Valid ParentRecord parentRecord) {
        remarksManageService.updateParentRecord(parentRecord);
        return ResultUtil.success();
    }

    @ApiOperation(value = "发布家长的评价和对教员的标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentRecord", value = "修改家长评价的信息体", required = true,
                    dataType = "ParentRecord", paramType = "body")
    })
    @RequiresPermissions("course/teacherEvaluation:update")
    @PutMapping("/parentRecord/state")
    public ResponseEntity publishParentRecord(@RequestBody @Valid ParentRecord parentRecord) {
        remarksManageService.publishParentRecord(parentRecord);
        return ResultUtil.success();
    }
}
