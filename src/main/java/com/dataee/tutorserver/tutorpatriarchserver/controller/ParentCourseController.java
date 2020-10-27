package com.dataee.tutorserver.tutorpatriarchserver.controller;

import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.entity.Bill;
import com.dataee.tutorserver.entity.Course;
import com.dataee.tutorserver.entity.CourseHourRecord;
import com.dataee.tutorserver.entity.Lesson;
import com.dataee.tutorserver.tutoradminserver.remarksmanage.service.IRemarksManageService;
import com.dataee.tutorserver.tutorpatriarchserver.bean.ClassHourDetail;
import com.dataee.tutorserver.tutorpatriarchserver.bean.MoneyBag;
import com.dataee.tutorserver.tutorpatriarchserver.bean.Remarks;
import com.dataee.tutorserver.tutorpatriarchserver.service.IParentCourseService;
import com.dataee.tutorserver.tutorteacherserver.bean.ScheduleBean;
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

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 家长端首页的我的课程模块
 *
 * @Author ChenShanShan
 * @CreateDate 2019/5/6 15:41
 */
@Api(value = "家长端我的课程模块")
@RequiresRoles("parent")
@RequiresPermissions("auth:ed")
@RestController
@RequestMapping("/")
public class ParentCourseController {
    @Autowired
    private IParentCourseService parentCourseService;
    @Autowired
    private ICourseService courseService;
    @Autowired
    private IRemarksManageService remarksManageService;

