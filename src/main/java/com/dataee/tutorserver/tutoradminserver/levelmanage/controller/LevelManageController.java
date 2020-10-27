package com.dataee.tutorserver.tutoradminserver.levelmanage.controller;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.entity.ParentLevel;
import com.dataee.tutorserver.entity.Product;
import com.dataee.tutorserver.entity.TeacherLevel;
import com.dataee.tutorserver.tutoradminserver.levelmanage.service.ILevelManageService;
import com.dataee.tutorserver.utils.ResultUtil;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/5 11:22
 */
@Api(value = "等级管理模块")
@RestController
@RequestMapping("/")
public class LevelManageController {
    @Autowired
    private ILevelManageService levelManageService;

    @ApiOperation(value = "管理员获取某一个产品的家长等级列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "产品id", required = true,
                    dataType = "int", paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Product.class, responseContainer = "List")
    })
    @GetMapping("/parentLevelList/{id}")
    @RequiresPermissions("parent/level:list")
    public ResponseEntity getParentLevelList(@RequestParam Integer pageNo, @RequestParam Integer limit,
                                             @PathVariable("id") int id) throws BaseServiceException {
        Page page = new Page(limit, pageNo);
        return ResultUtil.success(levelManageService.getParentLevelList(page, id));
    }

    @ApiOperation(value = "新增家长等级")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "产品id", required = true,
                    dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "parentLevel", value = "家长等级", required = true,
                    dataType = "ParentLevel", paramType = "body")
    })
    @PostMapping("/parentLevel/{id}")
    @RequiresPermissions("parent/level:create")
    public ResponseEntity createParentLevel(@PathVariable("id") int id, @RequestBody ParentLevel parentLevel) throws BaseServiceException {
        levelManageService.createParentLevel(id, parentLevel);
        return ResultUtil.success();
    }

    @ApiOperation(value = "修改家长等级")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentLevel", value = "家长等级", required = true,
                    dataType = "ParentLevel", paramType = "body")
    })
    @PutMapping("/parentLevel")
    @RequiresPermissions("parent/level:update")
    public ResponseEntity updateParentLevel(@RequestBody ParentLevel parentLevel) throws BaseServiceException {
        levelManageService.updateParentLevel(parentLevel);
        return ResultUtil.success();
    }

    @ApiOperation(value = "删除家长等级")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "等级id", required = true,
                    dataType = "int", paramType = "path")
    })
    @DeleteMapping("/parentLevel/{id}")
    @RequiresPermissions("parent/level:delete")
    public ResponseEntity deleteParentLevel(@PathVariable int id) throws BaseServiceException {
        levelManageService.deleteParentLevel(id);
        return ResultUtil.success();
    }

    @ApiOperation(value = "管理员获取教师等级列表")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Product.class, responseContainer = "List")
    })
    @GetMapping("/teacherLevelList")
    @RequiresPermissions("teacher/level:list")
    public ResponseEntity getParentLevelList(@RequestParam Integer pageNo, @RequestParam Integer limit) {
        Page page = new Page(limit, pageNo);
        return ResultUtil.success(levelManageService.getTeacherLevelList(page));
    }

    @ApiOperation(value = "新增教师等级")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teacherLevel", value = "教师等级", required = true,
                    dataType = "TeacherLevel", paramType = "body")
    })
    @PostMapping("/teacherLevel")
    @RequiresPermissions("teacher/level:create")
    public ResponseEntity createTeacherLevel(@RequestBody TeacherLevel teacherLevel) {
        levelManageService.createTeacherLevel(teacherLevel);
        return ResultUtil.success();
    }

    @ApiOperation(value = "修改教师等级")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teacherLevel", value = "教师等级", required = true,
                    dataType = "TeacherLevel", paramType = "body")
    })
    @PutMapping("/teacherLevel")
    @RequiresPermissions("teacher/level:update")
    public ResponseEntity updateTeacherLevel(@RequestBody TeacherLevel teacherLevel) throws BaseServiceException {
        levelManageService.updateTeacherLevel(teacherLevel);
        return ResultUtil.success();
    }

    @ApiOperation(value = "删除教师等级")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "等级id", required = true,
                    dataType = "int", paramType = "path")
    })
    @DeleteMapping("/teacherLevel/{id}")
    @RequiresPermissions("teacher/level:delete")
    public ResponseEntity deleteTeacherLevel(@PathVariable int id) throws BaseServiceException {
        levelManageService.deleteTeacherLevel(id);
        return ResultUtil.success();
    }
}
