package com.dataee.tutorserver.commons.commonservice.impl;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.commonservice.IPDFConvertToImageService;
import com.dataee.tutorserver.commons.errorInfoenum.ServiceExceptionsEnum;
import com.dataee.tutorserver.commons.exceptions.IllegalParameterException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author JinYue
 * @CreateDate 2019/6/7 20:36
 */
@Service
public class PDFConvertToImageServiceImpl implements IPDFConvertToImageService {
    private Logger logger = LoggerFactory.getLogger(PDFConvertToImageServiceImpl.class);
    private final int DPI = 96;
    private final String SUFFIX = "png";
    private final String DIR_SUFFIX = "image";
    private final String PATH_POINT = "/home/tutor/tutor-platform";

    @Override
    public List<String> pdfRendering(String filePath) throws IllegalParameterException, BaseServiceException {
        //文件路径分解
        String[] paths = this.separatePath(filePath);
        //    获取文件名(无后缀)
        String filename = paths[1];
        //    生成文件夹名称
        String fileDir = paths[0] + '/' + filename + '_' + DIR_SUFFIX;
        List<String> filepaths = new ArrayList<>();
        //写在括号里可以AutoClose
        try (PDDocument pdDocument = PDDocument.load(new File(PATH_POINT + '/' + filePath))) {
            PDFRenderer pdfRenderer = new PDFRenderer(pdDocument);
            int pageCounter = 0;
            //pdf页面转化
            for (PDPage pdPage : pdDocument.getPages()) {
                BufferedImage bim = pdfRenderer.renderImageWithDPI(pageCounter, DPI, ImageType.RGB);
                // 写入oss文件系统(从1开始)
                pageCounter++;
            String imagePath = saveImage(bim, SUFFIX, fileDir, filename + '_' + pageCounter);
            filepaths.add(imagePath);
        }
        return filepaths;
        } catch (InvalidPasswordException e) {
            logger.error(e.getMessage(), e);
            throw new BaseServiceException(ServiceExceptionsEnum.OBJECT_OPERATE_EXCEPTION);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new BaseServiceException(ServiceExceptionsEnum.OBJECT_OPERATE_EXCEPTION);
        }
    }

    /**
     * 存储图片到oss文件
     * <p>
     * 如果文件路径中存在未创建的文件夹则ImageIO会报空指针
     * File imageFile = new File(PATH_POINT + fullFileName);
     * boolean isSuccess = ImageIO.write(bim, suffix, imageFile);
     *
     * @param bim      图片流
     * @param suffix   存储的图片类型
     * @param filename PDF文件名
     * @return
     */
    private String saveImage(BufferedImage bim, String suffix, String fileDir, String filename) throws BaseServiceException {
        //创建存储图片的文件夹
        this.makeDir(fileDir);
        //存储图片文件名
        String fullFileName = fileDir + '/' + filename + '.' + suffix;
        logger.info("fullFIleName：{}", fullFileName);
        try {
            boolean isSuccess = ImageIOUtil.writeImage(bim, PATH_POINT + fullFileName, DPI);
            if (isSuccess) {
                return fullFileName.substring(1);
            } else {
                throw new BaseServiceException(ServiceExceptionsEnum.CLIENT_OPERATE_EXCEPTION);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new BaseServiceException(ServiceExceptionsEnum.CLIENT_OPERATE_EXCEPTION);
        }
    }


    /**
     * 获取文件名称
     *
     * @param path
     * @return
     * @throws IllegalParameterException
     */
    private String[] separatePath(String path) throws IllegalParameterException {
        File document = new File('/' + path);
        String fileDir = document.getParent();
        String name = document.getName();
        logger.info("fileDir:{}", fileDir);
        try {
            String filename = name.substring(0, name.lastIndexOf("."));
            if (fileDir == null) {
                fileDir = "";
            }
            String[] paths = {fileDir, filename};
            return paths;
        } catch (StringIndexOutOfBoundsException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalParameterException("非法文件名");
        }

    }


    /**
     * 获取完整的文件目录
     *
     * @param filePath
     * @return
     */
    private String getFileDir(String filePath) {
        try {
            String fileDir = filePath.substring(0, filePath.lastIndexOf("/") + 1);
            logger.info("fileDir: {}", fileDir);
            return fileDir;
        } catch (StringIndexOutOfBoundsException se) {
            return "";
        }
    }

    /**
     * 生成文件夹
     *
     * @param fileDir
     */
    private void makeDir(String fileDir) {
        File dir = new File(PATH_POINT + fileDir);
        if (dir.exists()) {
            if (dir.isDirectory()) {
                logger.info("file exists！");
                return;
            }
        }
        dir.mkdir();
    }
}
