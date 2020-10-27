package com.dataee.tutorserver.tutoradminserver.filemanage.service.impl;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.commonservice.IOSSService;
import com.dataee.tutorserver.commons.errorInfoenum.ServiceExceptionsEnum;
import com.dataee.tutorserver.entity.ContractPdf;
import com.dataee.tutorserver.tutoradminserver.filemanage.dao.ContractMapper;
import com.dataee.tutorserver.tutoradminserver.filemanage.service.IContractService;
import com.dataee.tutorserver.userserver.dao.ContractsMapper;
import com.dataee.tutorserver.utils.PageInfoUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author JinYue
 * @CreateDate 2019/5/5 22:46
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ContractServiceImpl implements IContractService {
    private final Logger logger = LoggerFactory.getLogger(ContractServiceImpl.class);
    @Autowired
    private ContractMapper contractMapper;
    @Autowired
    private ContractsMapper contractsMapper;
    @Autowired
    private IOSSService ossService;

    @Override
    public ContractPdf getResourceAddress(int contractId) throws BaseServiceException {
        ContractPdf contractPdf = contractMapper.selectSourceAddressByContractNameAndPersonId(contractId);
        if (contractPdf.getPdfAddress() == null) {
            throw new BaseServiceException(ServiceExceptionsEnum.FILE_NOT_EXIT);
        } else {
            return contractPdf;
        }
    }

    @Override
    public void deleteContract(int contractId) throws BaseServiceException {
        //删除数据资源
        ContractPdf contractPdf = contractMapper.selectSourceAddressByContractNameAndPersonId(contractId);
        if (contractPdf==null&&contractPdf.getPdfAddress() == null) {
            return;
        }
        ossService.deleteFile(contractPdf.getPdfAddress());
        ossService.deleteFile(contractPdf.getSignedPdfAddress());
        List<String> imageAddresses = contractsMapper.selectResourceAddressByFilenameAndPersonId(contractId);
        if (imageAddresses == null) {
            return;
        }
        //更改为本地挂载形式的文件夹的删除
        ossService.deleteFiles(imageAddresses);
        //删除数据记录
        //删除PDF合同和图片的合同
        contractMapper.deleteContractPdfAndImage(contractId);
    }

    @Override
    public NewPageInfo getContracts(String role, String personId, int pageNum, int pageSize) {
        PageHelper.startPage(1, 2);
        List<ContractPdf> contracts = contractMapper.queryContractsByPerson(role, personId);
        PageInfo pageInfo = new PageInfo(contracts);
        NewPageInfo<ContractPdf> newPageInfo = PageInfoUtil.read(pageInfo);
        return newPageInfo;
    }
}
