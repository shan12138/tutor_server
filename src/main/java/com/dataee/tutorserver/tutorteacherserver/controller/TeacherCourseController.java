package com.dataee.tutorserver.tutorteacherserver.controller;

import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.entity.Course;
import com.dataee.tutorserver.entity.Lesson;
import com.dataee.tutorserver.tutorpatriarchserver.bean.Remarks;
import com.dataee.tutorserver.tutorteacherserver.bean.ScheduleBean;
import com.dataee.tutorserver.tutorteacherserver.service.ITeacherCourseService;
import com.dataee.tutorserver.userserver.bean.GetCourseTeacherListResponseBean;
import com.dataee.tutorserver.userserver.service.ICourseService;
import com.dataee.tutorserver.utils.ResultUtil;
import com.dataee.tutorserver.utils.SecurityUtil;
import com.dataee.tutorserver.utils.TimeWeekUtil;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 教员端与课程有关的信息
 *
 * @Author ChenShanShan
 * @CreateDate 2019/5/15 19:17
 */
@Api(value = "教员端与课程有关的信息")
@RequiresRoles("teacher")
@RequiresPermissions("auth:ed")
@RestController
@RequestMapping("/")
public class TeacherCourseController {
    @Autowired
    private ITeacherCourseService teacherCourseService;
    @Autowired
    private ICourseService courseService;

    /**
     * 教师获取课程列表
     *
     * @return
     * @throws BaseControllerException
     */
    @ApiOperation(value = "教师获取课程列表息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit", value = "需要获取的信息的条数", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "需要获取的页（从1开始）", required = true, dataType = "Integer", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = GetCourseTeacherListResponseBean.class, responseContainer = "List")
    })
    @GetMapping("/teacher/courseList")
    public ResponseEntity getCourseList(@RequestParam Integer limit, @RequestParam Integer pageNo) throws BaseControllerException {
        Page page = new Page(limit, pageNo);
        return ResultUtil.success(courseService.getCourseList("teacher.teacher_id", SecurityUtil.getPersonId(), page));
    }

    /**
     * 教师获取课程的签到列表
     */
    @ApiOperation(value = "教师获取课程的签到列表其他信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseId", value = "课程id", required = true,
                    dataType = "Integer", paramType = "query"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Course.class)
    })
    @GetMapping("/attendanceRecord")
    public ResponseEntity getAttendanceRecordOfCourse(@RequestParam("courseId") Integer courseId) throws BaseControllerException {
        return ResultUtil.success(teacherCourseService.getAttendanceRecord(courseId,
                SecurityUtil.getPersonId()));
    }

    @ApiOperation(value = "教师获取课程的签到列表分页信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "课程id", required = true,
                    dataType = "Integer", paramType = "query"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Course.class)
    })
    @GetMapping("/attendanceRecordPageInfo")
    public ResponseEntity getAttendanceRecordPageInfo(@RequestParam("courseId") Integer courseId, @RequestParam Integer pageNo,
                                                      @RequestParam Integer limit) throws BaseControllerException {
        Page page = new Page(limit, pageNo);
        return ResultUtil.success(teacherCourseService.getAttendanceRecordPageInfo(courseId,
                SecurityUtil.getPersonId(), page));
    }

    /**
     * 签到（改变签到时间和是否签到标志）
     *
     * @param lessonId
     * @return
     * @throws BaseServiceException
     * @throws BaseControllerException
     */
    @ApiOperation(value = "签到（改变签到时间和是否签到标志）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseId", value = "课程id", required = true,
                    dataType = "Integer", paramType = "query")
    })
    @PutMapping("/checkInInfo")
    public ResponseEntity checkIn(@RequestParam("lessonId") Integer lessonId) throws BaseServiceException, BaseControllerException, ParseException {
        teacherCourseService.checkIn(lessonId, SecurityUtil.getPersonId());
        return ResultUtil.success();
    }

    /**
     * 签退（改变签退时间，计算上课课时，增加上课次数，改变课时记录）
     *
     * @param courseId
     * @return
     * @throws BaseControllerException
     * @throws BaseServiceException
     */
    @ApiOperation(value = "签退（改变签退时间，计算上课课时，增加上课次数，改变课时记录）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseId", value = "课程id", required = true,
                    dataType = "Integer", paramType = "query")
    })
    @PutMapping("/checkOutInfo")
    public ResponseEntity checkOut(@RequestParam("lessonId") Integer lessonId,@RequestParam("courseId")Integer courseId)
            throws BaseControllerException, BaseServiceException, ParseException {
        teacherCourseService.checkOut(SecurityUtil.getPersonId(), lessonId,courseId);
        return ResultUtil.success();
    }

    @ApiOperation(value = "获取课表页面")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "date", value = "日期", required = true,
                    dataType = "String", paramType = "query"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = ScheduleBean.class, responseContainer = "List")
    })
    @GetMapping("/schedule")
    public ResponseEntity getSchedule(@RequestParam("date") String date) throws BaseControllerException, ParseException {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        Date dateTime = ft.parse(date);
        int week = TimeWeekUtil.getWeekOfYear(dateTime);
        return ResultUtil.success(teacherCourseService.getSchedule(SecurityUtil.getPersonId(), week,
                date.split("-")[0]));
    }

    @ApiOperation(value = "教员查看某一节课的家长评价")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lessonId", value = "lessonID", required = true,
                    dataType = "Integer", paramType = "query"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Remarks.class, responseContainer = "List")
    })
    @GetMapping("/teacher/parentRecord")
    public ResponseEntity getParentRecord(@RequestParam("lessonId") Integer lessonId) {
        return ResultUtil.success(teacherCourseService.getParentRecord(lessonId));
    }
    @ApiOperation(value = "老师查看某一课次的详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lessonId", value = "lessonID", required = true,
                    dataType = "Integer", paramType = "query"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Lesson.class)
    })
    @GetMapping("/teacher/lessonNumber")
    public ResponseEntity getLessonById(@RequestParam("lessonId") Integer lessonId) {
        return ResultUtil.success(teacherCourseService.getLessonById(lessonId));
    }
}
