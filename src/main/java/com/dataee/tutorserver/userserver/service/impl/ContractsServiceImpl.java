package com.dataee.tutorserver.userserver.service.impl;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.commons.errorInfoenum.ServiceExceptionsEnum;
import com.dataee.tutorserver.commons.exceptions.IllegalParameterException;
import com.dataee.tutorserver.commons.exceptions.SQLOperationException;
import com.dataee.tutorserver.entity.ContractPdf;
import com.dataee.tutorserver.userserver.service.IContractsService;
import com.dataee.tutorserver.userserver.dao.ContractsMapper;
import com.dataee.tutorserver.userserver.service.IUserService;
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
 * @CreateDate 2019/5/6 1:38
 */
@Service
@Transactional(readOnly = true, rollbackFor = SQLOperationException.class)
public class ContractsServiceImpl implements IContractsService {
    private final Logger logger = LoggerFactory.getLogger(ContractsServiceImpl.class);
    @Autowired
    private ContractsMapper contractsMapper;

    @Autowired
    private IUserService userService;

    public void setContractsMapper(ContractsMapper contractsMapper) {
        this.contractsMapper = contractsMapper;
    }

    @Override
    public NewPageInfo getResourceAddress(int id, Page page) throws BaseServiceException {
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<String> resourceAddressList = contractsMapper.selectResourceAddressByFilenameAndPersonId(id);
        if (resourceAddressList == null || resourceAddressList.size() == 0) {
            throw new BaseServiceException(ServiceExceptionsEnum.FILE_NOT_EXIT);
        } else {
            PageInfo resource = new PageInfo(resourceAddressList);
            NewPageInfo resourceAddress = PageInfoUtil.read(resource);
            return resourceAddress;
        }
    }

    @Override
    public String getResourceAddress(int contractId) throws BaseServiceException {
        String resourceAddress = contractsMapper.selectSourceAddressByContractNameAndPersonId(contractId);
        if (resourceAddress == null) {
            throw new BaseServiceException(ServiceExceptionsEnum.FILE_NOT_EXIT);
        } else {
            return resourceAddress;
        }
    }

    @Override
    public String getParentResourceAddress(int contractId) throws BaseServiceException {
        String resourceAddress = contractsMapper.selectSourceAddress(contractId);
        if (resourceAddress == null) {
            throw new BaseServiceException(ServiceExceptionsEnum.FILE_NOT_EXIT);
        } else {
            return resourceAddress;
        }
    }

    /**
     * mapper用了many的映射可能分页不能成功
     *
     * @param personId
     * @param page
     * @return
     */
    @Override
    public NewPageInfo<ContractPdf> getContractList(Integer personId, String userId, Page page) throws BaseServiceException {
        Integer personRole = userService.getPersonRoleId(userId);
        if (personRole == null) {
            throw new BaseServiceException(ServiceExceptionsEnum.USER_NOT_EXIST);
        }
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<ContractPdf> contractPdfList = contractsMapper.queryContractListByPersonId(personId, personRole);
        PageInfo pageInfo = new PageInfo(contractPdfList);
        NewPageInfo newPageInfo = PageInfoUtil.read(pageInfo);
        return newPageInfo;
    }

    @Override
    public ContractPdf getContractPdf(Integer contractId) {
        return contractsMapper.getContractPdf(contractId);
    }
}
