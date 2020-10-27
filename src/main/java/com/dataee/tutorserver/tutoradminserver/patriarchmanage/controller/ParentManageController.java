package com.dataee.tutorserver.tutoradminserver.patriarchmanage.controller;

import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.entity.*;
import com.dataee.tutorserver.tutoradminserver.messagemanage.bean.InfoChangeVerifyRequestBean;
import com.dataee.tutorserver.tutoradminserver.patriarchmanage.bean.ClassAndHour;
import com.dataee.tutorserver.tutoradminserver.patriarchmanage.bean.FormalParent;
import com.dataee.tutorserver.tutoradminserver.patriarchmanage.bean.UpdateInvitation;
import com.dataee.tutorserver.tutoradminserver.patriarchmanage.bean.UpdateInvitationPage;
import com.dataee.tutorserver.tutoradminserver.patriarchmanage.service.IParentManageService;
import com.dataee.tutorserver.tutoradminserver.statemanage.service.IStateManageService;
import com.dataee.tutorserver.tutorpatriarchserver.service.IParentCenterService;
import com.dataee.tutorserver.tutorpatriarchserver.service.IParentCourseService;
import com.dataee.tutorserver.userserver.bean.GetCourseTeacherListResponseBean;
import com.dataee.tutorserver.userserver.service.ICourseService;
import com.dataee.tutorserver.utils.FiledErrorsUtil;
import com.dataee.tutorserver.utils.ResultUtil;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/10 15:21
 */
@Api(value = "管理员端家长管理模块")
@RestController
@RequestMapping("/")
public class ParentManageController {
    @Autowired
    private IParentManageService parentManageService;
    @Autowired
    private IParentCenterService parentCenterService;
    @Autowired
    private IParentCourseService parentCourseService;
    @Autowired
    private IStateManageService stateManageService;
    @Autowired
    private ICourseService courseService;

