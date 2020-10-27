package com.dataee.tutorserver.tutoradminserver.filemanage.controller;

import com.dataee.tutorserver.tutoradminserver.filemanage.bean.CoursewareRequestBean;
import com.dataee.tutorserver.tutoradminserver.filemanage.service.ITeachingResourceService;
import com.dataee.tutorserver.tutoradminserver.filemanage.service.ISaveResourceManageService;
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

import javax.validation.Valid;

/**
 * @author JinYue
 * @CreateDate 2019/6/9 22:15
 */
@RequiresPermissions("auth:ed")
@RestController
@RequestMapping("/admin/courseware")
public class CoursewareController {
    private Logger logger = LoggerFactory.getLogger(CoursewareController.class);

    @Autowired
    private ISaveResourceManageService saveResourceManageService;

    @Autowired
    private ITeachingResourceService coursewareService;

    @ApiOperation("管理员上传课件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseware", value = "课件信息", required = true,
                    dataType = "CoursewareRequestBean", paramType = "body"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok")
    })
    @RequiresPermissions("course/courseWare:create")
    @PostMapping("/")
    public ResponseEntity saveCourseWare(@RequestBody @Valid CoursewareRequestBean courseware) {
        saveResourceManageService.saveCourseware(courseware);
        return ResultUtil.success();
    }
}
