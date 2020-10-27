package com.dataee.tutorserver.tutoradminserver.teachermanage.controller;

import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.entity.Course;
import com.dataee.tutorserver.entity.CourseHourRecord;
import com.dataee.tutorserver.entity.Teacher;
import com.dataee.tutorserver.tutoradminserver.messagemanage.bean.InfoChangeVerifyRequestBean;
import com.dataee.tutorserver.tutoradminserver.patriarchmanage.bean.ClassAndHour;
import com.dataee.tutorserver.tutoradminserver.statemanage.service.IStateManageService;
import com.dataee.tutorserver.tutoradminserver.teachermanage.bean.PlatformInfoChangeRequestBean;
import com.dataee.tutorserver.tutoradminserver.teachermanage.bean.TeacherDetailResponseBean;
import com.dataee.tutorserver.tutoradminserver.teachermanage.bean.TeacherListResponseBean;
import com.dataee.tutorserver.tutoradminserver.teachermanage.service.ITeacherManageService;
import com.dataee.tutorserver.tutorpatriarchserver.service.IParentCourseService;
import com.dataee.tutorserver.tutorteacherserver.bean.ScoreBean;
import com.dataee.tutorserver.tutorteacherserver.service.ITeacherCenterService;
import com.dataee.tutorserver.userserver.bean.GetCourseTeacherListResponseBean;
import com.dataee.tutorserver.utils.DoubleToTwo;
import com.dataee.tutorserver.utils.ResultUtil;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/10 23:29
 */
@Api(value = "管理员端的教师管理模块")
@RestController
@RequestMapping("/")
public class TeacherManageController {
    @Autowired
    private ITeacherManageService teacherManageService;
    @Autowired
    private IStateManageService stateManageService;
    @Autowired
    private ITeacherCenterService teacherCenterService;
    @Autowired
    private IParentCourseService parentCourseService;


