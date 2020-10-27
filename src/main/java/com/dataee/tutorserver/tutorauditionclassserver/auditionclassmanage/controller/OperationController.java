package com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.controller;

import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.entity.Appointment;
import com.dataee.tutorserver.entity.MatchTeacherForm;
import com.dataee.tutorserver.entity.Operation;
import com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.bean.CancelAuditionClassBean;
import com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.bean.TeacherConsumeRequestBean;
import com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.service.ICreateFlowService;
import com.dataee.tutorserver.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(value = "对流程进行操作")
@RestController
@RequestMapping("/")
public class OperationController {
    @Autowired
    private ICreateFlowService createFlowService;

    @ApiOperation(value = "更换老师操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cancelAuditionClassBean", value = "创建更换老师信息题", required = true,
                    dataType = "CancelAuditionClassBean", paramType = "body")
    })
    @RequiresRoles(value = {"hireTeacher"}, logical = Logical.OR)
    @PostMapping("changeTeacher")
    public ResponseEntity changeTeacher (@RequestBody CancelAuditionClassBean cancelAuditionClassBean) throws BaseControllerException, BaseServiceException {
        createFlowService.changeTeacher(cancelAuditionClassBean);
        return ResultUtil.success();
    }

    @ApiOperation(value = "取消试听课操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cancelAuditionClassBean", value = "创建预约试听课信息体", required = true,
                    dataType = "CancelAuditionClassBean", paramType = "body")
    })
    //@RequiresRoles(value = {"courseConsultant,headMaster"}, logical = Logical.OR)
    @PostMapping("cancelAuditionClass")
    public ResponseEntity createAuditionFeedbackForm (@RequestBody CancelAuditionClassBean cancelAuditionClassBean) throws BaseControllerException, BaseServiceException {
        createFlowService.cancelAudition(cancelAuditionClassBean);
        return ResultUtil.success();
    }

    @ApiOperation(value = "下载简历")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nodeId", value = "nodeId", required = true,
                    dataType = "Integer", paramType = "query")
    })
    //@RequiresRoles(value = {"courseConsultant,headMaster"}, logical = Logical.OR)
    @GetMapping ("/downloadTeacherConsume/{nodeId:\\d+}")
    public ResponseEntity downloadTeacherConsume (@PathVariable Integer nodeId) throws BaseControllerException, BaseServiceException {
        MatchTeacherForm matchTeacherForm =new MatchTeacherForm();
        String downloadUrl = createFlowService.getTeacherConsumeUrl(nodeId);
        matchTeacherForm.setPdfAddress(downloadUrl);
        return ResultUtil.success(matchTeacherForm);
    }
}
