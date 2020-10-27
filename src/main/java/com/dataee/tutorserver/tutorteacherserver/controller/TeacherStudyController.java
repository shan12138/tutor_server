package com.dataee.tutorserver.tutorteacherserver.controller;

/**
 * 教员和家长学习模块
 *
 * @author JinYue
 * @CreateDate 2019/5/23 2:36
 */

import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.commons.commonservice.IOSSService;
import com.dataee.tutorserver.commons.commonservice.impl.OSSServiceImpl;
import com.dataee.tutorserver.entity.StudyResource;
import com.dataee.tutorserver.tutorteacherserver.bean.StudyCenterResponseBean;
import com.dataee.tutorserver.tutorteacherserver.service.ITeacherStudyService;
import com.dataee.tutorserver.tutorteacherserver.service.impl.TeacherStudyServiceImpl;
import com.dataee.tutorserver.utils.ResultUtil;
import com.dataee.tutorserver.utils.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Api(value = "keyword为教学中心，随堂测试， 试题中心的汉字值", tags = "教员和家长学习模块")
@RequiresRoles(value = {"teacher", "parent"}, logical = Logical.OR)
@RequiresPermissions("auth:ed")
@RestController
@RequestMapping("/teacher/studyResource")
public class TeacherStudyController {
    private final Logger logger = LoggerFactory.getLogger(TeacherStudyController.class);

    @Autowired
    private ITeacherStudyService teacherStudyService;

    public void setStudyService(TeacherStudyServiceImpl teacherStudyService) {
        this.teacherStudyService = teacherStudyService;
    }

    @Autowired
    private IOSSService ossService;

    public void setOssService(OSSServiceImpl ossService) {
        this.ossService = ossService;
    }


    @ApiOperation("获取资源列表")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = StudyResource.class, responseContainer = "List")
    })
    @GetMapping("/list")
    public ResponseEntity getTeachingPlanList(@RequestParam String keyword, @RequestParam Integer limit, @RequestParam Integer pageNo) throws BaseControllerException, BaseServiceException {
        //做keyword校验
        Integer personId = SecurityUtil.getPersonId();
        Page page = new Page(limit, pageNo);
        NewPageInfo<StudyCenterResponseBean> studyResourcesPageInfo = teacherStudyService.getStudyResourceByPersonId(keyword, personId, page);
        return ResultUtil.success(studyResourcesPageInfo);
    }

    @ApiOperation("获取指定资源内容,返回值中只有address")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = StudyResource.class)
    })
    @GetMapping("/{studyResourceId:\\d+}")
    public ResponseEntity getTeacherPlanList(@PathVariable Integer studyResourceId, @RequestParam Integer limit, @RequestParam Integer pageNo) throws BaseServiceException {
        Page page = new Page(limit, pageNo);
        NewPageInfo<String> teachingPlanAddressPageInfo = teacherStudyService.getStudyResourceById(studyResourceId, page);
        return ResultUtil.success(teachingPlanAddressPageInfo);
    }

}
