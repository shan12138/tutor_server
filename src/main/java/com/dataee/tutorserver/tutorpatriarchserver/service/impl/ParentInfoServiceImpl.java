package com.dataee.tutorserver.tutorpatriarchserver.service.impl;

import com.dataee.tutorserver.commons.exceptions.SQLOperationException;
import com.dataee.tutorserver.tutorpatriarchserver.dao.ParentInfoMapper;
import com.dataee.tutorserver.tutorpatriarchserver.service.IParentInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author JinYue
 * @CreateDate 2019/6/10 2:07
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ParentInfoServiceImpl implements IParentInfoService {
    private Logger logger = LoggerFactory.getLogger(ParentInfoServiceImpl.class);
    @Autowired
    private ParentInfoMapper parentInfoMapper;


    @Override
    public void saveHeadportrait(Integer parentId, String path) throws SQLOperationException {
        int count = parentInfoMapper.updateParentHeadportraitByPersonId(parentId, path);
        if (count != 1) {
            logger.error("saveHeadportrait:{}", "头像保存失败");
            throw new SQLOperationException();
        }
    }

    @Override
    public String getHeadportrait(Integer parentId) {
        String address = parentInfoMapper.queryHeadportrait(parentId);
        return address;
    }
}