    /**
     * 获得家长的课程列表
     *
     * @return
     * @throws BaseControllerException
     */
    @ApiOperation(value = "获得家长的课程列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit", value = "需要获取的信息的条数", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "需要获取的页（从1开始）", required = true, dataType = "Integer", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = GetCourseTeacherListResponseBean.class, responseContainer = "List")
    })
    @GetMapping("/parent/courseList")
    public ResponseEntity getOwnCourseList(@RequestParam Integer limit, @RequestParam Integer pageNo)
            throws BaseControllerException {
        Page page = new Page(limit, pageNo);
        return ResultUtil.success(courseService.getCourseList("course.parent_id", SecurityUtil.getPersonId(), page));
    }

    /**
     * 获得课程对应的详细信息，包括课时记录和签到记录
     *
     * @return
     * @throws BaseControllerException
     */
    @ApiOperation(value = "家长获得课程对应的详细信息(不包括签到列表)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseId", value = "课程id", required = true,
                    dataType = "String", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Course.class)
    })
    @GetMapping("/courseDetail")
    public ResponseEntity getCourseDetailInfo(@RequestParam @Valid @NotBlank(message = "课程ID不能为空") String courseId) {
        Course info = parentCourseService.getCourseDetailInfo(courseId);
        double total=0.0;
        double consume =0.0;
        ClassHourDetail classHourDetail =new ClassHourDetail();
        for (CourseHourRecord courseHourRecord:info.getCourseHourRecord()){
            total+=courseHourRecord.getTotalClassHour();
            consume+=courseHourRecord.getConsumeClassHour();
        }
        classHourDetail.setTotalClassHour(total);
        classHourDetail.setConsumeClassHour(consume);
        classHourDetail.setRestClassHour(total-consume);
        info.setClassHourDetail(classHourDetail);
        return ResultUtil.success(info);
    }

    @ApiOperation(value = "家长获得课程对应签到列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseId", value = "课程id", required = true,
                    dataType = "String", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Course.class)
    })
    @GetMapping("/courseDetail/lessons")
    public ResponseEntity getLessonDetailInfo(@RequestParam @Valid @NotBlank(message = "课程ID不能为空") String courseId,
                                              @RequestParam Integer pageNo, @RequestParam Integer limit) {
        Page page = new Page(limit, pageNo);
        return ResultUtil.success(parentCourseService.getLessonDetailInfo(courseId, page));
    }

    @ApiOperation(value = "家长获取课表页面")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "date", value = "日期", required = true,
                    dataType = "String", paramType = "query"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = ScheduleBean.class, responseContainer = "List")
    })
    @GetMapping("/parent/schedule")
    public ResponseEntity getSchedule(@RequestParam("date") String date) throws BaseControllerException, ParseException {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        Date dateTime = ft.parse(date);
        int week = TimeWeekUtil.getWeekOfYear(dateTime);
        return ResultUtil.success(parentCourseService.getSchedule(SecurityUtil.getPersonId(), week,
                date.split("-")[0]));
    }

    @ApiOperation(value = "家长查看某一节课的教员评价")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lessonId", value = "lessonID", required = true,
                    dataType = "Integer", paramType = "query"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Remarks.class, responseContainer = "List")
    })
    @GetMapping("/parent/teacherRecord")
    public ResponseEntity getTeacherRecord(@RequestParam("lessonId") Integer lessonId) {
        return ResultUtil.success(remarksManageService.getTeacherRecord(lessonId));
    }

    @PutMapping("/course/address")
    public ResponseEntity changeCourseAddress(@RequestParam Integer addressId, @RequestParam Integer courseId) {
        parentCourseService.changeCourseAddress(addressId, courseId);
        return ResultUtil.success();
    }


    @ApiOperation(value = "家长查看某一课次的详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lessonId", value = "lessonID", required = true,
                    dataType = "Integer", paramType = "query"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Lesson.class)
    })
    @GetMapping("/parent/lessonNumber")
    public ResponseEntity getLessonById(@RequestParam("lessonId") Integer lessonId) {
        return ResultUtil.success(parentCourseService.getLessonById(lessonId));
    }

    @ApiOperation(value = "家长查看钱包的信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = MoneyBag.class, responseContainer = "List")
    })
    @GetMapping("/parent/moneybag_info")
    public ResponseEntity getMoneybagInfo()  throws BaseControllerException{
        List<GetCourseTeacherListResponseBean> courseList = parentCourseService.getCourseList("course.parent_id", SecurityUtil.getPersonId());
        List<MoneyBag> moneyBags =new ArrayList<>();
        for(GetCourseTeacherListResponseBean c:courseList){
            MoneyBag moneyBag =new MoneyBag();
            List<Lesson> lessons = parentCourseService.getLessonsByCourseId(c.getCourseId());
            moneyBag.setCourseId(c.getCourseId());
            if(lessons.size()>0){
                moneyBag.setCurriculaTime(lessons.get(0).getRemarkCheckInTime());
            }
            moneyBag.setPrice(c.getCourseHourRecords().get(0).getPrice());
            double comsumeCourseHour =0.0;
            double balance =0.0;
            if( c.getCourseHourRecords()!=null&&c.getCourseHourRecords().size()>0){
                for(CourseHourRecord  hourRecord: c.getCourseHourRecords()){
                    comsumeCourseHour+=hourRecord.getConsumeClassHour();
                    if(hourRecord.getIsFree()!=null&&hourRecord.getIsFree()==0){
                        balance += (hourRecord.getTotalClassHour()-hourRecord.getConsumeClassHour())*hourRecord.getPrice()*(hourRecord.getDiscount()/100);
                    }
                }
            }
            moneyBag.setConsumeCourseHour(comsumeCourseHour);
            moneyBag.setBalance(balance);
            moneyBag.setCourseName(c.getCourseName());
            moneyBags.add(moneyBag);
        }
        return ResultUtil.success(moneyBags);
    }

    @ApiOperation(value = "家长查看钱包的账单信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseId", value = "courseId", required = true,
                    dataType = "Integer", paramType = "query"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Bill.class, responseContainer = "List")
    })
    @GetMapping("/parent/bill")
    public ResponseEntity getMoneybagInfo(@RequestParam("courseId")Integer courseId,
                                          @RequestParam Integer pageNo, @RequestParam Integer limit)  throws BaseControllerException{
        Page page = new Page(limit, pageNo);
        return ResultUtil.success(parentCourseService.getParentConsume(courseId,page));
    }
}
