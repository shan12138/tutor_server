package com.dataee.tutorserver.commons.commonservice;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.exceptions.IllegalParameterException;
import com.dataee.tutorserver.commons.exceptions.SQLOperationException;

import java.util.List;

/**
 * 保存PDF的通用接口
 *
 * @author JinYue
 * @CreateDate 2019/6/9 22:06
 */
public interface ISavePDFService<T> {
    /**
     * 存储文件
     * 并返回主键id
     *
     * @param file
     */
    Integer saveFile(T file) throws BaseServiceException, IllegalParameterException;

    /**
     * 保存pdf并返回主键ID
     *
     * @param fileInfo
     * @return
     */
    Integer saveFilePDF(T fileInfo) throws BaseServiceException;

    /**
     * 保存文件图片
     *
     * @param pdfId
     * @param paths
     */
    void saveFileImage(Integer pdfId, List<String> paths) throws SQLOperationException;
}
