package com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.controller;

import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.entity.*;
import com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.bean.BindAuditionClassBean;
import com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.bean.CreateFlowTypeBean;
import com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.bean.SaveReviewForm;
import com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.bean.SubmitFlowBean;
import com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.service.ICreateAppointmentService;
import com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.service.ICreateFlowService;
import com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.service.ICreateFormService;
import com.dataee.tutorserver.utils.ResultUtil;
import com.dataee.tutorserver.utils.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;

@Api(value = "创建流程")

//@RequiresPermissions("auth:ed")
@RestController
@RequestMapping("/")
public class CreateFlowController {
    @Autowired
    private ICreateFlowService createFlowService;

    @Autowired
    private ICreateFormService createFormService;

    @Autowired
    private ICreateAppointmentService createAppointmentService;

    @ApiOperation(value = "创建流程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "createFlowTypeBean", value = "创建流程信息体", required = true,
                    dataType = "CreateFlowTypeBean", paramType = "body")
    })
    @RequiresRoles(value = {"courseConsultant"}, logical = Logical.OR)
    @PostMapping("createFlow")
    public ResponseEntity createWorkFlow(@RequestBody CreateFlowTypeBean createFlowTypeBean) throws BaseControllerException {
        createFlowService.createFlow(createFlowTypeBean);
        return ResultUtil.success();
    }


    @ApiOperation(value = "保存个性化情况调研表单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "registerForm", value = "创建情况调研信息体", required = true,
                    dataType = "RegisterForm", paramType = "body")
    })
    @RequiresRoles(value = {"courseConsultant"}, logical = Logical.OR)
    @PostMapping("saveRegisterForm")
    public ResponseEntity saveRegisterForm(@RequestBody @Valid RegisterForm registerForm) throws BaseControllerException, BaseServiceException {
        createFormService.saveRegisterForm(registerForm);
        return ResultUtil.success();
    }


    @ApiOperation(value = "保存陪伴式情况调研表单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "accompanyRegisterForm", value = "创建陪伴式情况调研信息体", required = true,
                    dataType = "AccompanyRegisterForm", paramType = "body")
    })
    @RequiresRoles(value = {"courseConsultant"}, logical = Logical.OR)
    @PostMapping("saveAccompanyRegisterForm")
    public ResponseEntity saveRegisterForm(@RequestBody @Valid AccompanyRegisterForm accompanyRegisterForm) throws BaseControllerException, BaseServiceException {
        createFormService.saveAccompanyRegisterForm(accompanyRegisterForm);
        return ResultUtil.success();
    }

    @ApiOperation(value = "保存班主任审核表单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "saveReviewForm", value = "创建班主任审核信息体", required = true,
                    dataType = "SaveReviewForm", paramType = "body")
    })
    @RequiresRoles(value = {"headMaster"}, logical = Logical.OR)
    @PostMapping("saveReviewForm")
    public ResponseEntity saveReviewForm (@RequestBody SaveReviewForm saveReviewForm) throws BaseControllerException, BaseServiceException {
        createFormService.saveReviewForm(saveReviewForm);
        return ResultUtil.success();
    }

    @ApiOperation(value = "保存选聘老师表单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "matchTeacherForm", value = "创建选聘老师信息体", required = true,
                    dataType = "MatchTeacherForm", paramType = "body")
    })
    @RequiresRoles(value = {"hireTeacher"}, logical = Logical.OR)
    @PostMapping("saveMatchTeacherForm")
    public ResponseEntity saveMatchTeacherForm (@RequestBody MatchTeacherForm matchTeacherForm) throws BaseControllerException, BaseServiceException {
        createFormService.saveMatchTeacherForm(matchTeacherForm);
        return ResultUtil.success();
    }

    @ApiOperation(value = "保存培训表单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "trainForm", value = "创建培训信息体", required = true,
                    dataType = "TrainForm", paramType = "body")
    })
    @RequiresRoles(value = {"trainDepartment"}, logical = Logical.OR)
    @PostMapping("saveTrainForm")
    public ResponseEntity saveTrainForm (@RequestBody TrainForm trainForm) throws BaseControllerException, BaseServiceException {
        createFormService.saveTrainForm(trainForm);
        return ResultUtil.success();
    }

    @ApiOperation(value = "保存试听课反馈表单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "auditionFeedbackForm", value = "创建试听课反馈信息体", required = true,
                    dataType = "AuditionFeedbackForm", paramType = "body")
    })
    @RequiresRoles(value = {"marketConsultant"}, logical = Logical.OR)
    @PostMapping("saveAuditionFeedbackForm")
    public ResponseEntity saveAuditionFeedbackForm (@RequestBody  AuditionFeedbackForm auditionFeedbackForm) throws BaseControllerException, BaseServiceException {
        createFormService.saveAuditionFeedbackForm(auditionFeedbackForm);
        return ResultUtil.success();
    }

    @ApiOperation(value = "提交情况调研表单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "submitFlowBean", value = "提交情况调研信息体", required = true,
                    dataType = "SubmitFlowBean", paramType = "body")
    })
    @RequiresRoles(value = {"courseConsultant"}, logical = Logical.OR)
    @PostMapping("submitRegisterForm")
    public ResponseEntity submitRegisterForm(@RequestBody @Valid  SubmitFlowBean submitFlowBean) throws BaseControllerException, BaseServiceException {
        createFormService.submitRegisterForm(submitFlowBean);
        return ResultUtil.success();
    }

    @ApiOperation(value = "提交班主任审核表单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "submitFlowBean", value = "提交班主任审核信息体", required = true,
                    dataType = "SubmitFlowBean", paramType = "body")
    })
    @RequiresRoles(value = {"headMaster"}, logical = Logical.OR)
    @PostMapping("submitHeadMasterExamineForm")
    public ResponseEntity submitHeadMasterExamine(@RequestBody  @Valid SubmitFlowBean submitFlowBean) throws BaseControllerException, BaseServiceException {
        createFormService.submitReviewForm(submitFlowBean);
        return ResultUtil.success();
    }


    @ApiOperation(value = "提交选聘老师表单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "submitFlowBean", value = "提交选聘老师信息体", required = true,
                    dataType = "SubmitFlowBean", paramType = "body")
    })
    @RequiresRoles(value = {"hireTeacher"}, logical = Logical.OR)
    @PostMapping("submitMatchTeacherForm")
    public ResponseEntity submitMatchTeacherForm(@RequestBody @Valid SubmitFlowBean submitFlowBean) throws BaseControllerException, BaseServiceException {
        createFormService.submitMatchTeacherForm(submitFlowBean);
        return ResultUtil.success();
    }

    @ApiOperation(value = "提交培训老师表单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "submitFlowBean", value = "提交培训老师信息体", required = true,
                    dataType = "SubmitFlowBean", paramType = "body")
    })
    @RequiresRoles(value = {"trainDepartment"}, logical = Logical.OR)
    @PostMapping("submitTrainForm")
    public ResponseEntity submitTrainForm(@RequestBody @Valid SubmitFlowBean submitFlowBean) throws BaseControllerException, BaseServiceException {
        createFormService.submitTrainForm(submitFlowBean);
        return ResultUtil.success();
    }


    @ApiOperation(value = "提交试听课反馈表单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "submitFlowBean", value = "提交试听课反馈信息体", required = true,
                    dataType = "SubmitFlowBean", paramType = "body")
    })
    @RequiresRoles(value = {"marketConsultant"}, logical = Logical.OR)
    @PostMapping("submitAuditionFeedbackForm")
    public ResponseEntity submitAuditionFeedbackForm(@RequestBody  SubmitFlowBean submitFlowBean) throws BaseControllerException, BaseServiceException {
        createFormService.submitAuditionFeedbackForm(submitFlowBean);
        return ResultUtil.success();
    }

    @ApiOperation(value = "预约试听课")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appointment", value = "创建预约试听课信息体", required = true,
                    dataType = "Appointment", paramType = "body"),
            @ApiImplicitParam(name = "adminId", value = "提交给谁", required = true,
                    dataType = "Integer")
    })
    @RequiresRoles(value = {"courseConsultant"}, logical = Logical.OR)
    @PostMapping("saveAppointAuditionClass")
    public ResponseEntity bindAuditionClass (@RequestBody @Valid BindAuditionClassBean bindAuditionClassBean) throws BaseControllerException, BaseServiceException {
        createAppointmentService.appointAuditionClass(bindAuditionClassBean);
        return ResultUtil.success();
    }

    @ApiOperation(value = "预约记录列表")
            @ApiImplicitParams({
                    @ApiImplicitParam(name = "pageNo", value = "页码", required = true,
                            dataType = "int", paramType = "query"),
                    @ApiImplicitParam(name = "limit", value = "个数", required = true,
                            dataType = "int", paramType = "query"),
                    @ApiImplicitParam(name = "workFlowId", value = "流程id", required = true,
                            dataType = "Integer", paramType = "query"),
    })
    @GetMapping("appointList")
    public ResponseEntity waitCourseRecord (@RequestParam("pageNo")Integer pageNo ,@RequestParam("limit")Integer limit, @RequestParam("workFlowId") Integer workFlowId) throws BaseControllerException, BaseServiceException {
        Page page = new Page(limit, pageNo);
        return ResultUtil.success(createAppointmentService.appointList(workFlowId,page));
    }

    @ApiOperation(value = "培训部确认")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "workFlowId", value = "流程Id", required = true,
                    dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "nodeId", value = "当前流程最后一个节点的id", required = true,
                    dataType = "Integer", paramType = "query"),
    })
    @RequiresRoles(value = {"trainDepartment"}, logical = Logical.OR)
    @PostMapping ("trainDepartmentAccept")
    public ResponseEntity trainDepartmentAccept (@RequestParam("workFlowId")Integer workFlowId,@RequestParam("nodeId")Integer nodeId) throws BaseControllerException, BaseServiceException {
        createAppointmentService.trainDepartmentAccept(workFlowId,nodeId);
        return ResultUtil.success();
    }

    @ApiOperation(value = "培训部驳回")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "workFlowSn", value = "流程编号", required = true,
                    dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "nodeId", value = "当前流程最后一个节点的id", required = true,
                    dataType = "Integer", paramType = "query"),
    })
    @RequiresRoles(value = {"trainDepartment"}, logical = Logical.OR)
    @PostMapping ("trainDepartmentRefuse")
    public ResponseEntity trainDepartmentRefuse (@RequestParam("workFlowId")Integer workFlowId,@RequestParam("nodeId")Integer nodeId) throws BaseControllerException, BaseServiceException {
        createAppointmentService.trainDepartmentRefuse(workFlowId,nodeId);
        return ResultUtil.success();
    }

}
