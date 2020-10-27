package com.dataee.tutorserver.userserver.controller;

import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.ContractUrlResponseBean;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.commons.commonservice.IOSSService;
import com.dataee.tutorserver.commons.errorInfoenum.ServiceExceptionsEnum;
import com.dataee.tutorserver.commons.exceptions.IllegalParameterException;
import com.dataee.tutorserver.entity.ContractPdf;
import com.dataee.tutorserver.userserver.bean.GradeAndSubjectResponseBean;
import com.dataee.tutorserver.userserver.service.IContractsService;
import com.dataee.tutorserver.utils.ResultUtil;
import com.dataee.tutorserver.utils.SecurityUtil;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import java.net.URL;
import java.util.List;

/**
 * 教员和家长查看合同
 *
 * @author JinYue
 * @CreateDate 2019/5/6 1:34
 */
@RestController
@RequiresRoles(value = {"teacher", "parent"}, logical = Logical.OR)
@RequiresPermissions("auth:ed")
public class ContractsController {
    private final Logger logger = LoggerFactory.getLogger(ContractsController.class);
    @Autowired
    private IContractsService contractService;
    @Autowired
    private IOSSService ossService;

    public void setContractServicet(IContractsService contractService) {
        this.contractService = contractService;
    }


    /**
     * 查看指定的合同
     *
     * @param id
     * @return
     * @throws BaseServiceException
     */
    @ApiOperation(value = "查看指定的合同")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = NewPageInfo.class, responseContainer = "List")
    })
    @Valid
    @RequestMapping(value = "/contract/{id:\\d+}", method = RequestMethod.GET)
    public ResponseEntity viewContract(@PathVariable @Digits(integer = 6, fraction = 0, message = "未指定具体合同") Integer id,
                                       @RequestParam Integer limit, @RequestParam Integer page) throws BaseServiceException, IllegalParameterException {
        Page currPage = new Page(limit, page);
        NewPageInfo resourceAddress = contractService.getResourceAddress(id, currPage);
        //    获取URL
        if (resourceAddress.getList() == null) {
            throw new BaseServiceException(ServiceExceptionsEnum.FILE_NOT_EXIT);
        }
        List<String> urlList = ossService.getURLList(resourceAddress.getList());
        resourceAddress.setList(urlList);
        return ResultUtil.success(resourceAddress);
    }
    /**
     * 下载指定的合同
     */
    @ApiOperation(value = "下载指定的合同")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok")
    })
    @Valid
    @RequestMapping(value = "/downLoadContract/{contractId:\\d+}", method = RequestMethod.GET)
    public ResponseEntity downLoadContract(@PathVariable("contractId") @NotBlank(message = "文件序号不能为空") int contractId) throws BaseServiceException, BaseControllerException {
        ContractPdf contractPdf = contractService.getContractPdf(contractId);
        if(contractPdf!=null){
            if(contractPdf.getPersonRole()==40){
                if(contractPdf.getParentContract()!=null&&contractPdf.getParentContract().getIsSign()==0){
                    //获取资源路径
                    String resourceAddress = contractService.getResourceAddress(contractId);
                    //获取文件路径
                    URL url = ossService.getURL(resourceAddress);
                    ContractUrlResponseBean contractUrlResponseBean = new ContractUrlResponseBean();
                    contractUrlResponseBean.setContractUrl(url);
                    return ResultUtil.success(contractUrlResponseBean);
                }else if(contractPdf.getParentContract()!=null&&contractPdf.getParentContract().getIsSign()==1){
                    String resourceAddress = contractService.getParentResourceAddress(contractId);
                    //获取文件路径
                    URL url = ossService.getURL(resourceAddress);
                    ContractUrlResponseBean contractUrlResponseBean = new ContractUrlResponseBean();
                    contractUrlResponseBean.setContractUrl(url);
                    return ResultUtil.success(contractUrlResponseBean);
                }
            }else {
                //获取资源路径
                String resourceAddress = contractService.getResourceAddress(contractId);
                //获取文件路径
                URL url = ossService.getURL(resourceAddress);
                ContractUrlResponseBean contractUrlResponseBean = new ContractUrlResponseBean();
                contractUrlResponseBean.setContractUrl(url);
                return ResultUtil.success(contractUrlResponseBean);
            }
        }
        return ResultUtil.success();
    }

    /**
     * 查看合同列表
     *
     * @return
     * @throws BaseControllerException
     */
    @ApiOperation(value = "查看合同列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit", value = "需要获取的信息的条数", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "需要获取的页（从1开始）", required = true, dataType = "Integer", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = NewPageInfo.class, responseContainer = "List")
    })
    @RequestMapping(value = "/contract/list", method = RequestMethod.GET)
    public ResponseEntity viewContractList(@RequestParam Integer limit, @RequestParam Integer pageNo) throws BaseControllerException, BaseServiceException {
        Integer personId = SecurityUtil.getPersonId();
        Page page = new Page(limit, pageNo);
        String userId = SecurityUtil.getUserId();
        NewPageInfo<ContractPdf> contractList = contractService.getContractList(personId, userId, page);
        return ResultUtil.success(contractList);
    }
}
