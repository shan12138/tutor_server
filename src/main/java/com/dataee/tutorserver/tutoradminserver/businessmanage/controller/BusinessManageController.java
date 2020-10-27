package com.dataee.tutorserver.tutoradminserver.businessmanage.controller;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.entity.*;
import com.dataee.tutorserver.tutoradminserver.businessmanage.bean.PartnerResponseBean;
import com.dataee.tutorserver.tutoradminserver.businessmanage.service.IBusinessManageService;
import com.dataee.tutorserver.tutorminiprogressserver.bean.InvitedTeacherCountAndMoney;
import com.dataee.tutorserver.tutorminiprogressserver.service.InvitedTeacherService;
import com.dataee.tutorserver.tutorpartnerserver.partnermanage.service.IPartnerManageService;
import com.dataee.tutorserver.utils.PageInfoUtil;
import com.dataee.tutorserver.utils.ResultUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/7 16:57
 */
@Api("商务管理模块")
@RestController
@RequestMapping("/")
public class BusinessManageController {
    @Autowired
    private IBusinessManageService businessManageService;
    @Autowired
    private IPartnerManageService partnerManageService;
    @Autowired
    private InvitedTeacherService invitedTeacherService;

    @ApiOperation(value = "获取合伙人列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit", value = "需要获取的信息的条数", required = true,
                    dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "需要获取的页（从1开始）",
                    required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "keyWord", value = "关键字", required = true,
                    dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "telephone", value = "手机号",
                    required = true, dataType = "String", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Student.class, responseContainer = "List")
    })
    @RequiresPermissions("partner:list")
    @GetMapping(value = "/partner")
    public ResponseEntity getPartnerList(@RequestParam Integer limit, @RequestParam Integer pageNo,
                                         @RequestParam(required = false) String keyWord,
                                         @RequestParam(required = false) String telephone) {
        Page page = new Page(limit, pageNo);
        List<PartnerResponseBean> partnerList = businessManageService.getPartnerList(page, keyWord, telephone);
        for(PartnerResponseBean partner : partnerList) {
            InvitedTeacherCountAndMoney teacherCountAndMoney =
                    invitedTeacherService.teacherCountAndMoney(partner.getPartnerId());
            Double parentMoney = partnerManageService.getParentInvitationMoney(partner.getPartnerId());
            Double withdrawedMoney = partnerManageService.getWithdrawedMoney(partner.getPartnerId());
            partner.setCommissionSum(teacherCountAndMoney.getMoney() + parentMoney);
            partner.setWithdrawNum(withdrawedMoney);
        }
        PageInfo pageInfo = new PageInfo(partnerList);
        NewPageInfo newPageInfo = PageInfoUtil.read(pageInfo);
        return ResultUtil.success(newPageInfo);
    }

    @ApiOperation(value = "禁用、启用合伙人")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "合伙人id",
                    required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "state", value = "禁用、启用状态",
                    required = true, dataType = "String", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Student.class, responseContainer = "List")
    })
    @RequiresPermissions("partner:disableEnable")
    @PutMapping(value = "/partner/{id}")
    public ResponseEntity getPartnerList(@PathVariable("id") Integer id, @RequestParam String state) throws BaseServiceException {
        businessManageService.changePartnerState(id, state);
        return ResultUtil.success();
    }

    @ApiOperation(value = "查询合伙人")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "telephone", value = "手机号", required = true,
                    dataType = "String", paramType = "query"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Student.class, responseContainer = "List")
    })
    @GetMapping(value = "/partner/telephone")
    public ResponseEntity getPartnerByTelephone(@RequestParam String telephone) throws BaseServiceException {
        return ResultUtil.success(businessManageService.getWeChatUserByTelephone(telephone));
    }

    @ApiOperation(value = "新增合伙人")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "weChatUserId", value = "微信用户id", required = true,
                    dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "名称", required = true,
                    dataType = "String", paramType = "query"),
    })
    @RequiresPermissions("partner:create")
    @PostMapping(value = "/partner")
    public ResponseEntity createPartner(@RequestParam Integer weChatUserId,
                                        @RequestParam String name) throws BaseServiceException {
        businessManageService.createPartner(weChatUserId, name);
        return ResultUtil.success();
    }

    @ApiOperation(value = "商务管理中获取受邀家长列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit", value = "需要获取的信息的条数", required = true,
                    dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "需要获取的页（从1开始）",
                    required = true, dataType = "Integer", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Student.class, responseContainer = "List")
    })
    @RequiresPermissions("partner/parent:list")
    @GetMapping(value = "/partner/parent")
    public ResponseEntity getInvitationParent(@RequestParam Integer limit, @RequestParam Integer pageNo,
                                              @RequestParam(required = false) String keyWord, @RequestParam(required = false) String telephone,
                                              @RequestParam(required = false) String state, @RequestParam(required = false) Integer partnerId) {
        Page page = new Page(limit, pageNo);
        NewPageInfo<Parent> parentList = businessManageService.getInvitationParent(page, keyWord, telephone, state, partnerId);
        return ResultUtil.success(parentList);
    }

    @ApiOperation(value = "根据手机号查询家长")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "telephone", value = "手机号", required = true,
                    dataType = "String", paramType = "query"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Parent.class, responseContainer = "List")
    })
    @GetMapping(value = "/partner/parent/account")
    public ResponseEntity getParentByTelephone(@RequestParam("telephone") String telephone) {
        List<Parent> parentList = businessManageService.getParentByTelephone(telephone);
        return ResultUtil.success(parentList);
    }

    @ApiOperation(value = "匹配家长账号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "受邀家长id", required = true,
                    dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "parentId", value = "已经注册的家长id",
                    required = true, dataType = "Integer", paramType = "query")
    })
    @RequiresPermissions("partner/parent/account:create")
    @PostMapping(value = "/partner/parent/account")
    public ResponseEntity matchParentAccount(@RequestParam Integer id, @RequestParam Integer parentId) throws BaseServiceException {
        businessManageService.matchParentAccount(id, parentId);
        return ResultUtil.success();
    }

    @ApiOperation(value = "获得咨询师列表")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Administrator.class, responseContainer = "List")
    })
    @GetMapping(value = "/consultant")
    public ResponseEntity getConsultantList() {
        return ResultUtil.success(businessManageService.getConsultantList());
    }

    @ApiOperation(value = "分配咨询师")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentInvitationId", value = "家长id", required = true,
                    dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "consultantId", value = "咨询师id", required = true,
                    dataType = "Integer", paramType = "query"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Student.class, responseContainer = "List")
    })
    @RequiresPermissions("consultant:create")
    @PostMapping(value = "/consultant")
    public ResponseEntity distributionConsultant(@RequestParam Integer parentInvitationId, @RequestParam Integer consultantId) throws BaseServiceException {
        businessManageService.distributionConsultant(parentInvitationId, consultantId);
        return ResultUtil.success();
    }

    @GetMapping(value = "/invite/parent/{id}")
    public ResponseEntity getInviteParent(@PathVariable Integer id) {
        ParentInvitation parentInvitation = businessManageService.getInviteParent(id);
        return ResultUtil.success(parentInvitation);
    }
}
