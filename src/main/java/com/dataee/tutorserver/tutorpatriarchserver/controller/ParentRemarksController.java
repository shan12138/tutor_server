package com.dataee.tutorserver.tutorpatriarchserver.controller;

import com.dataee.tutorserver.entity.RemarkQuestion;
import com.dataee.tutorserver.tutorpatriarchserver.bean.RemarksListBean;
import com.dataee.tutorserver.tutorpatriarchserver.service.IParentRemarksService;
import com.dataee.tutorserver.tutorpatriarchserver.service.impl.ParentRemarksServiceImpl;
import com.dataee.tutorserver.utils.ResultUtil;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 家长评价
 *
 * @author JinYue
 * @CreateDate 2019/6/3 20:10
 */
@RestController
@RequestMapping("/parent")
public class ParentRemarksController {
    private final Logger logger = LoggerFactory.getLogger(ParentRemarksController.class);
    @Autowired
    private IParentRemarksService parentRemarksService;

    public void setParentRemarksService(ParentRemarksServiceImpl parentRemarksService) {
        this.parentRemarksService = parentRemarksService;
    }

    @ApiOperation(value = "家长端获取评价的问题")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = RemarkQuestion.class, responseContainer = "List")
    })
    @GetMapping("/remarks")
    public ResponseEntity getRemarkQuestions() {
        return ResultUtil.success(parentRemarksService.getRemarkQuestions());
    }

    @ApiOperation(value = "家长端填写评价")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "remarksList", value = "评价信息体", required = true,
                    dataType = "RemarksListBean", paramType = "body")
    })
    @PostMapping("/remarks")
    public ResponseEntity createParentRemarks(@RequestBody @Valid RemarksListBean remarksList) {
        parentRemarksService.createParentRemarks(remarksList);
        return ResultUtil.success();
    }
}
