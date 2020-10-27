package com.dataee.tutorserver.commons.commonservice.impl;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.commonservice.IZipService;
import com.dataee.tutorserver.commons.errorInfoenum.ServiceExceptionsEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author JinYue
 * @CreateDate 2019/6/30 16:13
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ZipServiceImpl implements IZipService {
    private Logger logger = LoggerFactory.getLogger(ZipServiceImpl.class);
    private final String TEMP_POINT = "/download";
    private final String PATH_POINT = "/home/tutor/tutor-platform";
    private final String SUFFIX = ".zip";

    @Override
    public String filesToPackage(List<String> files, String name) {
        String desPackagePath = PATH_POINT + TEMP_POINT + '/' + name;
        //创建指定的文件夹
        createPackage(desPackagePath);
        //复制文件到改文件夹中
        files.forEach(file -> copyFile(desPackagePath, file));
        return TEMP_POINT + '/' + name;
    }

    @Override
    public String zipPackages(List<String> packages, String name) throws BaseServiceException {
        //创建指定的文件夹
        createPackage(name + SUFFIX);
        //压缩改文件
        zipMultiFile(packages, PATH_POINT + TEMP_POINT + '/' + name);
        //返回地址
        return TEMP_POINT + '/' + name;
    }


    @Override
    public String zipPackage(String zipFileName, List<String> files) throws BaseServiceException {
        String zipPath = TEMP_POINT + File.separator + zipFileName + SUFFIX;
        zipFile(files, PATH_POINT + zipPath);
        return zipPath;
    }

    /**
     * 创建临时文件包
     *
     * @param packageName
     */
    private void createPackage(String packageName) {
        File newPackage = new File(PATH_POINT + File.separator + packageName);
        if (newPackage.exists() && newPackage.isDirectory()) {
            logger.info("file exists！");
            //删除改文件夹
            newPackage.delete();
            return;
        }
        newPackage.mkdir();
    }

    /**
     * 复制文件
     *
     * @param desPath
     * @param file
     */
    private void copyFile(final String desPath, String file) {
        String fileName = new File(PATH_POINT + '/' + file).getName();
        try (FileChannel inChannel = new FileInputStream(PATH_POINT + '/' + file).getChannel();
             FileChannel outChannel = new FileOutputStream(desPath + fileName).getChannel()) {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void zipFile(List<String> files, String zipPath) throws BaseServiceException {
        File zipFile = new File(zipPath);
        if (zipFile.exists()) {
            zipFile.delete();
        }
        try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile))) {
            // 要被压缩的文件夹
            for (String filePath : files) {
                logger.debug("---------------------------zipFile", filePath);
                writeFile(zipOut, new File(PATH_POINT + File.separator + filePath), "");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BaseServiceException(ServiceExceptionsEnum.FILE_ZIP_FAIL);
        }
    }

    /**
     * 压缩多级目录的文件
     *
     * @param filepathList 文件所在目录
     * @param zippath      压缩后zip文件名称
     */
    private void zipMultiFile(List<String> filepathList, String zippath) throws BaseServiceException {
        File zipFile = new File(zippath);
        try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile))) {
            // 要被压缩的文件夹
            File file = null;
            for (String filepath : filepathList) {
                file = new File(filepath);
                if (file.isDirectory()) {
                    File[] files = file.listFiles();
                    for (File fileSec : files) {
                        recursionZip(zipOut, fileSec, file.getName() + File.separator);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BaseServiceException(ServiceExceptionsEnum.FILE_ZIP_FAIL);
        }
    }

    private void recursionZip(ZipOutputStream zipOut, File file, String baseDir) throws BaseServiceException {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File fileSec : files) {
                recursionZip(zipOut, fileSec, baseDir + file.getName() + File.separator);
            }
        } else {
            writeFile(zipOut, file, baseDir);
        }
    }

    /**
     * 将文件写入压缩文件中
     */
    private void writeFile(ZipOutputStream zipOut, File file, String baseDir) throws BaseServiceException {
        byte[] buf = new byte[1024];
        try (InputStream input = new FileInputStream(file)) {
            zipOut.putNextEntry(new ZipEntry(baseDir + file.getName()));
            int len = 0;
            while ((len = input.read(buf)) != -1) {
                zipOut.write(buf, 0, len);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BaseServiceException(ServiceExceptionsEnum.FILE_IO_FAIL);
        }
    }
}
