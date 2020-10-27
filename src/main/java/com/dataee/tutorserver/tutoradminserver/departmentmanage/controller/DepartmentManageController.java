package com.dataee.tutorserver.tutoradminserver.departmentmanage.controller;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.entity.Department;
import com.dataee.tutorserver.tutoradminserver.departmentmanage.service.IDepartmentManageService;
import com.dataee.tutorserver.utils.ResultUtil;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/25 20:59
 */
@RestController
@RequestMapping("/admin")
public class DepartmentManageController {
    @Autowired
    private IDepartmentManageService departmentManageService;

    @ApiOperation(value = "管理员创建部门")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "名称", required = true,
                    dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "parentId", value = "父节点", required = true,
                    dataType = "Integer", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Department.class)
    })
    @RequiresPermissions("department:create")
    @PostMapping("/department")
    public ResponseEntity createDepartment(@RequestParam("name") String name, @RequestParam("parentId") Integer parentId) throws BaseServiceException {
        return ResultUtil.success(departmentManageService.createDepartment(name, parentId));
    }

    @ApiOperation(value = "管理员获取部门")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Department.class, responseContainer = "List")
    })
    @RequiresPermissions("department:list")
    @GetMapping("/department/list")
    public ResponseEntity getDepartment() {
        return ResultUtil.success(departmentManageService.getDepartment());
    }

    @ApiOperation(value = "管理员删除部门")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "部门id", required = true,
                    dataType = "Integer", paramType = "path")
    })
    @RequiresPermissions("department:delete")
    @DeleteMapping("/department/{id}")
    public ResponseEntity deleteDepartment(@PathVariable("id") Integer id) throws BaseServiceException {
        departmentManageService.deleteDepartment(id);
        return ResultUtil.success();
    }

    @ApiOperation(value = "管理员修改部门")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "名称", required = true,
                    dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "id编号", required = true,
                    dataType = "Integer", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Department.class)
    })
    @RequiresPermissions("department:update")
    @PutMapping("/department")
    public ResponseEntity changeDepartment(@RequestParam("name") String name, @RequestParam("id") Integer id) {
        return ResultUtil.success(departmentManageService.changeDepartment(name, id));
    }
}
