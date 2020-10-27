package com.dataee.tutorserver.utils;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Sheet;
import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.bean.ExcelListener;
import com.dataee.tutorserver.commons.errorInfoenum.ControllerExceptionEnum;
import org.apache.poi.ss.formula.functions.T;

import java.io.*;
import java.util.List;
import java.util.UUID;

/**
 * 解析Excel文件工具
 *
 * @author JinYue
 * @CreateDate 2019/5/30 22:29
 */
public class ExcelUtil {

    //private static final String DIR = "C:\\Users\\JinYue\\Desktop\\临时存放文件夹\\";
    //private static final String tempFile = "C:\\Users\\JinYue\\Desktop\\临时存放文件夹\\telephone3.xlsx";

    /**
     * 带java模型分析
     *
     * @param sheetCount
     * @param headLineMun
     */
    public static List analysisExcel(Class T, InputStream inputStream, final Integer sheetCount, final Integer headLineMun) throws BaseControllerException {
        try {
            ExcelListener listener = new ExcelListener();
            EasyExcelFactory.readBySax(inputStream, new Sheet(1, 1, T), listener);
            return listener.getDatas();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new BaseControllerException(ControllerExceptionEnum.ANALYSIS_ERROR);
        } finally {
            try {
                inputStream.close();
            } catch (IOException ioe) {
                throw new BaseControllerException(ControllerExceptionEnum.FILE_ERROR);
            }
        }
    }


    /**
     * 创建临时的文件
     *
     * @param inputStream
     * @param type
     * @return
     * @throws Exception
     */
    String createTempExcel(InputStream inputStream, String type) throws Exception {
        long time = System.currentTimeMillis();
        String filename = String.valueOf(time) + '.' + type;
        File excel = new File(filename);
        try (OutputStream outputStream = new FileOutputStream(filename)) {
            byte[] bytes = new byte[1024];
            int byteCount = 0;
            while ((byteCount = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes);
                outputStream.flush();
            }
            return filename;
        } catch (FileNotFoundException fe) {
            throw new Exception();

        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception();
        }
    }
}