    /**
     * 获得已认证家长列表
     *
     * @return
     */
    @ApiOperation(value = "管理员获得已认证家长列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前请求页号", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "请求数量", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "studentName", value = "学生名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sex", value = "性别", required = true, dataType = "String", paramType = "query"),

    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = FormalParent.class, responseContainer = "List")
    })
    @RequiresPermissions("parent/formal:list")
    @GetMapping("/authenticated/parentList")
    public ResponseEntity getAuthEdParentList(@RequestParam Integer pageNo, @RequestParam Integer limit,
                                              @RequestParam String  studentName, @RequestParam String sex ) {
        Page page = new Page(limit, pageNo);
        NewPageInfo<Parent> newPageInfo = parentManageService.getAuthEdParentList(page,studentName,sex);
        List<Parent> parents = newPageInfo.getList();
        List<FormalParent> formalParents = FormalParent.fromParents(parents);
        NewPageInfo<FormalParent> formalParentPage = new NewPageInfo<>(
                newPageInfo.getPageNum(),
                newPageInfo.getPages(),
                newPageInfo.getSize(),
                newPageInfo.getTotal(),
                formalParents
        );
        return ResultUtil.success(formalParentPage);
    }

    /**
     * 获得未认证家长列表
     *
     * @return
     */
    @ApiOperation(value = "管理员获得未认证家长列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前请求页号", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "请求数量", required = true, dataType = "Integer", paramType = "query"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Parent.class, responseContainer = "List")
    })
    @RequiresPermissions("parent/application:list")
    @GetMapping("/unauthenticated/parentList")
    public ResponseEntity getAuthIngParentList(@RequestParam Integer pageNo, @RequestParam Integer limit  ) {
        Page page = new Page(limit, pageNo);
        return ResultUtil.success(parentManageService.getParentList(2,page));
    }

    @ApiOperation(value = "管理员查看家长个人信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "家长id", required = true,
                    dataType = "String", paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Parent.class)
    })
    @RequiresPermissions(value = {"parent/application:read", "parent/formal:read"}, logical = Logical.OR)
    @GetMapping("/parentDetail/{id}")
    public ResponseEntity getParentDetail(@PathVariable("id") @NotBlank(message = "家长ID不能为空") String id) {
        //获取家长基本信息
        Parent parent = parentManageService.getParentDetail(id);
        return ResultUtil.success(parent);
    }

    /**
     * 获取家长的课程信息
     *
     * @param id
     * @return
     */
    @RequiresPermissions("parent/course:list")
    @GetMapping("/parentDetail/course/{id}")
    public ResponseEntity getParentCourse(@PathVariable("id") @NotBlank(message = "家长ID不能为空") String id) {
        List<ClassAndHour> classAndHourList = new ArrayList<>();
        //获取家长的课程列表信息
        List<GetCourseTeacherListResponseBean> courseTeacherListResponseBean = parentManageService.getCourseList("course.parent_id", id);
        if (courseTeacherListResponseBean.size() != 0) {
            //通过课程列表信息循环通过course_id查找其对应的课时信息将课程和课时封装起来
            for (GetCourseTeacherListResponseBean c : courseTeacherListResponseBean) {
                Course course = parentCourseService.getCourseDetailInfo(String.valueOf(c.getCourseId()));
                ClassAndHour classAndHour = new ClassAndHour();
                classAndHour.setContractPdf(c.getContractPdf());
                classAndHour.setCourseName(c.getCourseName());
                classAndHour.setTeacherName(c.getTeacherName());
                classAndHour.setStudentName(c.getStudentName());
                if (course.getCourseHourRecord().size()!=0) {
                       double totalCourseHour =0.0;
                       double comsumeCourseHour =0.0;
                       for(CourseHourRecord hourRecord:course.getCourseHourRecord()){
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
    }

    /**
     * 获取家长的子女信息
     *
     * @param id
     * @return
     */
    @RequiresPermissions("parent/student:list")
    @GetMapping("/parentDetail/student/{id}")
    public ResponseEntity getParentStudent(@PathVariable("id") @NotBlank(message = "家长ID不能为空") String id) {
        //获取家长的子女信息
        List<Student> student = parentManageService.getOwnChildren(id);
        for (Student student1 : student) {
            List<String> weakDiscipline = parentCenterService.getWeakDiscipline(student1.getStudentId());
            student1.setWeakDiscipline(weakDiscipline);
        }
        return ResultUtil.success(student);
    }

    @ApiOperation(value = "管理员审核家长端信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "infoChangeVerifyRequestBean", value = "审核信息体", required = true,
                    dataType = "InfoChangeVerifyRequestBean", paramType = "body")
    })
    @RequiresPermissions("parent:update")
    @PostMapping("/parentDetailInfoVerify")
    public ResponseEntity verifyParentDetail(@RequestBody @Valid InfoChangeVerifyRequestBean infoChangeVerifyRequestBean,
                                             BindingResult bindingResult) throws BaseControllerException, BaseServiceException {
        FiledErrorsUtil.getErrorException(bindingResult);
        if (infoChangeVerifyRequestBean.getAccepted() == 1) {
            parentManageService.acceptParentDetail(infoChangeVerifyRequestBean);
        } else {
            parentManageService.denyParentDetail(infoChangeVerifyRequestBean);
        }
        return ResultUtil.success();
    }

    /**
     * 改变家长状态
     *
     * @param id
     * @return
     * @throws BaseServiceException
     */
    @ApiOperation(value = "管理员改变家长状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "家长id", required = true,
                    dataType = "Integer", paramType = "path")
    })
    @RequiresPermissions("parent:disableEnable")
    @PutMapping("/parent/state/{id}")
    public ResponseEntity changeParentState(@PathVariable("id") @NotBlank(message = "家长ID不能为空") Integer id)
            throws BaseServiceException, BaseControllerException {
        stateManageService.changeDataState("parent", id);
        return ResultUtil.success();
    }

    /** 改参数
     * 查询家长信息
     *
     * @param queryCondition
     * @param state
     * @param sex
     * @return
     */
    @ApiOperation(value = "管理员查询家长申请信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryCondition", value = "查询条件", required = true,
                    dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "state", value = "查询状态", required = true,
                    dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sex", value = "性别", required = true,
                    dataType = "String", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Parent.class, responseContainer = "List")
    })
    @GetMapping("/parent/queryCondition")
    public ResponseEntity getParentByQueryCondition(@RequestParam("queryCondition") String queryCondition,
                                                    @RequestParam("state") String state, @RequestParam("sex") String sex,
                                                    @RequestParam Integer pageNo, @RequestParam Integer limit
                                                    ) {
        Page page = new Page(limit, pageNo);
        return ResultUtil.success(parentManageService.queryParent(queryCondition, state, sex, page));
    }

    @ApiOperation(value = "管理员获取修改家长邀请页面")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = UpdateInvitationPage.class)
    })
    @GetMapping("/updateParentInviteInfoPage")
    public ResponseEntity getUpdateParentInviteInfo() {
        List<Partner> partnerList = parentManageService.getPartnerList();
        List<ParentLevel> parentLevelList = parentManageService.getParentLevelList();
        UpdateInvitationPage updateInvitation =new UpdateInvitationPage();
        updateInvitation.setParentLevel(parentLevelList);
        updateInvitation.setPartners(partnerList);
        return ResultUtil.success(updateInvitation);
    }

    @ApiOperation(value = "管理员修改家长邀请")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = UpdateInvitationPage.class)
    })
    @RequiresPermissions("parent/invitation:update")
    @PostMapping("/updateParentInviteInfo")
    public ResponseEntity updateParentInviteInfo(@RequestBody UpdateInvitation updateInvitation) {
        parentManageService.changeInvitation(updateInvitation);
        return ResultUtil.success();
    }
}
