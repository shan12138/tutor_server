package com.dataee.tutorserver.tutorteacherserver.controller;

import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.commons.commonservice.IOSSService;
import com.dataee.tutorserver.commons.commonservice.impl.OSSServiceImpl;
import com.dataee.tutorserver.commons.exceptions.IllegalParameterException;
import com.dataee.tutorserver.entity.Question;
import com.dataee.tutorserver.entity.Teacher;
import com.dataee.tutorserver.entity.TeacherLabel;
import com.dataee.tutorserver.tutorteacherserver.bean.*;
import com.dataee.tutorserver.tutorteacherserver.service.ITeacherCenterService;
import com.dataee.tutorserver.tutorteacherserver.service.ITeacherSignUpService;
import com.dataee.tutorserver.tutorteacherserver.service.impl.TeacherCenterServiceImpl;
import com.dataee.tutorserver.utils.DoubleToTwo;
import com.dataee.tutorserver.utils.ResultUtil;
import com.dataee.tutorserver.utils.SecurityUtil;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.net.URL;

@Api(value = "教员个人中心模块")
@RequiresRoles("teacher")
@RestController
@RequestMapping("/")
public class TeacherCenterController {
    @Autowired
    private ITeacherCenterService teacherCenterService;

    @Autowired
    private ITeacherSignUpService teacherSignUpService;

    @Autowired
    private IOSSService ossService;

    public void setOssService(OSSServiceImpl ossService) {
        this.ossService = ossService;
    }

