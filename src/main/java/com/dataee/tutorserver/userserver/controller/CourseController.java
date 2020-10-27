package com.dataee.tutorserver.userserver.controller;

import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.entity.Grade;
import com.dataee.tutorserver.entity.Subject;
import com.dataee.tutorserver.userserver.bean.GetTodayCourseResponseBean;
import com.dataee.tutorserver.userserver.bean.GradeAndSubjectResponseBean;
import com.dataee.tutorserver.userserver.service.ICourseService;
import com.dataee.tutorserver.userserver.service.impl.CourseServiceImpl;
import com.dataee.tutorserver.utils.ResultUtil;
import com.dataee.tutorserver.utils.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 三方课程相关模块功能
 *
 * @Author ChenShanShan
 * @CreateDate 2019/4/27 11:24
 */
@Api(value = "三方课程相关模块功能")
@RestController
@RequestMapping("/")
public class CourseController {
    @Autowired
    private ICourseService courseService;

    public void setCourseService(CourseServiceImpl courseService) {
        this.courseService = courseService;
    }

    /**
     * 获得年级和科目列表
     *
     * @return
     */
    @ApiOperation(value = "获得年级和科目列表")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = GradeAndSubjectResponseBean.class)
    })
    @GetMapping("/grade/subject")
    public ResponseEntity getGradeAndSubject() {
        List<Grade> gradeList = courseService.getGrade();
        List<Subject> subjectList = courseService.getSubject();
        GradeAndSubjectResponseBean gradeAndSubjectResponseBean = new GradeAndSubjectResponseBean();
        gradeAndSubjectResponseBean.setGradeList(gradeList);
        gradeAndSubjectResponseBean.setSubjectList(subjectList);
        return ResultUtil.success(gradeAndSubjectResponseBean);
    }

    @RequiresRoles("teacher")
    @ApiOperation(value = "获得教师今日课程")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = GetTodayCourseResponseBean.class, responseContainer = "List")
    })
    @GetMapping("/teacher/todayLesson")
    public ResponseEntity getTeacherTodayLesson() throws BaseControllerException {
        return ResultUtil.success(courseService.getTeacherTodayCourse(SecurityUtil.getPersonId().toString()));
    }

    @RequiresRoles("parent")
    @ApiOperation(value = "获得家长今日课程")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = GetTodayCourseResponseBean.class, responseContainer = "List")
    })
    @GetMapping("/parent/todayLesson")
    public ResponseEntity getParentTodayLesson() throws BaseControllerException {
        return ResultUtil.success(courseService.getParentTodayCourse(SecurityUtil.getPersonId().toString()));
    }
































}
