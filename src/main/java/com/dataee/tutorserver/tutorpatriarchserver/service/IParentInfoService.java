package com.dataee.tutorserver.tutorpatriarchserver.service;

import com.dataee.tutorserver.commons.exceptions.SQLOperationException;

/**
 * @author JinYue
 * @CreateDate 2019/6/10 2:07
 */
public interface IParentInfoService {
    /**
     * 保存家长头像
     *
     * @param path
     */
    void saveHeadportrait(Integer parentId, String path) throws SQLOperationException;

    /**
     * 获取家长的头像
     *
     * @param parentId
     * @return
     */
    String getHeadportrait(Integer parentId);
}
