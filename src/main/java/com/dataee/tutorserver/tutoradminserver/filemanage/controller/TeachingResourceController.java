package com.dataee.tutorserver.tutoradminserver.filemanage.controller;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.commons.exceptions.IllegalParameterException;
import com.dataee.tutorserver.tutoradminserver.filemanage.bean.ResourceListRequestBean;
import com.dataee.tutorserver.tutoradminserver.filemanage.bean.TeachingResourceRequestBean;
import com.dataee.tutorserver.tutoradminserver.filemanage.service.ISaveResourceManageService;
import com.dataee.tutorserver.tutoradminserver.filemanage.service.ITeachingResourceService;
import com.dataee.tutorserver.utils.ResultUtil;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.io.IOException;

/**
 * @author JinYue
 * @CreateDate 2019/6/29 14:05
 */
@RestController
@RequestMapping("/admin/teachingResource")
public class TeachingResourceController {
    private Logger logger = LoggerFactory.getLogger(TeachingResourceController.class);
    @Autowired
    private ISaveResourceManageService saveResourceManageService;
    @Autowired
    private ITeachingResourceService teachingResourceService;

    @ApiOperation("管理员上传教学资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teachingResource", value = "教学资源", required = true,
                    dataType = "TeachingResourceRequestBean", paramType = "body")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok")
    })
    @RequiresPermissions("educationResource:create")
    @PostMapping("/")
    public ResponseEntity saveTeachingResource(@RequestBody @Valid TeachingResourceRequestBean teachingResource) {
        saveResourceManageService.saveTeachingResource(teachingResource);
        return ResultUtil.success();
    }


    @ApiOperation("管理员获取教学资源列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "请求的页号", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "请求的条数", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "studentName", value = "学生名", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "teacher", value = "老师名", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "courseName", value = "课程名", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "类型", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "headTeacher", value = "班主任", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "courseAdmin", value = "排课主任", required = false, dataType = "String", paramType = "query")

    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", responseContainer = "List", response = ResourceListRequestBean.class)
    })
    @RequiresPermissions("educationResource:list")
    @GetMapping("/list")
    public ResponseEntity getTeachingResourceList(@RequestParam Integer pageNo, @RequestParam Integer limit,
                                                  @RequestParam(required = false)String studentName,@RequestParam(required = false)String teacher,
                                                  @RequestParam(required = false)String courseName,@RequestParam(required = false)String type,@RequestParam(required = false)String headTeacher,
                                                  @RequestParam(required = false)String courseAdmin ) {
        Page page = new Page(limit, pageNo);
        NewPageInfo teachingResourceList = teachingResourceService.getTeachingResourceList(page,studentName,teacher,courseName,type,headTeacher,courseAdmin);
        return ResultUtil.success(teachingResourceList);
    }

    @ApiOperation("删除教学资源文件")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok")
    })
    @RequiresPermissions("educationResource:delete")
    @GetMapping("/delete/{id:\\d+}")
    public ResponseEntity deleteTeachingResource(@PathVariable Integer id) throws BaseServiceException {
        teachingResourceService.deleteTeachingResourceById(id);
        return ResultUtil.success();
    }


    @ApiOperation("获取指定的教学资源文件")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = String.class)
    })
    @GetMapping("/{id:\\d+}")
    public RedirectView getTeachingResourceAddress(@PathVariable Integer id) throws BaseServiceException, IOException, IllegalParameterException {
        String url = teachingResourceService.getTeachingResourceAddressById(id);
        if (url != null) {
            return new RedirectView(url);
        } else {
            throw new IllegalParameterException();
        }
    }
}
