package com.dataee.tutorserver.tutorpatriarchserver.controller;

import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.ContractUrlResponseBean;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.commons.commonservice.IOSSService;
import com.dataee.tutorserver.entity.Address;
import com.dataee.tutorserver.entity.MessageInformation;
import com.dataee.tutorserver.entity.Parent;
import com.dataee.tutorserver.entity.Student;
import com.dataee.tutorserver.tutoradminserver.filemanage.service.IContractService;
import com.dataee.tutorserver.tutorpatriarchserver.bean.*;
import com.dataee.tutorserver.tutorpatriarchserver.service.IParentCenterService;
import com.dataee.tutorserver.utils.ResultUtil;
import com.dataee.tutorserver.utils.SecurityUtil;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 家长个人中心模块
 *
 * @Author ChenShanShan
 * @CreateDate 2019/4/29 10:56
 */
@Api(value = "家长个人中心模块")
@RestController
@RequestMapping("/")
public class ParentCenterController {
    @Autowired
    private IParentCenterService parentCenterService;

    @RequiresRoles("parent")
    @ApiOperation(value = "家长端报名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentName", value = "家长姓名", required = true,
                    dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sex", value = "家长性别", required = true,
                    dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "idCard", value = "家长身份证号", required = true,
                    dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "inviteCode", value = "邀请码", required = true,
                    dataType = "String", paramType = "query")
    })
    @PostMapping(value = "/parent")
    public ResponseEntity createParent(@RequestParam String parentName, @RequestParam Integer sex, @RequestParam String idCard,
                                        @RequestParam String inviteCode) throws BaseControllerException, BaseServiceException {
        parentCenterService.createParent(SecurityUtil.getPersonId(), parentName, sex, idCard, inviteCode);
        return ResultUtil.success();
    }

    @RequiresRoles("parent")
    @ApiOperation(value = "家长端获取子女列表信息页面（正式）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit", value = "需要获取的信息的条数", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "需要获取的页（从1开始）", required = true, dataType = "Integer", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Student.class, responseContainer = "List")
    })
    @GetMapping(value = "/childList")
    public ResponseEntity getOwnChildren(@RequestParam Integer limit, @RequestParam Integer pageNo) throws BaseControllerException {
        Integer parentId = SecurityUtil.getPersonId();
        Page page = new Page(limit, pageNo);
        NewPageInfo<Student> studentList = parentCenterService.getOwnChildren(parentId, page);
        return ResultUtil.success(studentList);
    }

    @RequiresRoles("parent")
    @ApiOperation(value = "报名时家长端获取子女详情信息（报名）")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Student.class, responseContainer = "List")
    })
    @GetMapping(value = "/oneChild")
    public ResponseEntity getOneChildDetail() throws BaseControllerException {
        Integer parentId = SecurityUtil.getPersonId();
        //此时默认家长只有一个子女，查询出该学生id然后查询信息
        Student student = parentCenterService.getOneChildDetail(parentId);
        if (student == null) {
            return ResultUtil.success();
        }
        return ResultUtil.success(student);
    }

    @RequiresRoles("parent")
    @ApiOperation(value = "家长端查看子女详细信息（正式）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studentId", value = "学生id", required = true,
                    dataType = "String", paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Student.class, responseContainer = "List")
    })
    @GetMapping("/studentDetailInfo/{studentId}")
    public ResponseEntity getChildDetailInfo(@PathVariable("studentId") @NotBlank(message = "学生ID不能为空") String studentId) throws BaseControllerException {
        Student student = parentCenterService.getChildDetailInfo(studentId);
        return ResultUtil.success(student);
    }

    @RequiresRoles("parent")
    @ApiOperation(value = "家长端新增我的子女信息(正式)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "enrollChildInfoRequestBean", value = "学生信息体", required = true,
                    dataType = "EnrollChildInfoRequestBean", paramType = "body")
    })
    @PostMapping("/child")
    public ResponseEntity addChildInfo(@RequestBody @Valid EnrollChildInfoRequestBean enrollChildInfo)
            throws BaseServiceException, BaseControllerException {
        enrollChildInfo.setParentId(SecurityUtil.getPersonId());
        parentCenterService.addChildInfo(enrollChildInfo);
        return ResultUtil.success();
    }

    @RequiresRoles("parent")
    @ApiOperation(value = "家长端新增我的子女信息(报名)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "enrollChildInfoRequestBean", value = "学生信息体", required = true,
                    dataType = "EnrollChildInfoRequestBean", paramType = "body")
    })
    @PostMapping("/oneChild")
    public ResponseEntity addOneChildInfo(@RequestBody @Valid EnrollChildInfoRequestBean enrollChildInfo)
            throws BaseServiceException, BaseControllerException {
        enrollChildInfo.setParentId(SecurityUtil.getPersonId());
        Student student = parentCenterService.getOneChildDetail(SecurityUtil.getPersonId());
        if (student != null && student.getStudentId() != null) {
            //填写完子女信息后（只有一个）进行修改
            enrollChildInfo.setStudentId(student.getStudentId());
            parentCenterService.changeChildInfo(enrollChildInfo);
        } else {
            //若是第一次进入页面，添加子女则insert
            parentCenterService.addChildInfo(enrollChildInfo);
        }
        return ResultUtil.success();
    }

    @RequiresRoles("parent")
    @ApiOperation(value = "家长查看个人信息（正式以及报名）")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Parent.class)
    })
    @GetMapping("/parent")
    public ResponseEntity getParentInfo() throws BaseControllerException {
        return ResultUtil.success(parentCenterService.getParentInfo(SecurityUtil.getPersonId().toString()));
    }

    @RequiresRoles("parent")
    @ApiOperation(value = "家长修改个人信息（正式以及报名）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "changeParentInfoRequestBean", value = "修改信息体", required = true,
                    dataType = "ChangeParentInfoRequestBean", paramType = "body")
    })
    @PutMapping("/parent")
    public ResponseEntity changeParentInfo(@RequestBody @Valid ChangeParentInfoRequestBean changeParentInfoRequestBean)
            throws BaseControllerException, BaseServiceException {
        parentCenterService.changeParentInfo(SecurityUtil.getPersonId(),
                changeParentInfoRequestBean.getParentName(), changeParentInfoRequestBean.getSex());
        return ResultUtil.success();
    }

    @RequiresRoles("parent")
    @ApiOperation(value = "家长端修改子女信息（正式）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "enrollChildInfo", value = "学生信息体", required = true,
                    dataType = "EnrollChildInfoRequestBean", paramType = "body")
    })
    @PutMapping("/child")
    public ResponseEntity changeChildInfo(@RequestBody @Valid EnrollChildInfoRequestBean enrollChildInfo) throws BaseControllerException {
        parentCenterService.changeChildInfo(enrollChildInfo);
        return ResultUtil.success();
    }

    @RequiresRoles("parent")
    @ApiOperation(value = "家长新增地址信息(正式以及注册)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "createAddressRequestBean", value = "新增信息体", required = true,
                    dataType = "CreateAddressRequestBean", paramType = "body")
    })
    @PostMapping("/address")
    public ResponseEntity createAddress(@RequestBody @Valid CreateAddressRequestBean createAddressRequestBean)
            throws BaseControllerException, BaseServiceException {
        int state = parentCenterService.getState(SecurityUtil.getPersonId());
        createAddressRequestBean.setParentId(SecurityUtil.getPersonId());
        parentCenterService.addAddress(createAddressRequestBean);

       if (state == 1) {
            parentCenterService.changeParentState(SecurityUtil.getPersonId());
        }
        return ResultUtil.success();
    }



    @RequiresRoles("parent")
    @ApiOperation(value = "家长获取地址信息（正式以及注册）")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Address.class, responseContainer = "List")
    })
    @GetMapping("/address")
    public ResponseEntity getAddress() throws BaseControllerException {
/*  int state = parentCenterService.getState(SecurityUtil.getPersonId());
        if (state == 3) {
            return ResultUtil.success(parentCenterService.getAddress(SecurityUtil.getPersonId().toString()));
        } else if (state == 1 || state == 2) {
            //未完善信息，家长此时只有一个地址信息且处于未被审核状态
            Integer addressId = parentCenterService.getAddressId(SecurityUtil.getPersonId());
            return ResultUtil.success(parentCenterService.getOneAddress(addressId));
        }*/
        return ResultUtil.success(parentCenterService.getAddressByParentId(SecurityUtil.getPersonId()));
    }

    @GetMapping("/officialAddress")
    public ResponseEntity getOfficialAddress() throws BaseControllerException {
        int state = parentCenterService.getState(SecurityUtil.getPersonId());
        if (state == 3) {
            return ResultUtil.success(parentCenterService.getOfficialAddress(SecurityUtil.getPersonId().toString()));
        }
        return ResultUtil.success();
    }

    @RequiresRoles("parent")
    @ApiOperation(value = "修改地址信息(正式以及注册)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "changeAddressRequestBean", value = "修改信息体", required = true,
                    dataType = "ChangeAddressRequestBean", paramType = "body")
    })
    @PutMapping("/address")
    public ResponseEntity changeAddress(@RequestBody @Valid ChangeAddressRequestBean changeAddressRequestBean)
            throws BaseServiceException, BaseControllerException {
        //int state = parentCenterService.getState(SecurityUtil.getPersonId());
      /* if (state == 3) {
            parentCenterService.changeAddressInfo(addressId);
        }**/ /*else if (state == 1) {
            Integer addressId = parentCenterService.getAddressId(SecurityUtil.getPersonId());
            changeAddressRequestBean.setAddressId(addressId);
            parentCenterService.changeOneAddressInfo(changeAddressRequestBean);
        }*/
        parentCenterService.changeAddressInfo(changeAddressRequestBean);
        return ResultUtil.success();
    }


    @RequiresRoles("parent")
    @ApiOperation(value = "修改课程地址信息(正式以及注册)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "changeAddressRequestBean", value = "修改信息体", required = true,
                    dataType = "ChangeAddressRequestBean", paramType = "body")
    })
    @PutMapping("/courseAddress")
    public ResponseEntity changeCourseAddress(@RequestBody @Valid ChangeAddressRequestBean changeAddressRequestBean)
            throws BaseServiceException, BaseControllerException {
        int state = parentCenterService.getState(SecurityUtil.getPersonId());
        if (state == 3) {
            parentCenterService.changeCourseAddressInfo(changeAddressRequestBean);
        }
        return ResultUtil.success();
    }

    @GetMapping("/{role:teacher|parent}/msgNum")
    public ResponseEntity getMessageNum(@PathVariable("role") String role) throws BaseControllerException {
        if (role.equals("teacher")) {
            return ResultUtil.success(parentCenterService.getMsgNum(SecurityUtil.getPersonId(), 20));
        } else {
            return ResultUtil.success(parentCenterService.getMsgNum(SecurityUtil.getPersonId(), 40));
        }
    }

    @RequiresRoles(value = {"parent", "teacher"}, logical = Logical.OR)
    @ApiOperation(value = "家长或教师端获取消息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "role", value = "角色", required = true,
                    dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "limit", value = "需要获取的信息的条数", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "需要获取的页（从1开始）", required = true, dataType = "Integer", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = MessageInformation.class, responseContainer = "List")
    })
    @GetMapping("/{role:teacher|parent}/msgList")
    public ResponseEntity getParentMsg(@PathVariable("role") String role, @RequestParam Integer limit, @RequestParam Integer pageNo) throws BaseControllerException {
        Page page = new Page(limit, pageNo);
        if (role.equals("teacher")) {
            return ResultUtil.success(parentCenterService.getMsgList(SecurityUtil.getPersonId(), 20, page));
        } else {
            return ResultUtil.success(parentCenterService.getMsgList(SecurityUtil.getPersonId(), 40, page));
        }
    }

    /**
     * 查看合同文件
     *
     */
    @RequiresRoles(value = {"parent"}, logical = Logical.OR)
    @GetMapping("/parentcontract/list")
    public ResponseEntity scanFile(@RequestParam Integer limit, @RequestParam Integer pageNo)
            throws BaseServiceException, BaseControllerException {
        Integer personId = SecurityUtil.getPersonId();
        Page page = new Page(limit, pageNo);
        return ResultUtil.success(parentCenterService.getParentContractList(personId, 40, page));
    }


    @RequiresRoles(value = {"parent"}, logical = Logical.OR)
    @GetMapping("/parentcontract/{id}")
    public ResponseEntity scanFile(@PathVariable("id") Integer contractId)throws BaseServiceException{
        return ResultUtil.success(parentCenterService.getParentContractByid(contractId));
    }

    /**
     * 上传签名图片
     *
     */

    @ApiOperation(value = "家长上传签名图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "path", value = "上传的路径", required = true,
                    dataType = "String", paramType = "path")
    })
    @RequiresRoles(value = {"parent"}, logical = Logical.OR)
    @PutMapping("/uploadSignPicture")
    public ResponseEntity uploadSignPicture(@RequestParam("contractId")Integer contractId,
                                            @RequestParam("path") String path) throws BaseServiceException, BaseControllerException {
        parentCenterService.getUploadParentContract(contractId,path);
        return ResultUtil.success();
    }
}