    /**
     * 获取教师已认证列表
     *
     * @return
     */
    @ApiOperation(value = "管理员获取教师已认证列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前第几页", required = true,
                    dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "每页显示几条数据", required = true,
                    dataType = "Integer", paramType = "query"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = TeacherListResponseBean.class, responseContainer = "List")
    })
    @RequiresPermissions("teacher/formal:list")
    @GetMapping("/admin/authenticated/teacherList")
    public ResponseEntity getTeacherAuthEdList(@RequestParam Integer pageNo, @RequestParam Integer limit) {
        Page page = new Page(limit, pageNo);
        return ResultUtil.success(teacherManageService.getTeacherAuthEdList(page));
    }

    /**
     * 获取教师未认证列表
     *
     * @return
     */
    @ApiOperation(value = "管理员获取教师未认证列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前第几页", required = true,
                    dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "每页显示几条数据", required = true,
                    dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "queryCondition", value = "查询条件", required = true,
                    dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "state", value = "查询状态", required = true,
                    dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sex", value = "性别", required = true,
                    dataType = "String", paramType = "query"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = TeacherListResponseBean.class, responseContainer = "List")
    })
    @RequiresPermissions("teacher/application:list")
    @GetMapping("/admin/unauthenticated/teacherList")
    public ResponseEntity getTeacherAuthIngList(@RequestParam Integer pageNo, @RequestParam Integer limit,@RequestParam String queryCondition,String state,String sex ) {


        Page page = new Page(limit, pageNo);
        return ResultUtil.success(teacherManageService.getTeacherAuthIngList(page,queryCondition,state,sex));
    }

    /**
     * 获取教师详细信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "管理员获取教师详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "教师id", required = true,
                    dataType = "String", paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Teacher.class)
    })
    @RequiresPermissions(value = {"teacher/application:read", "teacher/formal:read"}, logical = Logical.OR)
    @GetMapping("/teacherDetail/{id}")
    public ResponseEntity getTeacherDetail(@PathVariable("id") @NotBlank(message = "教师ID不能为空") String id) {
        //获取教师基本信息
        Teacher teacher = teacherManageService.getTeacherDetail(id);
        teacher.setTeacherLabel(DoubleToTwo.convertDoubleToTwo(teacher.getTeacherLabel()));
        return ResultUtil.success(teacher);
    }

    @ApiOperation(value = "管理员获取教师课程信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "教师id", required = true,
                    dataType = "String", paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = ClassAndHour.class)
    })
    @RequiresPermissions("teacher/course:list")
    @GetMapping("/teacherCourse/{id}")
    public ResponseEntity getTeacherCourse(@PathVariable("id") @NotBlank(message = "教师ID不能为空") String id) {
        String teacherId = teacherManageService.getTeacherIdOfCourse(id);
        if (!(teacherId == null || teacherId.equals(""))) {
            List<ClassAndHour> classAndHourList = new ArrayList<>();
            //获取家长的课程列表信息
            List<GetCourseTeacherListResponseBean> courseTeacherListResponseBean = parentCourseService.getCourseList("course.parent_id", Integer.parseInt(id));
            if (courseTeacherListResponseBean.size() != 0) {
                //通过课程列表信息循环通过course_id查找其对应的课时信息将课程和课时封装起来
                for (GetCourseTeacherListResponseBean c : courseTeacherListResponseBean) {
                  //  Course course = parentCourseService.getCourseDetailInfo(String.valueOf(c.getCourseId()));
                    ClassAndHour classAndHour = new ClassAndHour();
                    classAndHour.setCourseName(c.getCourseName());
                    classAndHour.setTeacherName(c.getTeacherName());
                    classAndHour.setStudentName(c.getStudentName());
                    if (c.getCourseHourRecords().size()!=0) {
                        double totalCourseHour =0.0;
                        double comsumeCourseHour =0.0;
                        for(CourseHourRecord hourRecord:c.getCourseHourRecords()){
                            totalCourseHour+=hourRecord.getTotalClassHour();
                            comsumeCourseHour+=hourRecord.getConsumeClassHour();
                        }
                        classAndHour.setTotalClassHour(totalCourseHour);
                        classAndHour.setConsumeClassHour(comsumeCourseHour);
                        classAndHour.setRestClassHour(totalCourseHour-comsumeCourseHour);
                    }
                    classAndHourList.add(classAndHour);
                }
            }
            return ResultUtil.success(classAndHourList);
        } else {
            return ResultUtil.success();
        }



    }

    /**
     * 审核教员报名信息
     *
     * @param infoChangeVerifyRequestBean
     * @return
     * @throws BaseControllerException
     * @throws BaseServiceException
     */
    @ApiOperation(value = "管理员审核报名信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "infoChangeVerifyRequestBean", value = "审核信息体", required = true,
                    dataType = "InfoChangeVerifyRequestBean", paramType = "body")
    })
    @RequiresPermissions("teacher:update")
    @PutMapping("/teacherNextInfoVerify")
    public ResponseEntity verifyTeacherNextInfo(@RequestBody @Valid InfoChangeVerifyRequestBean infoChangeVerifyRequestBean)
            throws BaseControllerException, BaseServiceException {
        if (infoChangeVerifyRequestBean.getAccepted() == 1) {
            teacherManageService.acceptTeacherNextInfo(infoChangeVerifyRequestBean);
        } else {
            teacherManageService.denyTeacherNextInfo(infoChangeVerifyRequestBean);
        }
        return ResultUtil.success();
    }

    /**
     * 修改教师状态
     *
     * @param id
     * @return
     * @throws BaseServiceException
     */
    @ApiOperation(value = "管理员修改教师状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "教师id", required = true,
                    dataType = "Integer", paramType = "path")
    })
    @RequiresPermissions("teacher:disableEnable")
    @PutMapping("/teacher/state/{id}")
    public ResponseEntity changeTeacherState(@PathVariable("id") @NotBlank(message = "教师ID不能为空") Integer id)
            throws BaseServiceException, BaseControllerException {
        stateManageService.changeDataState("teacher", id);
        return ResultUtil.success();
    }

    /**
     * 查询教师信息
     *
     * @param queryCondition
     * @param state
     * @param sex
     * @return
     */
    @ApiOperation(value = "管理员筛选教员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryCondition", value = "查询条件", required = true,
                    dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "state", value = "查询状态", required = true,
                    dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sex", value = "性别", required = true,
                    dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "start", value = "开始带单", required = true,
                    dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "end", value = "结束带单", required = true,
                    dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "当前第几页", required = true,
                    dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "每页显示几条数据", required = true,
                    dataType = "Integer", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = TeacherListResponseBean.class, responseContainer = "List")
    })
    @GetMapping("/teacher/queryCondition")
    public ResponseEntity getAuthenticatedTeacherByQueryCondition(@RequestParam(required = false,value = "queryCondition") String queryCondition, @RequestParam(required = false,value = "state") String state, @RequestParam(required = false,value = "sex") String sex,@RequestParam(required = false,value = "start") Integer start, @RequestParam(required = false,value = "end") Integer end,@RequestParam Integer pageNo, @RequestParam Integer limit) {
        Page page = new Page(limit, pageNo);
        return ResultUtil.success(teacherManageService.queryTeacher(queryCondition, state, sex,start,end, page));
    }
    @ApiOperation("获取历史成绩")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teacherId", value = "教师ID", required = true,
                    dataType = "Integer", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = ScoreBean.class, responseContainer = "List")
    })
    @RequiresPermissions("teacher/score:list")
    @GetMapping("/admin/score/history")
    public ResponseEntity getScoreHistory(@RequestParam("teacherId") Integer teacherId, @RequestParam Integer pageNo, @RequestParam Integer limit) {
        Page page = new Page(limit, pageNo);
        return ResultUtil.success(teacherCenterService.getScoreHistory(teacherId, page));
    }

    /**
     * 修改平台信息即面试结果和授课范围
     *
     * @param platformChangeInfo
     * @return
     */
    @ApiOperation(value = "管理员修改平台信息即面试结果和授课范围和产品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "platformChangeInfo", value = "修改信息体", required = true,
                    dataType = "PlatformInfoChangeRequestBean", paramType = "body")
    })
    @RequiresPermissions("teacher/platformInfo:update")
    @PutMapping("/platformInfo")
    public ResponseEntity changePlatformInfo(@RequestBody PlatformInfoChangeRequestBean platformChangeInfo) {
        teacherManageService.changePlatformInfo(platformChangeInfo);
        return ResultUtil.success();
    }

    /**
     * 修改平台介绍即备注信息
     * @param platformIntroduce
     * @param teacherId
     * @return
     */
    @ApiOperation(value = "管理员修改平台介绍即备注信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "platformIntroduce", value = "平台介绍", required = true,
                    dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "teacherId", value = "教师ID", required = true,
                    dataType = "Integer", paramType = "query")
    })
    @RequiresPermissions("teacher/platformIntroduction:update")
    @PutMapping("/platformIntroduce")
    public ResponseEntity changePlatformIntroduce(@RequestParam("platformIntroduce") String platformIntroduce,
                                                  @RequestParam("teacherId") Integer teacherId) {
        teacherManageService.changePlatformIntroduce(platformIntroduce, teacherId);
        return ResultUtil.success();
    }
}
