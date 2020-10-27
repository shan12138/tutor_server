package com.dataee.tutorserver.commons.commonservice.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.commonservice.IOSSService;
import com.dataee.tutorserver.commons.errorInfoenum.ServiceExceptionsEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 实现对静态资源的上传和下载服务
 * 主要应用阿里云的OSS
 *
 * @author JinYue
 * @CreateDate 2019/5/2 22:12
 */
@Service
public class OSSServiceImpl implements IOSSService {
    private final Logger logger = LoggerFactory.getLogger(OSSServiceImpl.class);
    @Value("${com.aliyun.oss.bucketName}")
    private String bucketName;
    @Autowired
    private OSSClient ossClient;

    @Override
    public void uploadFile(@NotBlank String fileName, @NotBlank /*MultipartFile file*/String localFileName ) throws ClientException, BaseServiceException {
        ObjectMetadata meta = new ObjectMetadata();
        File srcFile =new File(localFileName);
        //meta.setContentLength(file.getSize());
        meta.addUserMetadata("filename", fileName);
        try {
            InputStream inputStream = new FileInputStream(srcFile);
          //  InputStream inputStream = file.getInputStream();
            //生成加签参数时对“+”等需要从新编码
            PutObjectResult result = ossClient.putObject(bucketName, fileName, inputStream, meta);
            logger.info("result: {}", result.getETag());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new BaseServiceException(ServiceExceptionsEnum.FILE_IO_EXCEPTION);
        } catch (OSSException osse) {
            logger.error(osse.getMessage(), osse);
            throw new BaseServiceException(ServiceExceptionsEnum.OBJECT_OPERATE_EXCEPTION);
        }
    }

    /**
     * 下载文件
     *
     * @param fileName
     * @throws ClientException
     */
    @Override
    public void downloadFile(String fileName) throws ClientException {
    }


    @Override
    public void deleteFile(String fileName) throws ClientException, BaseServiceException {
        try {
            ossClient.deleteObject(bucketName, fileName);
        } catch (OSSException osse) {
            logger.error(osse.getMessage(), osse);
            throw new BaseServiceException(ServiceExceptionsEnum.OBJECT_OPERATE_EXCEPTION);
        }
    }

    @Override
    public void deleteFiles(List<String> addressList) throws BaseServiceException {
        try {
            DeleteObjectsResult result = ossClient.deleteObjects(new DeleteObjectsRequest(bucketName).withKeys(addressList));
            List<String> deletedFiles = result.getDeletedObjects();
            if (deletedFiles.size() != addressList.size()) {
                logger.debug("--deleteFiles--:计划删除数据量：" + addressList.size() + "但实际删除文件:" + addressList.size());
            }
        } catch (OSSException osse) {
            logger.error(osse.getMessage(), osse);
            throw new BaseServiceException(ServiceExceptionsEnum.OBJECT_OPERATE_EXCEPTION);
        }
    }

    @Override
    public URL getURL(@NotBlank(message = "文件不存在") String fileName) throws ClientException, BaseServiceException {
        if (fileName == null || fileName.equals("")) {
            return null;
        }
        try {
            boolean found = ossClient.doesObjectExist(bucketName, fileName);
            if (!found) {
                logger.warn(ServiceExceptionsEnum.FILE_NOT_EXIT.toString());
                throw new BaseServiceException(ServiceExceptionsEnum.FILE_NOT_EXIT);
            }
            // 设置URL过期时间为1小时。
            Date expiration = new Date(System.currentTimeMillis() + 3600 * 1000);
            // 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
            URL url = ossClient.generatePresignedUrl(bucketName, fileName, expiration);
            return url;
        } catch (OSSException osse) {
            logger.error(osse.getMessage(), osse);
            throw new BaseServiceException(ServiceExceptionsEnum.FILE_NOT_EXIT);
        }
    }

    @Override
    public List<String> getURLList(List<String> resourceList) throws BaseServiceException {
        if (resourceList == null) {
            return new ArrayList<>();
        }
        List<String> urlList = new ArrayList<>();
        for (String resource : resourceList) {
            urlList.add(getURL(resource).toString());
        }
        return urlList;
    }
}
