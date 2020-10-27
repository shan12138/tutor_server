package com.dataee.tutorserver.tutorteacherserver.controller;

import com.dataee.tutorserver.entity.RemarkQuestion;
import com.dataee.tutorserver.tutorpatriarchserver.bean.RemarksListBean;
import com.dataee.tutorserver.tutorteacherserver.service.ITeacherRemarksService;
import com.dataee.tutorserver.utils.ResultUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/6/8 23:10
 */
@RestController
@RequestMapping("/teacher")
public class TeacherRemarksController {
    @Autowired
    private ITeacherRemarksService teacherRemarksService;

    @ApiOperation(value = "教员端获取评价的问题")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = RemarkQuestion.class, responseContainer = "List")
    })
    @GetMapping("/remarks")
    public ResponseEntity getRemarkQuestions() {
        return ResultUtil.success(teacherRemarksService.getRemarkQuestions());
    }

    @ApiOperation(value = "教员端填写评价")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "remarksList", value = "评价信息体", required = true,
                    dataType = "RemarksListBean", paramType = "body")
    })
    @PostMapping("/remarks")
    public ResponseEntity createTeacherRemarks(@RequestBody @Valid RemarksListBean remarksList) {
        teacherRemarksService.createTeacherRemarks(remarksList);
        return ResultUtil.success();
    }
}
