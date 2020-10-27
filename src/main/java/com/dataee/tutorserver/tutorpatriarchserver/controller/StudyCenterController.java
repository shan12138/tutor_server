package com.dataee.tutorserver.tutorpatriarchserver.controller;

import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.tutoradminserver.filemanage.bean.ResourceListResponseBean;
import com.dataee.tutorserver.tutorpatriarchserver.service.IStudyCenterService;
import com.dataee.tutorserver.tutorteacherserver.bean.StudyCenterResponseBean;
import com.dataee.tutorserver.utils.ResultUtil;
import com.dataee.tutorserver.utils.SecurityUtil;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 学习中心
 *
 * @author JinYue
 * @CreateDate 2019/6/10 23:22
 */
@RestController
@RequestMapping("/parent/studyCenter")
@RequiresRoles("parent")
public class StudyCenterController {
    private Logger logger = LoggerFactory.getLogger(StudyCenterController.class);

    @Autowired
    private IStudyCenterService studyCenterService;

    @ApiOperation(value = "获取教学资源列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit", value = "需要获取的信息的条数", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "需要获取的页（从1开始）", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "keyword", value = "列表名称", required = true, dataType = "String", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = ResourceListResponseBean.class, responseContainer = "List")
    })
    @GetMapping("/")
    public ResponseEntity getTeachingResourceList(@RequestParam("keyword") String keyword, @RequestParam Integer limit, @RequestParam Integer pageNo) throws BaseControllerException {
        Integer personId = SecurityUtil.getPersonId();
        Page page = new Page(limit, pageNo);
        NewPageInfo<StudyCenterResponseBean> teachingResourceList = studyCenterService.getTeachingResourceByKeyword(personId, keyword, page);
        return ResultUtil.success(teachingResourceList);
    }

    @ApiOperation(value = "获取指定的教学资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit", value = "需要获取的信息的条数", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "需要获取的页（从1开始）", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "资源id", required = true, dataType = "Integer", paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", responseContainer = "List")
    })
    @GetMapping("/{id:\\d+}")
    public ResponseEntity getTeachingResourceImageById(@PathVariable Integer id, @RequestParam Integer limit, @RequestParam Integer pageNo) throws BaseServiceException {
        Page page = new Page(limit, pageNo);
        NewPageInfo<String> imagesList = studyCenterService.getTeachingResourceImageById(id, page);
        return ResultUtil.success(imagesList);
    }

    /**
     * 下载指定的课件
     *
     * @param id
     * @return
     */
    @GetMapping("/download/{id:\\d+}")
    public ResponseEntity downloadResourcePdf(@PathVariable Integer id) throws BaseServiceException {
        String downloadUrl = studyCenterService.downloadCourseware(id);
        return ResultUtil.success(downloadUrl);
    }
}
