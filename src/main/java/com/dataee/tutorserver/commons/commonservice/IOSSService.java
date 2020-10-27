package com.dataee.tutorserver.commons.commonservice;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.List;

/**
 * 文件操作服务
 *
 * @author JinYue
 * @CreateDate 2019/5/3 13:33
 */
public interface IOSSService {
    /**
     * 文件上传
     *
     * @param
     * @param fileName
     */
    void uploadFile(String fileName, /*MultipartFile file*/String localFileName) throws BaseServiceException;

    /**
     * 文件下载
     *
     * @param fileName
     */
    void downloadFile(String fileName);

    /**
     * 删除指定文件
     *
     * @param fileName
     */
    void deleteFile(String fileName) throws BaseServiceException;


    /**
     * 批量删除文件
     *
     * @param addressList
     */
    void deleteFiles(List<String> addressList) throws BaseServiceException;

    /**
     * 获取指定文件的加签URL
     *
     * @param fileName
     */
    URL getURL(String fileName) throws BaseServiceException;

    /**
     * 获取错题集资源地址
     *
     * @param resourceList
     * @return
     */
    List<String> getURLList(List<String> resourceList) throws BaseServiceException;
}
