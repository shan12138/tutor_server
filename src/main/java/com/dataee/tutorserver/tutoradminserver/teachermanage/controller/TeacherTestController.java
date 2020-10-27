package com.dataee.tutorserver.tutoradminserver.teachermanage.controller;

import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.exceptions.IllegalParameterException;
import com.dataee.tutorserver.entity.StudyResource;
import com.dataee.tutorserver.tutoradminserver.teachermanage.bean.TeacherTestExcelBean;
import com.dataee.tutorserver.tutoradminserver.teachermanage.service.ITeacherTestService;
import com.dataee.tutorserver.utils.ExcelUtil;
import com.dataee.tutorserver.utils.FileTypeUtil;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author JinYue
 * @CreateDate 2019/6/16 12:07
 */
@RestController
@RequestMapping("/admin/teachertest")
public class TeacherTestController {
    private Logger logger = LoggerFactory.getLogger(TeacherTestController.class);

    @Autowired
    private ITeacherTestService teacherTestService;

    @ApiOperation("上传试题文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "excel文件", required = true,
                    dataType = "MultipartFile", paramType = "form"),
            @ApiImplicitParam(name = "grade", value = "年级", required = true,
                    dataType = "Integer", paramType = "form"),
            @ApiImplicitParam(name = "cType", value = "科目", required = true,
                    dataType = "String", paramType = "form")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok")
    })
    @RequiresPermissions("question:create")
    @PostMapping("/import")
    public ResponseEntity importTeacherTest(@RequestParam MultipartFile file, @RequestParam Integer grade, @RequestParam String cType) throws BaseControllerException, IOException {
        if (file == null) {
            throw new IllegalParameterException("上传文件异常");
        }
        //处理文件
        //验证参数合法性
        String fullName = file.getOriginalFilename();
        String suffix = FileTypeUtil.getSuffix(fullName);
        if (FileTypeUtil.isExcel(suffix)) {
            List<Object> TeacherTestList = ExcelUtil.analysisExcel(TeacherTestExcelBean.class, file.getInputStream(), 1, 1);
            if (TeacherTestList != null || TeacherTestList.size() != 0) {
                teacherTestService.saveTeacherTests(TeacherTestList);
                return ResultUtil.success();
            }
            return ResultUtil.success();
        } else {
            logger.info("不是excel");
            throw new IllegalParameterException("文件类型有误");
        }
    }
}
