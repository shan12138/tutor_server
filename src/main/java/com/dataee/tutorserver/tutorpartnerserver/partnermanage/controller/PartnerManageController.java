package com.dataee.tutorserver.tutorpartnerserver.partnermanage.controller;

import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.commons.errorInfoenum.ControllerExceptionEnum;
import com.dataee.tutorserver.entity.Leisure;
import com.dataee.tutorserver.entity.Partner;
import com.dataee.tutorserver.tutorminiprogressserver.bean.InvitedTeacherCountAndMoney;
import com.dataee.tutorserver.tutorminiprogressserver.service.InvitedTeacherService;
import com.dataee.tutorserver.tutorpartnerserver.partnermanage.bean.PartnerInfo;
import com.dataee.tutorserver.tutorpartnerserver.partnermanage.bean.PartnerMoney;
import com.dataee.tutorserver.tutorpartnerserver.partnermanage.bean.PartnerParentRequestBean;
import com.dataee.tutorserver.tutorpartnerserver.partnermanage.service.IPartnerManageService;
import com.dataee.tutorserver.utils.ResultUtil;
import com.dataee.tutorserver.utils.SecurityUtil;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/12 15:53
 */
@Api("合伙人的小程序模块")
@RestController
@RequestMapping("/")
public class PartnerManageController {
    @Autowired
    private IPartnerManageService partnerManageService;
    @Autowired
    private InvitedTeacherService invitedTeacherService;

