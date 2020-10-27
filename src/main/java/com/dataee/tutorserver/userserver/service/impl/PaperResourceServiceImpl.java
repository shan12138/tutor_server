package com.dataee.tutorserver.userserver.service.impl;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.commonservice.IOSSService;
import com.dataee.tutorserver.userserver.service.IPaperResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.List;

/**
 * @author JinYue
 * @CreateDate 2019/6/19 17:13
 */
@Service
public class PaperResourceServiceImpl implements IPaperResourceService {
    private Logger logger = LoggerFactory.getLogger(PaperResourceServiceImpl.class);

    @Autowired
    private IOSSService ossService;

    @Override
    public List<String> getPaperImageLinkListByAddresses(List<String> addresses) throws BaseServiceException {
        List<String> urlStrList = ossService.getURLList(addresses);
        return urlStrList;
    }

    @Override
    public String getPaperImageLinkByAddress(String address) throws BaseServiceException {
        URL url = ossService.getURL(address);
        return url.toString();
    }
}
