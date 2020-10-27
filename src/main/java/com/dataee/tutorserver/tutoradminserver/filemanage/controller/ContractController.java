package com.dataee.tutorserver.tutoradminserver.filemanage.controller;

import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.ContractUrlResponseBean;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.commonservice.IOSSService;
import com.dataee.tutorserver.commons.commonservice.ISavePDFService;
import com.dataee.tutorserver.commons.exceptions.IllegalParameterException;
import com.dataee.tutorserver.entity.ContractPdf;
import com.dataee.tutorserver.tutoradminserver.filemanage.bean.ParentContractRequestBean;
import com.dataee.tutorserver.tutoradminserver.filemanage.bean.TeacherContractRequestBean;
import com.dataee.tutorserver.tutoradminserver.filemanage.service.IContractService;
import com.dataee.tutorserver.tutoradminserver.filemanage.service.ISaveContractManageService;
import com.dataee.tutorserver.userserver.service.IPersonService;
import com.dataee.tutorserver.utils.ResultUtil;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.net.URL;

/**
 * 文件操作
 *
 * @author JinYue
 * @CreateDate 2019/5/3 14:28
 */
@RestController
@RequestMapping("/admin/contract")
public class ContractController {
    private final Logger logger = LoggerFactory.getLogger(ContractController.class);
    @Autowired
    private IOSSService ossService;

    @Autowired
    private IContractService contractService;

    @Autowired
    private IPersonService personService;

    @Autowired
    private ISaveContractManageService saveContractManageService;

    private final String PARENT = "parent";

    /**
     * 上传教员的合同
     *
     * @param contract
     * @return
     */
    @RequiresPermissions("teacher/contract:create")
    @PostMapping("/")
    public ResponseEntity uploadTeacherContractFile(@RequestBody @Valid TeacherContractRequestBean contract)
            throws BaseControllerException, BaseServiceException {
        //判断personId和role是否有效
        if (!personService.findPerson(contract.getPersonId(), contract.getRole())) {
            throw new IllegalParameterException();
        }
        saveContractManageService.saveTeacherContract(contract);
        return ResultUtil.success();
    }

    /**
     * 上传家长的合同
     *
     * @param contract
     * @return
     */
    @PostMapping("/parent")
    public ResponseEntity uploadParentContractFile(@RequestBody @Valid ParentContractRequestBean contract) {
        saveContractManageService.saveParentContract(contract);
        return ResultUtil.success();
    }


    /**
     * 删除指定的合同文件
     *
     * @param contractId
     * @return
     * @throws BaseServiceException
     */
    //@DeleteMapping("/{contractId}")
    //public ResponseEntity deleteFile(@PathVariable @NotNull(message = "文件序号不能为空 ") Integer contractId) throws BaseServiceException {
    //    //获取资源路径
    //    String resourceAddress = contractService.getResourceAddress(contractId);
    //    //获取文件路径删除仓库中的文件
    //    ossService.deleteFile(resourceAddress);
    //    //删除数据库中的存储数据
    //    contractService.deleteContract(contractId);
    //    return ResultUtil.success();
    //}

    /**
     * 查看合同文件
     *
     * @param contractId
     * @return
     * @throws BaseServiceException
     */
    @GetMapping("/{contractId}")
    public ResponseEntity scanFile(@PathVariable("contractId") @NotBlank(message = "文件序号不能为空") int contractId)
            throws BaseServiceException {
        //获取资源路径
        ContractPdf contractPdf = contractService.getResourceAddress(contractId);
        ContractUrlResponseBean contractUrlResponseBean = new ContractUrlResponseBean();
        //获取文件路径
        URL url = ossService.getURL(contractPdf.getPdfAddress());
        contractUrlResponseBean.setContractUrl(url);
        if(contractPdf!=null&&contractPdf.getSignedPdfAddress()!=null){
            URL url1 = ossService.getURL(contractPdf.getSignedPdfAddress());
            contractUrlResponseBean.setContractUrl(url1);
        }
        return ResultUtil.success(contractUrlResponseBean);
    }

    @ApiOperation("管理员获取指定人员的合同列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit", value = "每页数量", required = true,
                    dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "Page", value = "当前要访问的页", required = true,
                    dataType = "Integer", paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok",
                    response = NewPageInfo.class)
    })
    @GetMapping("/{role:teacher|parent}/list")
    public ResponseEntity scanContractList(@PathVariable String role, @RequestParam @NotBlank(message = "id不能为空") String personId,
                                           @RequestParam(defaultValue = "2") Integer limit, @RequestParam(defaultValue = "1") Integer page) throws BaseServiceException {
        NewPageInfo contracts = contractService.getContracts(role, personId, page, limit);
        return ResultUtil.success(contracts);
    }

    /**
     * 管理员删除指定的合同
     *
     * @param id
     * @return
     * @throws BaseServiceException
     */
    @ApiOperation("删除指定的合同")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "合同的id", required = true, dataType = "Integer", paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok")
    })
    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity deleteContract(@PathVariable Integer id) throws BaseServiceException {
        contractService.deleteContract(id);
        return ResultUtil.success();
    }
}
