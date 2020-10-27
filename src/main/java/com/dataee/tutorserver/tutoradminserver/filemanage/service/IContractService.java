package com.dataee.tutorserver.tutoradminserver.filemanage.service;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.entity.ContractPdf;

/**
 * 对上传的文件进行保存或者查询
 *
 * @author JinYue
 * @CreateDate 2019/5/5 22:40
 */
public interface IContractService {

    /**
     * 获取文件资源路径
     *
     * @param contractId
     * @return
     */
    ContractPdf getResourceAddress(int contractId) throws BaseServiceException;

    /**
     * 删除合同
     * 可重构
     *
     * @param contractId
     */
    void deleteContract(int contractId) throws BaseServiceException;

    /**
     * 获取老师或家长的错题集
     *
     * @param personId
     * @return
     */
    NewPageInfo getContracts(String role, String personId, int pageNum, int pageSize);


}
