package com.dataee.tutorserver.tutoradminserver.subjectandgrademanage.controller;

import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.entity.Grade;
import com.dataee.tutorserver.entity.Subject;
import com.dataee.tutorserver.tutoradminserver.statemanage.service.IStateManageService;
import com.dataee.tutorserver.tutoradminserver.subjectandgrademanage.bean.SubjectGradeBean;
import com.dataee.tutorserver.tutoradminserver.subjectandgrademanage.service.ISubjectAndGradeManageService;
import com.dataee.tutorserver.utils.ResultUtil;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 数据管理（科目和年级管理)
 *
 * @Author ChenShanShan
 * @CreateDate 2019/5/7 23:07
 */
@Api(value = "管理员端数据管理模块")
@RestController
@RequestMapping("/")
public class SubjectAndGradeManageController {
    @Autowired
    private ISubjectAndGradeManageService subjectAndGradeManageService;
    @Autowired
    private IStateManageService stateManageService;

    /**
     * 获取所有科目
     *
     * @return
     */
    @ApiOperation(value = "管理员获取所有科目")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Subject.class, responseContainer = "List")
    })
    @GetMapping("/subject")
    public ResponseEntity getAllSubject(@RequestParam Integer pageNo, @RequestParam Integer limit) {
        Page page = new Page(limit, pageNo);
        return ResultUtil.success(subjectAndGradeManageService.getAllSubject(page));
    }

    @ApiOperation(value = "管理员修改科目优先级")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "subject", value = "修改信息体", required = true,
                    dataType = "SubjectGradeBean", paramType = "body")
    })
    @RequiresPermissions("subject:update")
    @PutMapping("/subject")
    public ResponseEntity changeSubject(@RequestBody @Valid SubjectGradeBean subject) throws BaseServiceException {
        subjectAndGradeManageService.changeData("subject", subject.getId(), subject.getPriority());
        return ResultUtil.success();
    }

    /**
     * 管理员端修改科目状态
     *
     * @param id
     * @return
     * @throws BaseServiceException
     */
    @ApiOperation(value = "管理员端修改科目状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "科目id", required = true,
                    dataType = "Integer", paramType = "path")
    })
    @RequiresPermissions("subject:update")
    @PutMapping("/subject/state/{id}")
    public ResponseEntity changeSubjectState(@PathVariable("id") @NotNull(message = "科目ID不能为空") Integer id) throws BaseServiceException, BaseControllerException {
        stateManageService.changeDataState("subject", id);
        return ResultUtil.success();
    }

    /**
     * 管理员新增科目
     *
     * @param name
     * @return
     * @throws BaseServiceException
     */
    @ApiOperation(value = "管理员新增科目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "科目名称", required = true,
                    dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "priority", value = "科目名称", required = true,
                    dataType = "Integer", paramType = "query")
    })
    @RequiresPermissions("subject:create")
    @PostMapping("/subject")
    public ResponseEntity addSubject(@RequestParam @NotBlank(message = "科目名不能为空") String name,
                                     @RequestParam @NotNull(message = "科目优先级不能为空") Integer priority) throws BaseServiceException {
        subjectAndGradeManageService.addData("subject", name, priority);
        return ResultUtil.success();
    }

    /**
     * 获取所有年级
     *
     * @return
     */
    @ApiOperation(value = "管理员获取所有年级")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Grade.class, responseContainer = "List")
    })
    @GetMapping("/grade")
    public ResponseEntity getAllGrade(@RequestParam Integer pageNo, @RequestParam Integer limit) {
        Page page = new Page(limit, pageNo);
        return ResultUtil.success(subjectAndGradeManageService.getAllGrade(page));
    }

    @ApiOperation(value = "管理员修改年级优先级")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "grade", value = "修改信息体", required = true,
                    dataType = "Grade", paramType = "body")
    })
    @RequiresPermissions("grade:update")
    @PutMapping("/grade")
    public ResponseEntity changeGrade(@RequestBody @Valid SubjectGradeBean grade) throws BaseServiceException {
        subjectAndGradeManageService.changeData("grade", grade.getId(), grade.getPriority());
        return ResultUtil.success();
    }

    /**
     * 修改年级状态
     *
     * @param id
     * @return
     * @throws BaseServiceException
     */
    @ApiOperation(value = "管理员端修改年级状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "年级id", required = true,
                    dataType = "Integer", paramType = "path")
    })
    @RequiresPermissions("grade:update")
    @PutMapping("/grade/state/{id}")
    public ResponseEntity changeGradeState(@PathVariable("id") @NotNull(message = "年级ID不能为空") Integer id) throws BaseServiceException, BaseControllerException {
        stateManageService.changeDataState("grade", id);
        return ResultUtil.success();
    }

    /**
     * 新增年级
     *
     * @param name
     * @return
     * @throws BaseControllerException
     * @throws BaseServiceException
     */
    @ApiOperation(value = "管理员新增年级")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "年级名称", required = true,
                    dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "priority", value = "年级优先级", required = true,
                    dataType = "Integer", paramType = "query")
    })
    @RequiresPermissions("grade:create")
    @PostMapping("/grade")
    public ResponseEntity addGrade(@RequestParam @NotBlank(message = "年级名不能为空") String name,
                                   @RequestParam @NotNull(message = "年级优先级不能为空") Integer priority) throws BaseServiceException {
        subjectAndGradeManageService.addData("grade", name, priority);
        return ResultUtil.success();
    }
}
