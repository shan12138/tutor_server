package com.dataee.tutorserver.commons.commonservice;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.exceptions.IllegalParameterException;

import java.util.List;

/**
 * PDF转成图片
 *
 * @author JinYue
 * @CreateDate 2019/6/7 20:35
 */
public interface IPDFConvertToImageService {
    /**
     * pdf转成图片返回图片存储路径的地址
     *
     * @param filePath
     * @return
     */
    List<String> pdfRendering(String filePath) throws IllegalParameterException, BaseServiceException;
}
