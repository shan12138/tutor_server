package com.dataee.tutorserver.tutorteacherserver.controller;

import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.entity.Student;
import com.dataee.tutorserver.tutorteacherserver.bean.MyStudentInfoResponseBean;
import com.dataee.tutorserver.tutorteacherserver.service.IMyStudentsService;
import com.dataee.tutorserver.tutorteacherserver.service.impl.MyStudentsServiceImpl;
import com.dataee.tutorserver.utils.ResultUtil;
import com.dataee.tutorserver.utils.SecurityUtil;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 查看我的学生的信息
 *
 * @author JinYue
 * @CreateDate 2019/5/16 11:46
 */
@RequiresRoles("teacher")
@RequiresPermissions("auth:ed")
@RestController
public class MyStudentsController {
    private final Logger logger = LoggerFactory.getLogger(MyStudentsController.class);
    @Autowired
    private IMyStudentsService myStudentsService;

    public void setMyStudentsService(MyStudentsServiceImpl myStudentsService) {
        this.myStudentsService = myStudentsService;
    }

    /**
     * 获取教师的学生列表
     *
     * @return
     * @throws BaseControllerException
     */
    @ApiOperation("获取教师的学生列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit", value = "需要获取的信息的条数", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "需要获取的页（从1开始）", required = true, dataType = "Integer", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok")
    })
    @RequestMapping(value = "/teacher/studentList", method = RequestMethod.GET)
    public ResponseEntity getStudentList(@RequestParam Integer limit, @RequestParam Integer pageNo) throws BaseControllerException {
        Integer teacherId = SecurityUtil.getPersonId();
        Page page = new Page(limit, pageNo);
        NewPageInfo<Student> students = myStudentsService.getMyStudentsList(teacherId, page);
        return ResultUtil.success(students);
    }

    /**
     * 获取去教师的指定学生的详细信息
     *
     * @param studentId
     * @return
     * @throws BaseControllerException
     */
    @RequestMapping(value = "/teacher/student/{studentId:\\d+}", method = RequestMethod.GET)
    public ResponseEntity getStudentInfo(@PathVariable Integer studentId) throws BaseControllerException {
        Integer teacherId = SecurityUtil.getPersonId();
        MyStudentInfoResponseBean student = myStudentsService.getMyStudentInfoById(teacherId, studentId);
        return ResultUtil.success(student);
    }
}
