package com.dataee.tutorserver.userserver.controller;

import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.commons.bean.UserPrincipals;
import com.dataee.tutorserver.commons.commonservice.IOSSService;
import com.dataee.tutorserver.commons.commonservice.impl.OSSServiceImpl;
import com.dataee.tutorserver.commons.exceptions.IllegalParameterException;
import com.dataee.tutorserver.commons.exceptions.SQLOperationException;
import com.dataee.tutorserver.entity.ErrorQuestion;
import com.dataee.tutorserver.entity.Person;
import com.dataee.tutorserver.userserver.bean.CourseResponseBean;
import com.dataee.tutorserver.userserver.bean.ErrorQuestionRequestBean;
import com.dataee.tutorserver.userserver.bean.ErrorQuestionResponseBean;
import com.dataee.tutorserver.userserver.bean.LessonNumberResponseBean;
import com.dataee.tutorserver.userserver.service.IErrorQuestionService;
import com.dataee.tutorserver.userserver.service.impl.ErrorQuestionServiceImpl;
import com.dataee.tutorserver.utils.ResultUtil;
import com.dataee.tutorserver.utils.SecurityUtil;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 用户对错题的上传和查看功能
 *
 * @author JinYue
 * @CreateDate 2019/5/7 0:41
 */
@RequiresPermissions("auth:ed")
@RestController
@RequestMapping("/")
public class ErrorQuestionController {
    private final Logger logger = LoggerFactory.getLogger(ErrorQuestionController.class);
    @Autowired
    private IErrorQuestionService errorQuestionService;

    public void setErrorQuestionService(ErrorQuestionServiceImpl errorQuestionService) {
        this.errorQuestionService = errorQuestionService;
    }

    @Autowired
    private IOSSService ossService;

    public void setIossService(OSSServiceImpl ossService) {
        this.ossService = ossService;
    }

    /**
     * 上传错题
     *
     * @param errorQuestion
     * @return
     */
    @ApiOperation("上传错题")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "role", value = "用户的身份", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "errorQuestion", value = "错题信息", required = true, dataType = "ErrorQuestionRequestBean", paramType = "body")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok")
    })
    @RequiresRoles(value = {"teacher", "parent"}, logical = Logical.OR)
    @PostMapping("/{role:teacher|parent}/upload/errorQuestion")
    public ResponseEntity uploadErrorQuestions(@PathVariable String role, @RequestBody @Valid ErrorQuestionRequestBean errorQuestion)
            throws BaseControllerException, SQLOperationException {
        Integer personId = SecurityUtil.getPersonId();
        errorQuestionService.saveErrorQuestions(errorQuestion, personId, role);
        return ResultUtil.success();
    }

    /**
     * 获取错题列表
     *
     * @param role
     * @return
     */
    @ApiOperation("获取错题列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "role", value = "用户的身份", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "limit", value = "获取信息的数量", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "要获取的页（从1开始）", required = true, dataType = "Integer", paramType = "query"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = ErrorQuestionResponseBean.class, responseContainer = "List")
    })
    @RequiresRoles(value = {"teacher", "parent"}, logical = Logical.OR)
    @GetMapping("/{role:teacher|parent}/getErrorQuestions")
    public ResponseEntity getErrorQuestionList(@PathVariable String role, @RequestParam Integer limit, @RequestParam Integer pageNo) throws BaseControllerException, BaseServiceException {
        Page page = new Page(limit, pageNo);
        Integer personId = SecurityUtil.getPersonId();
        NewPageInfo<ErrorQuestion> resourceAddress = errorQuestionService.getErrorQuestionsByPersonId(personId, role, page);
        return ResultUtil.success(resourceAddress);
    }

    /**
     * 获取指定错题集
     *
     * @param errorQuestionId
     * @return
     */
    @ApiOperation("获取指定错题集")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "errorQuestionId", value = "错题ID", required = true, dataType = "Integer", paramType = "path"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = String.class, responseContainer = "List")
    })
    @RequiresRoles(value = {"teacher", "parent", "superAdmin", "courseAdmin"}, logical = Logical.OR)
    @RequiresPermissions("auth:ed")
    @GetMapping("/errorQuestion/{errorQuestionId}")
    public ResponseEntity getErrorQuestion(@PathVariable("errorQuestionId") Integer errorQuestionId)
            throws BaseServiceException, IllegalParameterException {
        ErrorQuestion errorQuestion = errorQuestionService.getErrorQuestionByQuestionId(errorQuestionId);
        if (errorQuestion != null) {
            if (errorQuestion.getQuestionPicture() != null && errorQuestion.getQuestionPicture().size() != 0) {
                List<String> urlList = ossService.getURLList(errorQuestion.getQuestionPicture());
                errorQuestion.setQuestionPicture(urlList);
            }
        } else {
            throw new IllegalParameterException();
        }
        return ResultUtil.success(errorQuestion);
    }


    /**
     * 获取该用户的课程列表
     *
     * @return
     * @throws BaseControllerException
     */
    @ApiOperation("获取该用户的课程列表")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = CourseResponseBean.class, responseContainer = "List")
    })
    @RequiresRoles(value = {"teacher", "parent"}, logical = Logical.OR)
    @GetMapping("/course/name/list")
    public ResponseEntity getCourseName() throws BaseControllerException {
        UserPrincipals principals = SecurityUtil.getPrincipal();
        List<CourseResponseBean> courseList = errorQuestionService.getCourseName(principals.getPersonId(), principals.getCurrRole());
        return ResultUtil.success(courseList);
    }

    /**
     * 获取该用户的指定课程的已上过的课次列表
     *
     * @param courseId
     * @return
     * @throws IllegalParameterException
     */
    @ApiOperation("获取该用户的指定课程的已上过的课次列表")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = LessonNumberResponseBean.class, responseContainer = "List")
    })
    @RequiresRoles(value = {"teacher", "parent"}, logical = Logical.OR)
    @GetMapping("/lesson/number/list")
    public ResponseEntity getLessonNumber(@RequestParam Integer courseId) throws IllegalParameterException {
        if (courseId == null) {
            throw new IllegalParameterException("请先选择课程");
        }
        List<LessonNumberResponseBean> lessonNumberList = errorQuestionService.getLessonNumber(courseId);
        return ResultUtil.success(lessonNumberList);
    }
}