    @ApiOperation("获取邀请家长的页面信息(除邀请人列表外)")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", responseContainer = "List", response = Leisure.class)
    })
    @GetMapping("/wx/partner/parent/info")
    public ResponseEntity getParentInvitationInfo() throws BaseControllerException {
        Integer weChatUserId = SecurityUtil.getPersonId();
        if (weChatUserId != null) {
            PartnerInfo partner = partnerManageService.getParentInvitationInfo(weChatUserId);
            return ResultUtil.success(partner);
        } else {
            throw new BaseControllerException(ControllerExceptionEnum.RE_LOGIN);
        }
    }

    @ApiOperation("获取邀请家长列表(邀请表中的所有数据，不管家长是否注册过)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "页码", required = true,
                    dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "个数", required = true,
                    dataType = "int", paramType = "query"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", responseContainer = "List", response = Leisure.class)
    })
    @GetMapping("/wx/partner/parent/list")
    public ResponseEntity getParentInvitationList(@RequestParam("pageNo") int pageNo,
                                                  @RequestParam("limit") int limit) throws BaseControllerException {
        Integer weChatUserId = SecurityUtil.getPersonId();
        if (weChatUserId != null) {
            Page page = new Page(limit, pageNo);
            return ResultUtil.success(partnerManageService.getParentInvitationList(page, weChatUserId));
        } else {
            throw new BaseControllerException(ControllerExceptionEnum.RE_LOGIN);
        }
    }

    @ApiOperation("获取邀请家长列表(家长注册过的)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "页码", required = true,
                    dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "个数", required = true,
                    dataType = "int", paramType = "query"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", responseContainer = "List", response = Leisure.class)
    })
    @GetMapping("/wx/partner/parent/register/list")
    public ResponseEntity getParentInvitationRegisterList(
                                                  @RequestParam("pageNo") int pageNo,
                                                  @RequestParam("limit") int limit) throws BaseControllerException {
        Integer weChatUserId = SecurityUtil.getPersonId();
        if (weChatUserId != null) {
            Page page = new Page(limit, pageNo);
            return ResultUtil.success(partnerManageService.getParentInvitationRegisterList(page, weChatUserId));
        } else {
            throw new BaseControllerException(ControllerExceptionEnum.RE_LOGIN);
        }
    }

    @ApiOperation(value = "获取邀请家长详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentId", value = "家长id", required = true,
                    dataType = "int", paramType = "query"),
    })
    @GetMapping("/wx/partner/parent/invitation")
    public ResponseEntity getParentInfoDetail(@RequestParam int parentId) {
        return ResultUtil.success(partnerManageService.getParentInfoDetail(parentId));
    }

    @ApiOperation(value = "邀请家长")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parent", value = "邀请家长信息体", required = true,
                    dataType = "ParentInvitationRequestBean", paramType = "body")
    })
    @PostMapping("/wx/partner/parent/invitation")
    public ResponseEntity inviteParent(@RequestBody PartnerParentRequestBean parent) throws BaseControllerException {
        Integer weChatUserId = SecurityUtil.getPersonId();
        partnerManageService.inviteParent(weChatUserId, parent);
        return ResultUtil.success();
    }

    @ApiOperation(value = "获取合伙人个人信息")
    @GetMapping("/wx/partner")
    public ResponseEntity getPartnerInfo() throws BaseControllerException {
        Integer weChatUserId = SecurityUtil.getPersonId();
        return ResultUtil.success(partnerManageService.getPartnerInfo(weChatUserId));
    }

    @ApiOperation(value = "获取合伙人手机号")
    @GetMapping("/wx/partner/telephone")
    public ResponseEntity getPartnerTelephone() throws BaseControllerException {
        Integer weChatUserId = SecurityUtil.getPersonId();
        return ResultUtil.success(partnerManageService.getPartnerTelephone(weChatUserId));
    }

    @ApiOperation(value = "修改个人信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "alipayAccount", value = "支付宝账号", required = true,
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "alipayName", value = "支付宝名称", required = true,
                    dataType = "string", paramType = "query")
    })
    @PutMapping("/wx/partner")
    public ResponseEntity updatePartnerInfo(@RequestParam("alipayAccount") String alipayAccount,
                                            @RequestParam("alipayName") String alipayName) throws BaseControllerException {
        Integer weChatUserId = SecurityUtil.getPersonId();
        partnerManageService.updatePartnerInfo(weChatUserId, alipayAccount, alipayName);
        return ResultUtil.success();
    }

    @ApiOperation(value = "获取合伙人的提现金额")
    @GetMapping("/wx/partner/money")
    public ResponseEntity getMoney() throws BaseControllerException {
        Integer weChatUserId = SecurityUtil.getPersonId();
        Partner partner = partnerManageService.getPartnerByWeChatUserId(weChatUserId);
        InvitedTeacherCountAndMoney teacherCountAndMoney =
                invitedTeacherService.teacherCountAndMoney(partner.getPartnerId());
        Double parentMoney = partnerManageService.getParentInvitationMoney(partner.getPartnerId());
        parentMoney = (parentMoney == null) ? 0.0 : parentMoney;
        Double withdrawingMoney = partnerManageService.getWithdrawingMoney(partner.getPartnerId());
        withdrawingMoney = (withdrawingMoney == null) ? 0.0 : withdrawingMoney;
        Double withdrawedMoney = partnerManageService.getWithdrawedMoney(partner.getPartnerId());
        withdrawedMoney = (withdrawedMoney == null) ? 0.0 : withdrawedMoney;
        PartnerMoney partnerMoney = new PartnerMoney(teacherCountAndMoney.getMoney()
                + parentMoney - withdrawingMoney - withdrawedMoney, withdrawingMoney);
        return ResultUtil.success(partnerMoney);
    }

    @ApiOperation(value = "申请提现")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "number", value = "提现金额", required = true,
                    dataType = "int", paramType = "query"),
    })
    @PostMapping("/wx/partner/money")
    public ResponseEntity withdrawMoney(@RequestParam Integer number) throws BaseControllerException {
        Integer weChatUserId = SecurityUtil.getPersonId();
        Partner partner = partnerManageService.getPartnerByWeChatUserId(weChatUserId);
        InvitedTeacherCountAndMoney teacherCountAndMoney =
                invitedTeacherService.teacherCountAndMoney(partner.getPartnerId());
        Double parentMoney = partnerManageService.getParentInvitationMoney(partner.getPartnerId());
        parentMoney = (parentMoney == null) ? 0.0 : parentMoney;
        Double withdrawingMoney = partnerManageService.getWithdrawingMoney(partner.getPartnerId());
        withdrawingMoney = (withdrawingMoney == null) ? 0.0 : withdrawingMoney;
        Double withdrawedMoney = partnerManageService.getWithdrawedMoney(partner.getPartnerId());
        withdrawedMoney = (withdrawedMoney == null) ? 0.0 : withdrawedMoney;
        if((teacherCountAndMoney.getMoney()
                + parentMoney - withdrawingMoney - withdrawedMoney) * 100 < number) {
            throw new BaseControllerException(ControllerExceptionEnum.WITHDRAW_BEYOND);
        }
        partnerManageService.withdrawMoney(weChatUserId, number);
        return ResultUtil.success();
    }

    @ApiOperation(value = "查看可提现金额明细")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "页码", required = true,
                    dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "个数", required = true,
                    dataType = "int", paramType = "query"),
    })
    @GetMapping("/wx/partner/money/detail")
    public ResponseEntity getMoneyDetail(@RequestParam("pageNo") int pageNo,
                                         @RequestParam("limit") int limit) throws BaseControllerException {
        Integer weChatUserId = SecurityUtil.getPersonId();
        Page page = new Page(limit, pageNo);
        return ResultUtil.success(partnerManageService.getMoneyDetail(page, weChatUserId));
    }

    @ApiOperation(value = "查看提现记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "页码", required = true,
                    dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "个数", required = true,
                    dataType = "int", paramType = "query"),
    })
    @GetMapping("/wx/partner/withdraw/detail")
    public ResponseEntity getWithdrawDetail(@RequestParam("pageNo") int pageNo,
                                         @RequestParam("limit") int limit) throws BaseControllerException {
        Integer weChatUserId = SecurityUtil.getPersonId();
        Page page = new Page(limit, pageNo);
        return ResultUtil.success(partnerManageService.getWithdrawDetail(page, weChatUserId));
    }
}
