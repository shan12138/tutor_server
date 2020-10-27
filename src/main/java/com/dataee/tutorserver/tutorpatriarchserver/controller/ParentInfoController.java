package com.dataee.tutorserver.tutorpatriarchserver.controller;

import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.commonservice.IOSSService;
import com.dataee.tutorserver.commons.exceptions.SQLOperationException;
import com.dataee.tutorserver.tutorpatriarchserver.service.IParentInfoService;
import com.dataee.tutorserver.utils.ResultUtil;
import com.dataee.tutorserver.utils.SecurityUtil;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URL;

/**
 * 教师个人信息
 *
 * @author JinYue
 * @CreateDate 2019/6/10 0:25
 */
@RestController
@RequestMapping("/parent")
public class ParentInfoController {
    private Logger logger = LoggerFactory.getLogger(ParentInfoController.class);

    @Autowired
    private IParentInfoService parentInfoService;
    @Autowired
    private IOSSService ossService;

    @ApiOperation("保存头像")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "headPortrait", value = "头像地址", required = true, dataType = "String", paramType = "query"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok")
    })
    @PutMapping("/headportrait")
    public ResponseEntity saveHeadportrait(@RequestParam(value = "headPortrait") String headPortrait)
            throws BaseControllerException, SQLOperationException {
        if (headPortrait == null) {
            return ResultUtil.error(HttpStatus.BAD_REQUEST, "头像路径不能为空");
        }
        Integer parentId = SecurityUtil.getPersonId();
        parentInfoService.saveHeadportrait(parentId, headPortrait);
        return ResultUtil.success();
    }

    @ApiOperation("家长获取头像")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = URL.class)
    })
    @GetMapping("/headportrait")
    public ResponseEntity getHeadportrait() throws BaseControllerException, BaseServiceException {
        Integer parentId = SecurityUtil.getPersonId();
        String address = parentInfoService.getHeadportrait(parentId);
        URL headportrait = ossService.getURL(address);
        return ResultUtil.success(headportrait);
    }
}
