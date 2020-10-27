package com.dataee.tutorserver.userserver.service.impl;

import com.dataee.tutorserver.userserver.dao.WebLinkMapper;
import com.dataee.tutorserver.userserver.service.IWebLinkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author JinYue
 * @CreateDate 2019/6/18 23:55
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class WebLinkServiceImpl implements IWebLinkService {
    private Logger logger = LoggerFactory.getLogger(WebLinkServiceImpl.class);

    @Autowired
    private WebLinkMapper webLinkMapper;

    @Override
    public String getWebLink(String keyword) {
        String address = webLinkMapper.queryWebLink(keyword);
        return address;
    }
}
