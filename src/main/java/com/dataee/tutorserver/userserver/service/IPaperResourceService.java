package com.dataee.tutorserver.userserver.service;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;

import java.util.List;

/**
 * @author JinYue
 * @CreateDate 2019/6/19 17:09
 */
public interface IPaperResourceService {
    /**
     * 获取地址链接
     *
     * @param address
     * @return
     */
    String getPaperImageLinkByAddress(String address) throws BaseServiceException;

    /**
     * 获取地址的列表
     *
     * @param addresses
     * @return
     */
    List<String> getPaperImageLinkListByAddresses(List<String> addresses) throws BaseServiceException;
}
