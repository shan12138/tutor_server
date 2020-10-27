package com.dataee.tutorserver.utils;

import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.errorInfoenum.ControllerExceptionEnum;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLConnection;

/**
 * 判断文件类型工具
 *
 * @author JinYue
 * @CreateDate 2019/5/31 20:25
 */
public class FileTypeUtil {
    private static final String XLS = "xls";
    private static final String CSV = "csv";
    private static final String XLSX = "xlsx";

    public static String getSuffix(final String fullName) throws BaseControllerException {
        String suffix = fullName.substring(fullName.lastIndexOf('.') + 1);
        if (suffix != null) {
            return suffix;
        } else {
            throw new BaseControllerException(ControllerExceptionEnum.FILE_ERROR);
        }
    }

    public static boolean isExcel(String suffix) {
        boolean excel = true;
        //获取文件后缀名
        switch (suffix) {
            case XLS:
                break;
            case CSV:
                break;
            case XLSX:
                break;
            default:
                excel = false;
        }
        return excel;
    }

    /**
     * 当前方法判断文件类型不包含excel
     *
     * @param fileName
     * @return
     */
    public static String identifyFileType(final String fileName) {
        String fileType;
        try {
            fileType = URLConnection.guessContentTypeFromStream(new FileInputStream(new File(fileName)));
            System.out.println("-------------------------" + fileType);
        } catch (IOException ex) {
            System.out.println("ERROR: Unable to process file type for " + fileName + " - " + ex);
            fileType = "null";
        }
        return fileType;
    }
}
