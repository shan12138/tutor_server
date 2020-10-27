package com.dataee.tutorserver.tutoradminserver.coursemanage.controller;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.commons.exceptions.IllegalParameterException;
import com.dataee.tutorserver.commons.exceptions.SQLOperationException;
import com.dataee.tutorserver.entity.CourseHourRecord;
import com.dataee.tutorserver.tutoradminserver.coursemanage.bean.CourseLesson;
import com.dataee.tutorserver.tutoradminserver.coursemanage.bean.SaveLessonRequestBean;
import com.dataee.tutorserver.tutoradminserver.coursemanage.bean.SaveRemarksResponseBean;
import com.dataee.tutorserver.tutoradminserver.coursemanage.service.IQueryScheduleMngService;
import com.dataee.tutorserver.tutoradminserver.coursemanage.service.ISaveScheduleMngService;
import com.dataee.tutorserver.tutoradminserver.coursemanage.service.impl.SaveScheduleMngServiceImpl;
import com.dataee.tutorserver.tutorteacherserver.service.ILeisureService;
import com.dataee.tutorserver.utils.ResultUtil;
import com.dataee.tutorserver.utils.TimeWeekUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 管理员排课
 *
 * @author JinYue
 * @CreateDate 2019/5/24 17:42
 */
@Api("管理员排课")
@RequiresRoles(value = {"superAdmin", "parentAdmin", "courseAdmin", "scheduleAdmin"}, logical = Logical.OR)
@RequiresPermissions("auth:ed")
@RestController
    @RequestMapping("/schedule")
public class ScheduleMngController {
    private final Logger logger = LoggerFactory.getLogger(ScheduleMngController.class);

    @Autowired
    private ISaveScheduleMngService saveScheduleMngService;
    @Autowired
    private IQueryScheduleMngService queryScheduleMngService;
    @Autowired
    private ILeisureService leisureService;

    public void setSaveScheduleMngService(SaveScheduleMngServiceImpl saveScheduleMngService) {
        this.saveScheduleMngService = saveScheduleMngService;
    }

    /**
     * 保存排课信息
     * 需要重构
     *
     * @param lesson
     * @return
     */
    @ApiOperation(value = "保存排课信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lessons", value = "课程信息", required = true,
                    dataType = "SaveLessonsRequestBean", paramType = "body")
    })
    @RequiresPermissions("lesson:create")
    @PostMapping("/")
    public ResponseEntity SaveNewLessons(@RequestBody @Valid SaveLessonRequestBean lesson) throws BaseServiceException, IllegalParameterException {
        //查重
        //保存新的课程
        Integer teacherId = leisureService.getTeacherOfCourse(lesson.getCourseId());
        if (teacherId == null) {
            throw new IllegalParameterException();
        }
        lesson.setTeacherId(teacherId);
        saveScheduleMngService.addLessonByWeek(lesson);
        return ResultUtil.success();
    }

    /**
     * 获取教师本周的课表
     *
     * @return
     */
    @ApiOperation(value = "获取该教师该周的课程排课信息(包括该课程以及其他课程)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "date", value = "第几周", required = true,
                    dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "courseId", value = "课程编号", required = true,
                    dataType = "Integer", paramType = "query")
    })
    @GetMapping("/lessons")
    public ResponseEntity GetTeacherLessons(@RequestParam(value = "date", required = false) String date,
                                            @RequestParam("courseId") Integer courseId) throws IllegalParameterException, ParseException {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        Date dateTime = ft.parse(date);
        Integer teacherId = leisureService.getTeacherOfCourse(courseId);
        if (teacherId == null) {
            throw new IllegalParameterException();
        }
        int week = TimeWeekUtil.getWeekOfYear(dateTime);
        return ResultUtil.success(queryScheduleMngService.getLessons(teacherId, week, courseId));
    }
    /**
     * 保存指定周的备注
     * 需要重构的service的部分
     *
     * @param remark
     * @return
     * @throws IllegalParameterException
     */
    @PutMapping("/remark")
    public ResponseEntity saveRemarks(@RequestBody SaveRemarksResponseBean remark) throws IllegalParameterException, SQLOperationException {
        Integer teacherId = leisureService.getTeacherOfCourse(remark.getCourseId());
        if (teacherId == null) {
            throw new IllegalParameterException();
        }
        remark.setTeacherId(teacherId);
        saveScheduleMngService.saveRemark(remark);
        return ResultUtil.success();

    }
    @ApiOperation(value = "获取该课程的排课列表)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseId", value = "课程id", required = true,
                    dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "teacherName", value = "老师名称", required = false,
                    dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "状态", required = false,
                    dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = false,
                    dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = false,
                    dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "当前请求页号", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "请求数量", required = true, dataType = "Integer", paramType = "query"),
    })
    @RequiresPermissions("lesson:list")
    @GetMapping("/course/lessons")
    public ResponseEntity courseLessons(@RequestParam("courseId") Integer courseId,@RequestParam(required = false,value = "teacherName") String teacherName,@RequestParam(required = false,value = "status") String status,@RequestParam(required = false,value = "startTime") String startTime,@RequestParam(required = false,value = "endTime")String endTime,@RequestParam Integer pageNo, @RequestParam Integer limit) throws IllegalParameterException, SQLOperationException {
        Page page = new Page(limit, pageNo);
        NewPageInfo<CourseLesson> courseLessonList = queryScheduleMngService.getLessonsByCourseId(courseId,teacherName,status,startTime,endTime,page);
        return ResultUtil.success(courseLessonList);
    }
}