    @RequiresPermissions("register:ing")
    @ApiOperation(value = "教员端填写注册报名的第一步信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teacherBasicInfoRequestBean", value = "报名信息体", required = true,
                    dataType = "TeacherBasicInfoRequestBean", paramType = "body")
    })
    @PutMapping("/teacher/first")
    public ResponseEntity writeFirstInformation(@RequestBody @Valid TeacherBasicInfoRequestBean teacherBasicInfoRequestBean)
            throws BaseControllerException {
        Integer teacherId = SecurityUtil.getPersonId();
        teacherBasicInfoRequestBean.setTeacherId(teacherId);
        teacherCenterService.writeFirstInformation(teacherBasicInfoRequestBean);
        return ResultUtil.success();
    }

    @RequiresPermissions("register:ing")
    @ApiOperation(value = "教员端填写注册报名的第二步信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teacherBasicInfoRequestBean", value = "报名信息体", required = true,
                    dataType = "TeacherBasicInfoRequestBean", paramType = "body")
    })
    @PutMapping("/teacher/second")
    public ResponseEntity writeSecondInformation(@RequestBody @Valid TeacherBasicInfoRequestBean teacherBasicInfoRequestBean)
            throws BaseControllerException {
        Integer teacherId = SecurityUtil.getPersonId();
        teacherBasicInfoRequestBean.setTeacherId(teacherId);
        teacherCenterService.writeSecondInformation(teacherBasicInfoRequestBean);
        return ResultUtil.success();
    }

    @RequiresPermissions("register:ing")
    @ApiOperation(value = "教员端查看注册报名的第一步信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Teacher.class)
    })
    @GetMapping("/teacher/first")
    public ResponseEntity getFirstInformation()
            throws BaseControllerException {
        Integer teacherId = SecurityUtil.getPersonId();
        return ResultUtil.success(teacherCenterService.getFirstInformation(teacherId));
    }

    @RequiresPermissions("register:ing")
    @ApiOperation(value = "教员端查看注册报名的第二步信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Teacher.class)
    })
    @GetMapping("/teacher/second")
    public ResponseEntity getSecondInformation() throws BaseControllerException {
        Integer teacherId = SecurityUtil.getPersonId();
        return ResultUtil.success(teacherCenterService.getSecondInformation(teacherId));
    }

    @RequiresPermissions("register:ing")
    @ApiOperation(value = "教员端查看注册报名的第三步信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Teacher.class)
    })
    @GetMapping("/teacher/third")
    public ResponseEntity getThirdInformation() throws BaseControllerException {
        Integer teacherId = SecurityUtil.getPersonId();
        return ResultUtil.success(teacherCenterService.getThirdInformation(teacherId));
    }

    @ApiOperation(value = "教员选择课程进行自测")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "grade", value = "年级", required = true,
                    dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "cType", value = "科目", required = true,
                    dataType = "String", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Question.class, responseContainer = "List")
    })
    @GetMapping("/paper")
    public ResponseEntity getPaper(@RequestParam @NotBlank(message = "年级不能为空") String grade,
                                   @RequestParam @NotBlank(message = "科目不能为空") String cType) {
        return ResultUtil.success(teacherCenterService.getPaper(grade, cType));
    }

    /**
     * 教员修改个人信息
     *
     * @param teacherDetailInfoRequestBean
     * @return
     * @throws BaseControllerException
     * @throws BaseServiceException
     */
    @ApiOperation(value = "教员修改个人信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teacherDetailInfoRequestBean", value = "修改信息体", required = true,
                    dataType = "TeacherDetailInfoRequestBean", paramType = "body")
    })
    @PutMapping("/teacher")
    public ResponseEntity changeTeacherInfo(@RequestBody @Valid TeacherDetailInfoRequestBean teacherDetailInfoRequestBean) throws BaseControllerException, BaseServiceException {
        Integer teacherId = SecurityUtil.getPersonId();
        teacherDetailInfoRequestBean.setTeacherId(teacherId);
        teacherCenterService.changeTeaInfo(teacherDetailInfoRequestBean);
        return ResultUtil.success();
    }

    /**
     * 教员查看个人信息
     *
     * @return
     * @throws BaseControllerException
     */
    @ApiOperation(value = "教员查看个人信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Teacher.class)
    })
    @GetMapping("/teacher")
    public ResponseEntity getTeacherInfo() throws BaseControllerException {
        return ResultUtil.success(teacherCenterService.getTeacherInfo(SecurityUtil.getPersonId()));
    }

    @ApiOperation(value = "教员查看头像")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = URL.class)
    })
    @GetMapping("/teacher/headPicture")
    public ResponseEntity getTeacherHeadPicture() throws BaseControllerException, BaseServiceException {
        String headPicture = teacherCenterService.getHeadPicture(SecurityUtil.getPersonId());
        URL url = ossService.getURL(headPicture);
        return ResultUtil.success(url);
    }

    /**
     * sql语句已经存在limit
     *
     * @param limit
     * @param pageNo
     * @return
     */
    @RequiresPermissions("auth:ed")
    @ApiOperation(value = "教员查看优秀教师")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit", value = "需要获取的信息的条数", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "需要获取的页（从1开始）", required = true, dataType = "Integer", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Teacher.class, responseContainer = "List")
    })
    @GetMapping("/excellentTeacher")
    public ResponseEntity getExcellentTeachers(@RequestParam Integer limit, @RequestParam Integer pageNo) {
        Page page = new Page(limit, pageNo);
        NewPageInfo<Teacher> teacher = teacherCenterService.queryTeacher(page);
        return ResultUtil.success(teacher);
    }

    @ApiOperation("提交测试，路径获取教师id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "score", value = "测试成绩", required = true,
                    dataType = "ScoreBean", paramType = "body")
    })
    @PutMapping("/teacher/score")
    public ResponseEntity changeTeacherState(@RequestBody ScoreBean score) throws BaseControllerException, BaseServiceException {
        Integer teacherId = SecurityUtil.getPersonId();
        score.setTeacherId(teacherId);
        teacherCenterService.saveCourseScore(score);
        if (score.getScore() >= 80) {
            //如果测试通过，自动添加一条授课范围(首先判断是否存在该授课范围)
            Integer id = teacherCenterService.getTeachingAreaId(score);
            if (id == null) {
                teacherCenterService.saveTeachingArea(score);
            }
        }
        return ResultUtil.success();
    }

    @ApiOperation("获取历史成绩")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit", value = "需要获取的信息的条数", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "需要获取的页（从1开始）", required = true, dataType = "Integer", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = ScoreBean.class, responseContainer = "List")
    })
    @GetMapping("/teacher/score/history")
    public ResponseEntity getScoreHistory(@RequestParam Integer limit, @RequestParam Integer pageNo) throws BaseControllerException {
        Integer teacherId = SecurityUtil.getPersonId();
        Page page = new Page(limit, pageNo);
        return ResultUtil.success(teacherCenterService.getScoreHistory(teacherId, page));
    }

    @ApiOperation("获取标签信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = TeacherLabel.class)
    })
    @GetMapping("/teacherLabel")
    public ResponseEntity getTeacherLabel() throws BaseControllerException {
        Integer teacherId = SecurityUtil.getPersonId();
        TeacherLabel teacherLabel = teacherCenterService.getTeacherLabel(teacherId);
        if (teacherLabel == null) {
            return ResultUtil.success();
        }
        return ResultUtil.success(DoubleToTwo.convertDoubleToTwo(teacherLabel));
    }

    @ApiOperation("获取银行卡信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = BankAccountResponseBean.class)
    })
    @GetMapping("/teacher/bankaccount")
    public ResponseEntity getBankAccountInfo() throws BaseControllerException, BaseServiceException {
        return ResultUtil.success(teacherCenterService.getBankAccountInfo(SecurityUtil.getPersonId()));
    }

    @ApiOperation("获取学生账号信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = StudentCardResponseBean.class)
    })
    @GetMapping("/teacher/studentcard")
    public ResponseEntity getStudentCard() throws BaseControllerException, BaseServiceException {
        return ResultUtil.success(teacherCenterService.getStudentCardPicture(SecurityUtil.getPersonId()));
    }

    /**
     * 修改银行可和学生证信息
     *
     * @param teacherCardInfo
     * @return
     * @throws BaseControllerException
     * @throws BaseServiceException
     */
    @ApiOperation("修改银行可和学生证信息")
    @PutMapping("/teacher/cardInfo")
    public ResponseEntity updateCardInfo(@RequestBody @Valid TeacherOtherInfoStudentCardRequestBean teacherCardInfo) throws BaseControllerException, BaseServiceException {
        teacherSignUpService.addStudentCardInfo(teacherCardInfo, SecurityUtil.getPersonId());
        return ResultUtil.success();
    }
}
