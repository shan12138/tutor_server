package com.dataee.tutorserver.userserver.service;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.entity.ContractPdf;

import java.util.List;

/**
 * 合同查看业务
 *
 * @author JinYue
 * @CreateDate 2019/5/6 1:36
 */
public interface IContractsService {
    /**
     * 获取资源路径
     *
     * @param id
     */
    NewPageInfo getResourceAddress(int id, Page page) throws BaseServiceException;

    /**
     * 获取资源路径
     */
    String getResourceAddress(int contractId) throws BaseServiceException;


    String getParentResourceAddress(int contractId) throws BaseServiceException;
    /**
     * 获取合同列表
     *
     * @param personId
     * @return
     */
    NewPageInfo<ContractPdf> getContractList(Integer personId, String userId, Page page) throws BaseServiceException;


    ContractPdf getContractPdf(Integer contractId);

}
