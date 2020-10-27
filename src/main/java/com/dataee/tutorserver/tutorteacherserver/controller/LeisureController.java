package com.dataee.tutorserver.tutorteacherserver.controller;

import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.errorInfoenum.ControllerExceptionEnum;
import com.dataee.tutorserver.entity.Leisure;
import com.dataee.tutorserver.tutorteacherserver.service.ILeisureService;
import com.dataee.tutorserver.tutorteacherserver.service.impl.LeisureServiceImpl;
import com.dataee.tutorserver.utils.ResultUtil;
import com.dataee.tutorserver.utils.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 获取教师课余时间
 *
 * @author JinYue
 * @CreateDate 2019/5/24 9:06
 */
@Api("获取教师的可以时间")
@RestController
public class LeisureController {
    private final Logger logger = LoggerFactory.getLogger(LeisureController.class);

    @Autowired
    private ILeisureService leisureService;

    public void setLeisureService(LeisureServiceImpl leisureService) {
        this.leisureService = leisureService;
    }

    @ApiOperation(tags = "获取教师的课余时间(day0-6)", value = "返回体中的teacher信息不返回，day从0开始")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", responseContainer = "List", response = Leisure.class)
    })
    @RequiresRoles(value = "teacher")
    @RequestMapping(value = "/teacher/leisure", method = RequestMethod.GET)
    public ResponseEntity getLeisure() throws BaseControllerException {
        Integer teacherId = SecurityUtil.getPersonId();
        if (teacherId != null) {
            List<Leisure> leisureList = leisureService.getLeisureById(teacherId);
            return ResultUtil.success(leisureList);
        } else {
            throw new BaseControllerException(ControllerExceptionEnum.RE_LOGIN);
        }
    }

    @RequiresRoles(value = {"superAdmin", "parentAdmin", "courseAdmin", "scheduleAdmin"}, logical = Logical.OR)
    @RequiresPermissions("auth:ed")
    @RequestMapping(value = "/admin/leisure", method = RequestMethod.GET)
    public ResponseEntity getTeacherLeisureById(@RequestParam("courseId") Integer courseId) throws BaseControllerException {
        Integer teacherId = leisureService.getTeacherOfCourse(courseId);
        if (teacherId != null) {
            List<Leisure> leisureList = leisureService.getLeisureById(teacherId);
            return ResultUtil.success(leisureList);
        } else {
            throw new BaseControllerException(ControllerExceptionEnum.RE_LOGIN);
        }
    }
}
