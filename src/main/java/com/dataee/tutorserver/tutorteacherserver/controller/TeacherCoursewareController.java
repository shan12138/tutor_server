package com.dataee.tutorserver.tutorteacherserver.controller;

import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.commons.exceptions.IllegalParameterException;
import com.dataee.tutorserver.tutorteacherserver.bean.TeacherCoursewareResponseBean;
import com.dataee.tutorserver.tutorteacherserver.service.ITeacherCoursewareService;
import com.dataee.tutorserver.utils.ResultUtil;
import com.dataee.tutorserver.utils.SecurityUtil;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author JinYue
 * @CreateDate 2019/6/29 14:51
 */
@RestController
@RequestMapping("/teacher")
public class TeacherCoursewareController {

    private Logger logger = LoggerFactory.getLogger(TeacherCoursewareController.class);
    @Autowired
    private ITeacherCoursewareService teacherCoursewareService;


    @ApiOperation("点击查看当堂课件(此接口复用)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lessonId", value = "课堂id", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "请求的页号", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "请求的数量", required = true, dataType = "Integer", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = TeacherCoursewareResponseBean.class)
    })
    @GetMapping("/courseware")
    public ResponseEntity scanCourseware(@RequestParam Integer lessonId, @RequestParam Integer pageNo, @RequestParam Integer limit)
            throws BaseControllerException, BaseServiceException {
        if (lessonId == null) {
            throw new IllegalParameterException("没有选择课程");
        }
        Page page = new Page(limit, pageNo);
        TeacherCoursewareResponseBean teacherCourseware = teacherCoursewareService.getCoursewareInfo(lessonId, page);
        return ResultUtil.success(teacherCourseware);
    }

    @ApiOperation("标记已读")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "coursewareId", value = "课件的ID", required = true, dataType = "Integer", paramType = "path")
    })
    @PutMapping("/sign/{coursewareId:\\d+}")
    public ResponseEntity CoursewareRead(@PathVariable Integer coursewareId) {
        teacherCoursewareService.read(coursewareId);
        return ResultUtil.success();
    }

    /**
     * 下载指定的课件
     *
     * @param id
     * @return
     */
    @GetMapping("/download/{id:\\d+}")
    public ResponseEntity downloadResourcePdf(@PathVariable Integer id) throws BaseServiceException {
        String downloadUrl = teacherCoursewareService.downloadCourseware(id);
        return ResultUtil.success(downloadUrl);
    }




}
