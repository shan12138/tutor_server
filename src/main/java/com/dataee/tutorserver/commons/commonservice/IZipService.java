package com.dataee.tutorserver.commons.commonservice;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;

import java.util.List;

/**
 * 压缩文件
 *
 * @author JinYue
 * @CreateDate 2019/6/30 16:11
 */
public interface IZipService {
    /**
     * 对指定的文件进行打包
     *
     * @param files
     * @param name
     * @return
     */
    String filesToPackage(List<String> files, String name);

    /**
     * 对指定的文件包进行打包
     *
     * @param packages
     * @param name
     * @return
     */
    String zipPackages(List<String> packages, String name) throws BaseServiceException;

    /**
     * 压缩单个文件
     *
     * @param zipFileName
     * @param files
     * @return
     */
    String zipPackage(String zipFileName, List<String> files) throws BaseServiceException;
}
