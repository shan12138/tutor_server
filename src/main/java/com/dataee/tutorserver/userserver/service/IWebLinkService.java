package com.dataee.tutorserver.userserver.service;

/**
 * @author JinYue
 * @CreateDate 2019/6/18 23:54
 */
public interface IWebLinkService {
    /**
     * 根据关键词获取网址
     *
     * @param keyword
     * @return
     */
    String getWebLink(String keyword);
}
