package com.dataee.tutorserver.tutorpatriarchserver.controller;

import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.entity.Teacher;
import com.dataee.tutorserver.tutorpatriarchserver.service.IParentTeacherService;
import com.dataee.tutorserver.userserver.bean.GetCourseTeacherListResponseBean;
import com.dataee.tutorserver.userserver.service.ICourseService;
import com.dataee.tutorserver.utils.DoubleToTwo;
import com.dataee.tutorserver.utils.ResultUtil;
import com.dataee.tutorserver.utils.SecurityUtil;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * 家长端教员模块
 *
 * @Author ChenShanShan
 * @CreateDate 2019/5/6 22:11
 */
@Api(value = "家长端我的教员模块")
@RequiresRoles("parent")
@RequiresPermissions("auth:ed")
@RestController
@RequestMapping("/")
public class ParentTeacherController {
    @Autowired
    private IParentTeacherService parentTeacherService;
    @Autowired
    private ICourseService courseService;

    /**
     * 获取教员列表
     *
     * @return
     * @throws BaseControllerException
     */
    @ApiOperation(value = "家长端获取教员列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit", value = "需要获取的信息的条数", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "需要获取的页（从1开始）", required = true, dataType = "Integer", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = GetCourseTeacherListResponseBean.class, responseContainer = "List")
    })
    @GetMapping("/parent/teacherList")
    public ResponseEntity getOwnTeacherList(@RequestParam Integer limit, @RequestParam Integer pageNo)
            throws BaseControllerException {
        Page page = new Page(limit, pageNo);
        return ResultUtil.success(courseService.getCourseList("course.parent_id", SecurityUtil.getPersonId(), page));
    }

    /**
     * 家长端获取教员详情信息
     *
     * @param teacherId
     * @return
     * @throws BaseControllerException
     */
    @ApiOperation(value = "家长端获取教员详情信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teacherId", value = "教员id", required = true,
                    dataType = "String", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Teacher.class)
    })
    @GetMapping("/teacherDetailInfo")
    public ResponseEntity getTeacherDetailInfo(@RequestParam @Valid @NotBlank(message = "教师ID不能为空") String teacherId)
            throws BaseControllerException {
        Teacher teacher = parentTeacherService.getTeacherDetailInfo(teacherId);
        teacher.setTeacherLabel(DoubleToTwo.convertDoubleToTwo(teacher.getTeacherLabel()));
        return ResultUtil.success(teacher);
    }

}
