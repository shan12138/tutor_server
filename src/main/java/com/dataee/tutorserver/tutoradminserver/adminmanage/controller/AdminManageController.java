package com.dataee.tutorserver.tutoradminserver.adminmanage.controller;

import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.commons.exceptions.SQLOperationException;
import com.dataee.tutorserver.entity.Role;
import com.dataee.tutorserver.tutoradminserver.adminmanage.bean.AdminManage;
import com.dataee.tutorserver.tutoradminserver.adminmanage.bean.AdminPasswordRequestBean;
import com.dataee.tutorserver.tutoradminserver.adminmanage.service.IAdminManageService;
import com.dataee.tutorserver.utils.ResultUtil;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/5 21:25
 */
@Api(value = "管理员端的管理管理员模块")
@RestController
@RequestMapping("/")
public class AdminManageController {
    @Autowired
    private IAdminManageService adminManageService;

    /**
     * 获取管理员列表
     *
     * @return
     */
    @ApiOperation(value = "获取管理员列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit", value = "需要获取的信息的条数", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "需要获取的页（从1开始）", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "keyword", value = "关键字", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "roleId", value = "角色id", required = true, dataType = "Integer", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = AdminManage.class, responseContainer = "List")
    })
    @RequiresPermissions("staff:list")
    @GetMapping("/adminList")
    public ResponseEntity getAdminList(@RequestParam Integer pageNo, @RequestParam Integer limit,
                                       @RequestParam(required = false) String keyword,
                                       @RequestParam(required = false) Integer roleId) {
        Page page = new Page(limit, pageNo);
        return ResultUtil.success(adminManageService.getAdminList(page, keyword, roleId));
    }

    /**
     * 转让总管理，修改role_id
     *
     * @return
     */
    @ApiOperation(value = "转让总管理")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "adminAuthId", value = "管理员的id编号", required = true,
                    dataType = "Integer", paramType = "path")
    })
    @PutMapping("/admin/power/{adminAuthId}")
    public ResponseEntity transferAdminPower(@PathVariable("adminAuthId") Integer adminAuthId) throws BaseServiceException, BaseControllerException {
        adminManageService.transferAdminPower(adminAuthId);
        return ResultUtil.success();
    }

    /**
     * 修改管理员信息（包括基本信息和角色信息）
     *
     * @param adminManage
     * @return
     * @throws BaseServiceException
     */
    @ApiOperation(value = "修改管理员信息（包括基本信息和角色信息）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "adminManage", value = "修改的管理员信息体", required = true,
                    dataType = "AdminManage", paramType = "body")
    })
    @RequiresPermissions("staff:update")
    @PutMapping("/admin")
    public ResponseEntity changeAdmin(@RequestBody @Valid AdminManage adminManage) throws BaseServiceException {
        adminManageService.changeAdmin(adminManage);
        return ResultUtil.success();
    }

    /**
     * 删除管理员
     *
     * @param id
     * @return
     * @throws BaseServiceException
     */
    @ApiOperation(value = "删除管理员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "管理员的ID编号", required = true,
                    dataType = "Integer", paramType = "path")
    })
    @RequiresPermissions("staff:delete")
    @DeleteMapping("/admin/{id}")
    public ResponseEntity deleteAdmin(@PathVariable("id") @NotNull(message = "管理员ID不能为空") Integer id) throws BaseServiceException {
        adminManageService.deleteAdmin(id);
        return ResultUtil.success();
    }

    /**
     * 金悦的部分
     * 重置管理员密码
     *
     * @return
     */
    @ApiOperation(value = "修改制定管理员密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "adminPassword", value = "修改密码的相关信息", required = true,
                    dataType = "AdminPasswordRequestBean", paramType = "body")
    })
    @RequiresPermissions("staff/password:update")
    @PutMapping("/admin/password")
    public ResponseEntity resetPassword(@RequestBody AdminPasswordRequestBean adminPassword) throws SQLOperationException {
        adminManageService.changeAdminPasswordById(adminPassword);
        return ResultUtil.success();
    }

    @ApiOperation(value = "获取管理员角色列表")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Role.class, responseContainer = "List")
    })
    @GetMapping("/admin/roles")
    public ResponseEntity getAdminRole() {
        return ResultUtil.success(adminManageService.getAdminRole());
    }
}
