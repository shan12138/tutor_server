package com.dataee.tutorserver.tutoradminserver.coursemanage.controller;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.commons.exceptions.IllegalParameterException;
import com.dataee.tutorserver.commons.exceptions.SQLOperationException;
import com.dataee.tutorserver.entity.*;
import com.dataee.tutorserver.tutoradminserver.coursemanage.bean.*;
import com.dataee.tutorserver.tutoradminserver.coursemanage.service.ICourseMngService;
import com.dataee.tutorserver.tutoradminserver.coursemanage.service.ILessonsService;
import com.dataee.tutorserver.tutoradminserver.patriarchmanage.service.IParentManageService;
import com.dataee.tutorserver.tutoradminserver.teachermanage.bean.TeacherListResponseBean;
import com.dataee.tutorserver.tutorteacherserver.service.ITeacherInviteService;
import com.dataee.tutorserver.userserver.service.ICourseService;
import com.dataee.tutorserver.utils.ResultUtil;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.*;
import org.apache.commons.collections.functors.FalsePredicate;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author 杨少聪
 * @Date 2019/5/17
 * @Description: com.dataee.tutorserver.tutoradminserver.coursemanage.controller
 */
@Api(value = "管理员端的课程管理模块")
@RestController
@RequestMapping("/")
public class  CourseMngController {
    @Autowired
    private ICourseMngService courseMngService;
    @Autowired
    private ICourseService courseService;
    @Autowired
    private ILessonsService lessonsService;
    @ApiOperation(value = "管理员获取课程列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "页数", required = true,
                    dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "每页个数", required = true,
                    dataType = "String", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = CourseListResponseBean.class, responseContainer = "List")
    })
    @RequiresPermissions("course:list")
    @GetMapping("/admin/getCourseList")
    public ResponseEntity getCourseList(@RequestParam("pageNo") Integer pageNo, @RequestParam("limit") Integer limit) {
        Page page = new Page(limit, pageNo);
        return ResultUtil.success(courseMngService.getCourseList(page));
    }

    @ApiOperation(value = "管理员筛选课程列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryCondition", value = "查询条件", required = true,
                    dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "grade", value = "年级", required = true,
                    dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "subject", value = "科目", required = true,
                    dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "teacher", value = "老师", required = true,
                    dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "headTeacher", value = "班主任", required = true,
                    dataType = "String", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = CourseListResponseBean.class, responseContainer = "List")
    })
    @RequiresPermissions("course:list")
    @GetMapping("/course/queryCondition")
    public ResponseEntity queryCourse(@RequestParam(required = false,value = "studentName") String studentName, @RequestParam(required = false,value = "grade") String grade,@RequestParam(required = false,value = "subject")String subject,
                                      @RequestParam(required = false,value = "teacher") String teacher, @RequestParam(required = false,value = "headTeacher") String headTeacher,@RequestParam(value = "productId")Integer  productId,
                                      @RequestParam("pageNo") Integer pageNo, @RequestParam("limit") Integer limit) {
        Page page = new Page(limit, pageNo);
        return ResultUtil.success(courseMngService.queryCourse(studentName, grade,subject, teacher, headTeacher,productId, page));
    }

    @ApiOperation(value = "管理员获取新增课程列表页面")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = GetAddCourseResponseBean.class)
    })
    @GetMapping("/addCourseInfoPage")
    public ResponseEntity getCreateCourse() {
        List<Grade> gradeList = courseService.getGrade();
        List<Subject> subjectList = courseService.getSubject();
        List<Parent> parentList = courseService.getParentList(3);
        //排课主任
        List<Administrator> scheduleAdminList = courseMngService.getAdminList("scheduleAdmin");
        //班主任
        List<Administrator> parentAdminList = courseMngService.getAdminList("parentAdmin");
        //学管师
      //  List<Administrator> courseAdminList = courseMngService.getAdminList("courseAdmin");
        GetAddCourseResponseBean courseResponseBean = new GetAddCourseResponseBean();
        courseResponseBean.setParentList(parentList);
        courseResponseBean.setGradeList(gradeList);
        courseResponseBean.setSubjectList(subjectList);
        courseResponseBean.setScheduleAdminList(scheduleAdminList);
       // courseResponseBean.setCourseAdminList(courseAdminList);
        courseResponseBean.setParentAdminList(parentAdminList);
        return ResultUtil.success(courseResponseBean);
    }

    @ApiOperation(value = "管理员获取家长的子女和地址列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentId", value = "家长ID编号", required = true,
                    dataType = "Integer", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = GetParentChildAddressResponseBean.class)
    })
    @GetMapping("/admin/childList/{parentId}")
    public ResponseEntity getParentChildList(@PathVariable("parentId") Integer parentId) {
        List<Student> studentList = courseMngService.getParentChildList(parentId);
        List<Address> addressList = courseMngService.getParentAddressList(parentId);
        GetParentChildAddressResponseBean childAddress = new GetParentChildAddressResponseBean(studentList, addressList);
        return ResultUtil.success(childAddress);
    }

    @ApiOperation(value = "管理员新增课程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "course", value = "新增课程信息体", required = true,
                    dataType = "CreateCourseRequestBean", paramType = "body")
    })
    @RequiresPermissions("course:create")
    @PostMapping("/course")
    public ResponseEntity createCourse(@RequestBody @Valid CreateCourseRequestBean course) throws BaseServiceException {
         courseMngService.createCourse(course);
        return ResultUtil.success();
    }

    @ApiOperation(value = "管理员删除课程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "课程id", required = true,
                    dataType = "Integer", paramType = "path")
    })
    @RequiresPermissions("course:delete")
    @DeleteMapping("/course/{id}")
    public ResponseEntity deleteCourse(@PathVariable("id") Integer id) throws BaseServiceException {
        courseMngService.deleteCourse(id);
        return ResultUtil.success();
    }

    @ApiOperation(value = "管理员获取课程详情信息(不包括签到列表)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "课程id", required = true,
                    dataType = "String", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = CourseDetailResponseBean.class)
    })
    @RequiresPermissions("course:read")
    @GetMapping("/admin/courseDetail")
    public ResponseEntity getCourseDetail(@RequestParam("courseId") String courseId) {
        Course course = courseMngService.getCourseDetailInfo(courseId);
        CourseDetailResponseBean courseDetail = new CourseDetailResponseBean();
        if(course.getAddress()!=null){
            courseDetail.setAddress(course.getAddress().getRegion() + course.getAddress().getAddressDetail());
        }
        courseDetail.setParentName(course.getParent().getParentName());
        courseDetail.setStudentName(course.getStudent().getStudentName());
        if (course.getTeacher() != null) {
            courseDetail.setTeacherName(course.getTeacher().getTeacherName());
        }
        courseDetail.setCourseHourRecord(course.getCourseHourRecord());
        courseDetail.setCourseName(course.getCourseName());
        courseDetail.setProductName(course.getProduct().getProductName());
        courseDetail.setCourseAdmin(course.getCourseAdmin());
        courseDetail.setHeadAdmin(course.getHeadAdmin());
        return ResultUtil.success(courseDetail);
    }

    @ApiOperation(value = "管理员获取课程详情信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "课程id", required = true,
                    dataType = "String", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = CourseDetailResponseBean.class)
    })
    @RequiresPermissions("course:read")
    @GetMapping("/admin/lessonDetail")
    public ResponseEntity getLessonDetail(@RequestParam("courseId") String courseId, @RequestParam Integer pageNo,
                                          @RequestParam Integer limit) {
        Page page = new Page(limit, pageNo);
        NewPageInfo lessons = courseMngService.getLessonDetailInfo(courseId, page);
        List<Lesson> lessonsList = lessons.getList();
        List<LessonResponseBean> lessonResponseBeanList = new ArrayList<>();
        if (lessons.getList().size() != 0) {
            for (Lesson lesson : lessonsList) {
                LessonResponseBean lessonResponseBean = new LessonResponseBean(lesson.getTeacherId(),
                        lesson.getLessonNumber(), lesson.getCourseTime(), lesson.getParentRecordConfirmed(),
                        lesson.getTeacherRecordConfirmed(), lesson.getUploadState(), lesson.getTime());
                lessonResponseBean.setId(lesson.getId());
                if (lesson.getCheckInTime() != null && !lesson.getCheckInTime().equals("")) {
                    lessonResponseBean.setDate(lesson.getCheckInTime().split(" ")[0]);
                    lessonResponseBean.setCheckInTime(lesson.getCheckInTime().split(" ")[1]);
                }
                if (lesson.getCheckOutTime() != null && !lesson.getCheckOutTime().equals("")) {
                    lessonResponseBean.setCheckOutTime(lesson.getCheckOutTime().split(" ")[1]);
                }
                if (lesson.getClassTime() != null) {
                    lessonResponseBean.setClassTime(lesson.getClassTime());
                }
                if (lesson.getTeacherName() != null) {
                    lessonResponseBean.setTeacherName(lesson.getTeacherName());
                }
                lessonResponseBeanList.add(lessonResponseBean);
            }
        }
        lessons.setList(lessonResponseBeanList);
        return ResultUtil.success(lessons);
    }

    /**
     * 修改签到记录
     * 传回原始签到时间以及修改后签到时间，方便修改课时总消耗记录
     */
    @ApiOperation(value = "管理员修改签到记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "attendanceRecord", value = "修改的签到记录实体", required = true,
                    dataType = "AttendanceRecordRequestBean", paramType = "body")
    })
    @RequiresPermissions("course/checkRecord:update")
    @PutMapping("/lesson")
    public ResponseEntity changeAttendanceRecord(@RequestBody @Valid AttendanceRecordRequestBean attendanceRecord)
            throws ParseException, BaseServiceException {
        Lesson lesson = new Lesson();
        lesson.setId(attendanceRecord.getLessonId());
        lesson.getCourse().setCourseId(attendanceRecord.getCourseId());
        lesson.setCheckInTime(attendanceRecord.getCheckInTime());
        lesson.setCheckOutTime(attendanceRecord.getCheckOutTime());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd HH:MM");
        Date date1 = formatter.parse(lesson.getCheckInTime());
        Date date2 = formatter.parse(lesson.getCheckOutTime());
        double millisecond = date2.getTime() - date1.getTime();
        //计算修改后课程时长
        double hour = millisecond / (60 * 60 * 1000);
        hour = (double) Math.round(hour * 100) / 100;
        lesson.setClassTime(hour);
        //原始时长
        double hour1 = attendanceRecord.getOldClassTime();
        if (hour1 == -1) {
            hour1 = 0;
        }
        //第二个参数为原始时长以及修改后时长的差额进行课时记录的改变
        courseMngService.changeAttendanceRecord(lesson, hour - hour1, attendanceRecord.getCourseHourRecordId());
        return ResultUtil.success();
    }

    @ApiOperation(value = "管理员获取所有教师")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Teacher.class, responseContainer = "List")
    })
    @GetMapping("/allTeacher")
    public ResponseEntity getAllTeacher() {
        return ResultUtil.success(courseMngService.getAllTeacher());
    }

    @ApiOperation(value = "管理员给课程设置教师")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseId", value = "课程ID编号", required = true,
                    dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "teacherId", value = "教师ID编号", required = true,
                    dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "state", value = "状态（更换教师0还是设置教师1）", required = true,
                    dataType = "Integer", paramType = "query")
    })
    @RequiresPermissions("course/teacher:update")
    @PutMapping("/course/teacher")
    public ResponseEntity setTeacherForCourse(@RequestParam("courseId") Integer courseId,
                                              @RequestParam("teacherId") Integer teacherId,
                                              @RequestParam("state") Integer state) throws SQLOperationException, IllegalParameterException {
        if (state == 1) {
            //设置教师
            courseMngService.setTeacherForCourse(courseId, teacherId);
        } else {
            //更换教师
            Integer oldTeacherId = courseMngService.getTeacherIdByCourseId(2, courseId);
            if (oldTeacherId != null && !oldTeacherId.equals(teacherId)) {
                lessonsService.cleanAllLessonsByWeek(oldTeacherId, courseId);
                courseMngService.changeTeacherForCourse(courseId, teacherId);
            }
        }
        return ResultUtil.success();
    }

    @ApiOperation(value = "管理员得到分配试讲的教师页面,(0默认代表不是系统推荐，1代表系统推荐)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseId", value = "课程ID编号", required = true,
                    dataType = "Integer", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = TeacherListResponseBean.class, responseContainer = "List")
    })
    @GetMapping("/teacher/temp")
    public ResponseEntity getTempTeacher(@RequestParam("courseId") Integer courseId,
                                         @RequestParam("flag") Integer flag,
                                         @RequestParam(required = false)String  queryCondition,
                                         @RequestParam(required = false)String grade,
                                         @RequestParam(required = false)String subject,
                                         @RequestParam(required = false)String sex,
                                         @RequestParam(required = false)String state,
                                         @RequestParam Integer pageNo, @RequestParam Integer limit) throws BaseServiceException {
        Page page = new Page(limit, pageNo);
        return ResultUtil.success(courseMngService.getTempTeacher(courseId, flag, page,queryCondition,grade,subject,sex,state));
    }

    @ApiOperation(value = "管理员安排/取消试讲")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseId", value = "课程ID编号", required = true,
                    dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "teacherId", value = "教员ID编号", required = true,
                    dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "state", value = "是否安排试讲还是取消试讲", required = true,
                    dataType = "Integer", paramType = "query")
    })
    @PutMapping("/tempSpeaking")
    public ResponseEntity setTempSpeakingTeacher(@RequestParam("id") Integer courseId,
                                                 @RequestParam("teacherId") Integer teacherId,
                                                 @RequestParam("state") Integer state) throws BaseServiceException {
        if (state == 1) {
            courseMngService.setTempSpeakingTeacher(courseId, teacherId);
        } else {
            courseMngService.cancleTempSpeakingTeacher(courseId, teacherId);
        }
        return ResultUtil.success();
    }

    @ApiOperation(value = "管理员获取所有有课的教员")
    @GetMapping("/admin/teacher")
    public ResponseEntity getHasCourseTeacher() {
        return ResultUtil.success(courseMngService.getHasCourseTeacher());
    }

    @ApiOperation(value = "管理员获取教师所上的课")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teacherId", value = "教师的ID", required = true,
                    dataType = "Integer", paramType = "path")
    })
    @GetMapping("/teacher/course/{teacherId}")
    public ResponseEntity getTeacherCourse(@PathVariable("teacherId") Integer teacherId) {
        return ResultUtil.success(courseMngService.getTeacherCourse(teacherId));
    }


    /**
     * 获取当周课程
     *
     * @param pageNo
     * @param limit
     * @return
     */
    @ApiOperation(value = "管理员获取当周课程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前请求页号", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "请求数量", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "studentName", value = "学生名", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "teacher", value = "老师名", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "grade", value = "班级", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "subject", value = "学科", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "headTeacher", value = "班主任", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, dataType = "String", paramType = "query")

    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = CurrentCourseResponseBean.class, responseContainer = "List")
    })
    @RequiresPermissions("course/thisWeek:list")
    @GetMapping("/admin/curr_course/list")
    public ResponseEntity getCurrentCourses(@RequestParam Integer pageNo, @RequestParam Integer limit,
                                            @RequestParam(required = false)String studentName,@RequestParam(required= false)String teacher,
                                            @RequestParam(required = false)String grade,@RequestParam(required= false)String subject,
                                            @RequestParam(required = false)String headTeacher,@RequestParam(required= false)String startTime,@RequestParam(required= false)String endTime
                                                                                                        ) {

        Page page = new Page(limit, pageNo);
        NewPageInfo<CurrentCourseResponseBean> currentCourseList = courseMngService.getCurrCourseList(page,studentName,teacher,grade,subject,headTeacher,startTime,endTime);
        return ResultUtil.success(currentCourseList);
    }

    /**
     * 根据课程id获取课时记录
     */
    @ApiOperation(value = "根据课程id获取课时记录列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseId", value = "课程ID编号", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "当前请求页号", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "请求数量", required = true, dataType = "Integer", paramType = "query"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = CourseHourRecord.class, responseContainer = "List")
    })
    @GetMapping("/admin/course_hour_detail/list")
    public ResponseEntity getCourseHourList(@RequestParam Integer pageNo, @RequestParam Integer limit,@RequestParam("courseId") Integer courseId) {
        Page page = new Page(limit, pageNo);
        NewPageInfo<CourseHourRecord> courseHourRecordList = courseMngService.getAllCourseHourRecordByCourseId(courseId,page);
        return ResultUtil.success(courseHourRecordList);
    }

    @ApiOperation(value = "管理员根据课程id获取总课时记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseId", value = "课程ID编号", required = true, dataType = "Integer", paramType = "query"),
    })
    @GetMapping("/admin/course_hour_detail")
    public ResponseEntity getTotalCourseHour(@RequestParam("courseId") Integer courseId) {
        CourseHourRecordDetail totalCourseHour = courseMngService.getTotalCourseHour(courseId);
        return ResultUtil.success(totalCourseHour);
    }

    @ApiOperation(value = "管理员添加课时记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "addCourseHour", value = "新增课时记录信息体", required = true,
                    dataType = "AddCourseHour", paramType = "body")
    })
    @RequiresPermissions("courseHourRecord:create")
   @PostMapping("/admin/add_course_hour")
   public ResponseEntity addCourseHour(@RequestBody AddCourseHour  addCourseHour) throws BaseServiceException {
         courseMngService.addCourseHour(addCourseHour);
        return ResultUtil.success();
   }
    @ApiOperation(value = "管理员赠送课时记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "giveFreeCourseHour", value = "赠送课时记录信息体", required = true,
                    dataType = "GiveFreeCourseHour", paramType = "body")
    })
    @RequiresPermissions("courseHourRecord:create")
    @PostMapping("/admin/give_course_hour")
    public ResponseEntity giveCourseHour(@RequestBody GiveFreeCourseHour  giveFreeCourseHour) throws BaseServiceException {
        courseMngService.giveFreeCourseHour(giveFreeCourseHour);
        return ResultUtil.success();
    }
}
