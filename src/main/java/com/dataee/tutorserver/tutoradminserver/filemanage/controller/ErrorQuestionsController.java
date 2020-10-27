package com.dataee.tutorserver.tutoradminserver.filemanage.controller;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.tutoradminserver.filemanage.bean.ErrorQuestionResponseBean;
import com.dataee.tutorserver.tutoradminserver.filemanage.service.IErrorQuestionsService;
import com.dataee.tutorserver.utils.ResultUtil;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * 查看所有错题列表
 *
 * @author JinYue
 * @CreateDate 2019/5/14 21:48
 */
@RestController
@RequestMapping("/admin/errorQuestions")
public class ErrorQuestionsController {
    private final Logger logger = LoggerFactory.getLogger(ErrorQuestionsController.class);
    @Autowired
    private IErrorQuestionsService errorQuestionsService;


    /**
     * 管理员获取当前以有效的所有的错题
     *
     * @return
     */
    @ApiOperation("管理员获取当前以有效的所有的错题")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "请求的页号", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "请求的条数", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "studentName", value = "学生名", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "queryCondition", value = "查询条件", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "lessonNumber", value = "课次", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "essentialContent", value = "关键内容", required = false, dataType = "String", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = ErrorQuestionResponseBean.class, responseContainer = "List")
    })
    @RequiresPermissions("wrongBook:list")
    @GetMapping("/list")
    public ResponseEntity getAllErrorQuestions(@RequestParam Integer pageNo, @RequestParam Integer limit,
                                               @RequestParam(required = false) String studentName,@RequestParam(required = false) String queryCondition,
                                               @RequestParam(required = false) Integer  lessonNumber,@RequestParam(required = false) String  essentialContent
                                                                                                                      ) {
        Page page = new Page(limit, pageNo);
        NewPageInfo errorQuestions = errorQuestionsService.getAllErrorQuestions(page,studentName,queryCondition,lessonNumber,essentialContent);
        return ResultUtil.success(errorQuestions);
    }

    /**
     * 查看指定的错题
     *
     * @param id
     * @return
     * @throws BaseServiceException
     */
    @ApiOperation("查看指定的错题")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "错题编号", required = true, dataType = "Integer", paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = ErrorQuestionResponseBean.class)
    })
    @RequiresPermissions("wrongBook:read")
    @GetMapping("/{id:\\d+}")
    public ResponseEntity getErrorQuestion(@PathVariable Integer id) throws BaseServiceException {
        ErrorQuestionResponseBean errorQuestion = errorQuestionsService.getErrorQuestion(id);
        return ResultUtil.success(errorQuestion);
    }

    /**
     * 修改指定错题的打印状态
     *
     * @param id
     * @param isPrint
     * @return
     */
    @ApiOperation("修改指定错题的打印状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "错题编号", required = true, dataType = "Integer", paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok")
    })
    @PutMapping("/{id:\\d+}/print")
    public ResponseEntity printStatus(@PathVariable Integer id, @RequestParam Boolean isPrint) {
        errorQuestionsService.printed(id, isPrint);
        return ResultUtil.success();
    }

    /**
     * 修改指定错题的排课状态
     *
     * @param id
     * @param isCourse
     * @return
     */
    @ApiOperation("修改指定错题的排课状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "错题编号", required = true, dataType = "Integer", paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok")
    })
    @PutMapping("/{id:\\d+}/course")
    public ResponseEntity courseStatus(@PathVariable Integer id, @RequestParam Boolean isCourse) {
        errorQuestionsService.coursed(id, isCourse);
        return ResultUtil.success();
    }


    /**
     * 下载指定的错题
     *
     * @param id
     * @return
     */
    @GetMapping("/download/{id:\\d+}")
    public ResponseEntity downloadErrorQuestion(@PathVariable Integer id) throws BaseServiceException {
        String downloadUrl = errorQuestionsService.downloadErrorQuestionPackage(id);
        return ResultUtil.success(downloadUrl);
    }
}
