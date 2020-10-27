package com.dataee.tutorserver.userserver.controller;

import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.commonservice.ISTSProvisionAuthorityService;
import com.dataee.tutorserver.commons.commonservice.impl.STSPorvisionAuthorityServiceImpl;
import com.dataee.tutorserver.utils.ResultUtil;
import com.dataee.tutorserver.utils.SecurityUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 获取资源权限
 *
 * @author JinYue
 * @CreateDate 2019/5/10 1:46
 */
@RestController
public class ProvisionAuthorityController {
    @Autowired
    private ISTSProvisionAuthorityService stsProvisionAuthorityService;

    private final String COURSE_ADMIN = "courseAdmin";

    private final String  HIRE_TEACHER = "hireTeacher";

    /**
     * 授权上传错题和合同以及教师信息的权力
     *
     * @param role
     * @param keyWord 关键词代表要上传文件的类别errorQuestion或contract或teacherInfo
     * @return
     * @throws BaseControllerException
     * @throws BaseServiceException
     */
    @ApiOperation("获取上传资源的全限，keyword支持errorQuestion|teacherInfo")
    @RequiresRoles(value = {"teacher", "parent"}, logical = Logical.OR)
    @RequestMapping(value = "/{role:teacher|parent}/resource/authority", method = RequestMethod.GET)
    public ResponseEntity getOSSAuthority(@PathVariable String role, @RequestParam @NotBlank(message = "不确定的权限选择")
    @Pattern(regexp = "^errorQuestion|teacherInfo|parentInfo", message = "请求错误") String keyWord) throws BaseControllerException, BaseServiceException {
        String userId = SecurityUtil.getUserId();
        //获取32位sessionName
        String newUserId = getSessionName(userId);
        AssumeRoleResponse.Credentials credentials = stsProvisionAuthorityService.getUserResourceCredentials(keyWord, role, newUserId);
        return ResultUtil.success(credentials);
    }

    @ApiOperation("管理员获取上传权限合同和课件（路径只有以及目录contracts）")
    @RequiresRoles(value = {"superAdmin", "courseAdmin", "parentAdmin", "teacherAdmin","scheduleAdmin"}, logical = Logical.OR)
    @RequiresPermissions("auth:ed")
    @RequestMapping(value = "/admin/{file:contract|courseware}/authority", method = RequestMethod.GET)
    public ResponseEntity getauthority(@PathVariable String file) throws BaseControllerException, BaseServiceException {
        String userId = SecurityUtil.getUserId();
        //获取32位sessionName
        String newUserId = getSessionName(userId);
        //管理员获取contract的policy
        AssumeRoleResponse.Credentials credentials = stsProvisionAuthorityService.getAdminContractCredentials(file, COURSE_ADMIN, newUserId);
        return ResultUtil.success(credentials);
    }


    @ApiOperation("管理员获取上传权限简历（路径只有以及目录resume）")
    @RequiresRoles(value = {"hireTeacher"}, logical = Logical.OR)
    @RequiresPermissions("auth:ed")
    @RequestMapping(value = "/admin/{file:resume}/authority", method = RequestMethod.GET)
    public ResponseEntity getOssAuthority(@PathVariable String file) throws BaseControllerException, BaseServiceException {
        String userId = SecurityUtil.getUserId();
        //获取32位sessionName
        String newUserId = getSessionName(userId);
        //管理员获取contract的policy
        AssumeRoleResponse.Credentials credentials = stsProvisionAuthorityService.getAdminContractCredentials(file, HIRE_TEACHER, newUserId);
        return ResultUtil.success(credentials);
    }

    /**
     * 将userId更改后用作stsToken的sessionName
     *
     * @param userId
     * @return
     */
    private String getSessionName(String userId) {
        userId.replace("-", "");
        if (userId.length() > 32) {
            userId = userId.substring(0, 31);
        }
        return userId;
    }

}
