package com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.controller;

import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.entity.Administrator;
import com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.service.ICreateFlowService;
import com.dataee.tutorserver.utils.ResultUtil;
import com.dataee.tutorserver.utils.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

@Api(value = "公共流程")
@RestController
@RequestMapping("/")
public class FlowController {

    @Autowired
    private ICreateFlowService createFlowService;

    @ApiOperation(value = "查看所有流程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "页码", required = true,
                    dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "个数", required = true,
                    dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "state", value = "状态(已办，待办)", required = true,
            dataType = "String", paramType = "query"),

    })
    @GetMapping("Flows")
    public ResponseEntity getNotDoneFlows(@RequestParam String state,@RequestParam Integer pageNo, @RequestParam Integer limit) throws BaseControllerException {
        Page page = new Page(limit, pageNo);
        Integer personId = SecurityUtil.getPersonId();
        return ResultUtil.success(createFlowService.getAllFlow(state,personId,page));
    }


    @ApiOperation(value = "查看所有流程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "流程id", required = true,
                    dataType = "int", paramType = "query"),
    })
    @GetMapping("workFlow/{id}")
    public ResponseEntity getWorkFlow(@PathVariable("id")Integer id) throws BaseControllerException {
        return ResultUtil.success(createFlowService.getWorkFlowByWorkFlowId(id));
    }

    @ApiOperation(value = "提交到对应的人")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sequenceNum", value = "node的步数", required = true,
                    dataType = "Integer", paramType = "query"),
    })
    @GetMapping("submitToSb")
    public ResponseEntity submitToSb(@RequestParam Integer sequenceNum) throws BaseControllerException {
        if(sequenceNum == 0){
            List<List<Administrator>>  adminList = new ArrayList<>();
            List<Administrator> courseConsultant = createFlowService.getAdminByRole(107);
            List<Administrator> headMaster = createFlowService.getAdminByRole(108);
            List<Administrator> hireTeacher = createFlowService.getAdminByRole(109);
            List<Administrator> trainDepartment = createFlowService.getAdminByRole(110);
            List<Administrator> marketConsultant = createFlowService.getAdminByRole(111);
            adminList.add(courseConsultant);
            adminList.add(headMaster);
            adminList.add(hireTeacher);
            adminList.add(trainDepartment);
            adminList.add(marketConsultant);
            return ResultUtil.success(adminList);
        }

        if(sequenceNum == 1){
            return ResultUtil.success(createFlowService.getAdminByRole(108));
        }else if(sequenceNum == 2){
            return ResultUtil.success(createFlowService.getAdminByRole(109));
        }else if(sequenceNum == 3){
            return ResultUtil.success(createFlowService.getAdminByRole(110));
        }else if(sequenceNum == 4){
            return ResultUtil.success(createFlowService.getAdminByRole(111));
        }
        return ResultUtil.success();
    }


    @ApiOperation(value = "提交到培训部")
    @GetMapping("submitToTrainDepartment")
    public ResponseEntity submitToSb() throws BaseControllerException {
        return ResultUtil.success(createFlowService.getAdminByRole(110));
    }

}
