package com.dataee.tutorserver.tutoradminserver.rolemanage.controller;

import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.entity.AdminRole;
import com.dataee.tutorserver.entity.Permission;
import com.dataee.tutorserver.tutoradminserver.rolemanage.bean.CreateRoleRequestBean;
import com.dataee.tutorserver.tutoradminserver.rolemanage.bean.UpdateRoleRequestBean;
import com.dataee.tutorserver.tutoradminserver.rolemanage.service.IRoleManageService;
import com.dataee.tutorserver.utils.ResultUtil;
import com.dataee.tutorserver.utils.SecurityUtil;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/4 11:21
 */
@Api(value = "管理员端角色权限管理模块")
@RestController
@RequestMapping("/")
public class RoleManageController {
    @Autowired
    private IRoleManageService roleManageService;

    @ApiOperation(value = "管理员获取角色列表")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = AdminRole.class, responseContainer = "List")
    })
    @GetMapping("/roleList")
    @RequiresPermissions("role:list")
    public ResponseEntity getRoleList(@RequestParam Integer pageNo, @RequestParam Integer limit,
                                      @RequestParam(required = false) String keyword) {
        Page page = new Page(limit, pageNo);
        keyword = (keyword == null ? "" : keyword);
        return ResultUtil.success(roleManageService.getRoleList(page, keyword));
    }

    @ApiOperation(value = "获取某一个角色的权限列表")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Permission.class, responseContainer = "List")
    })
    @GetMapping("/role/permission/{roleId}")
    @RequiresPermissions("role/permission:read")
    public ResponseEntity getPermissionsOfRole(@PathVariable("roleId") Integer roleId) {
        return ResultUtil.success(roleManageService.getPermissionsOfRole(roleId));
    }

    @ApiOperation(value = "新建角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "role", value = "新建角色信息体", required = true,
                    dataType = "CreateRoleRequestBean", paramType = "body")
    })
    @PostMapping("/role")
    @RequiresPermissions("role:create")
    public ResponseEntity createRole(@RequestBody CreateRoleRequestBean role) throws BaseServiceException, BaseControllerException {
        int createdId = SecurityUtil.getId();
        roleManageService.createRole(role, createdId);
        return ResultUtil.success();
    }

    @ApiOperation(value = "修改角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "修改角色id", required = true,
                    dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "role", value = "修改角色信息体", required = true,
                    dataType = "UpdateRoleRequestBean", paramType = "body")
    })
    @PatchMapping("/role/{roleId}")
    @RequiresPermissions("role:update")
    public ResponseEntity updateRole(@PathVariable("roleId") int roleId,
                                     @RequestBody UpdateRoleRequestBean role) throws BaseServiceException {
        roleManageService.updateRole(roleId, role);
        return ResultUtil.success();
    }

    @ApiOperation(value = "删除角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色Id", required = true,
                    dataType = "int", paramType = "path")
    })
    @DeleteMapping("/role/{roleId}")
    @RequiresPermissions("role:delete")
    public ResponseEntity deleteRole(@PathVariable("roleId") int roleId) throws BaseServiceException {
        roleManageService.deleteRole(roleId);
        return ResultUtil.success();
    }

    @ApiOperation(value = "获取角色赋予权限界面")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Permission.class, responseContainer = "List")
    })
    @GetMapping("/role/permission")
    public ResponseEntity getAllPermissions() {
        return ResultUtil.success(roleManageService.getAllPermissions());
    }

}
