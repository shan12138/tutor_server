package com.dataee.tutorserver.tutoradminserver.filemanage.service.impl;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.commonservice.IPDFConvertToImageService;
import com.dataee.tutorserver.commons.commonservice.ISavePDFService;
import com.dataee.tutorserver.commons.errorInfoenum.ServiceExceptionsEnum;
import com.dataee.tutorserver.commons.exceptions.IllegalParameterException;
import com.dataee.tutorserver.commons.exceptions.SQLOperationException;
import com.dataee.tutorserver.tutoradminserver.filemanage.bean.ContractRequestBean;
import com.dataee.tutorserver.tutoradminserver.filemanage.dao.ContractMapper;
import com.dataee.tutorserver.tutoradminserver.filemanage.service.IContractService;
import com.dataee.tutorserver.userserver.dao.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author JinYue
 * @CreateDate 2019/6/9 22:51
 */

@Service("saveContractService")
@Transactional(rollbackFor = Exception.class)
public class SaveContractServiceImpl<T extends ContractRequestBean> implements ISavePDFService<T> {
    private Logger logger = LoggerFactory.getLogger(SaveContractServiceImpl.class);
    @Autowired
    private ContractMapper contractMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IPDFConvertToImageService pdfConvertToImageService;
    @Autowired
    private IContractService contractService;

    @Override
    public Integer saveFile(T file) throws BaseServiceException, IllegalParameterException {
        Integer contractId = saveFilePDF(file);
        List<String> paths = pdfConvertToImageService.pdfRendering(file.getContractAddress());
        if (contractId != null && paths != null) {
            saveFileImage(contractId, paths);
        } else {
            throw new BaseServiceException(ServiceExceptionsEnum.CLIENT_OPERATE_EXCEPTION);
        }
        return contractId;
    }

    @Override
    public Integer saveFilePDF(T fileInfo) throws BaseServiceException {
        Integer roleId = userMapper.queryRoleIdByRole(fileInfo.getRole());
        if (roleId != null) {
            //判断当前合同是否存在
            Integer contractId = this.findContract(fileInfo, roleId);
            //删除原来的合同
            if (contractId != null) {
                contractService.deleteContract(contractId);
            }
            contractMapper.insertContract(fileInfo, roleId);
            Integer newContractId = fileInfo.getContractId();
            //判断是否需要插入到student中去
            if (newContractId == null) {
                throw new SQLOperationException();
            }
            return newContractId;
        } else {
            throw new BaseServiceException(ServiceExceptionsEnum.ROLE_NOT_EXIT);
        }

    }

    @Override
    public void saveFileImage(Integer pdfId, List<String> paths) throws SQLOperationException {
        int size = paths.size();
        int count = contractMapper.insertContractImages(pdfId, paths);
        if (size != count) {
            logger.info("saveContractImage: {}", "数据插入异常，预计插入量 " + size + "实际插入量 " + count);
            throw new SQLOperationException();
        }
        //    更新合同的上传的状态
        contractMapper.updateUploadState(pdfId);
    }

    /**
     * 判断当前的合同是否已经存在
     *
     * @param contract
     * @return
     */
    public Integer findContract(T contract, Integer roleId) {
        Integer contractId = contractMapper.queryContractId(contract.getContractName(), contract.getContractAddress(),
                contract.getPersonId(), roleId);
        if (contractId != null) {
            contractId = -1;
        }
        return contractId;
    }
}
