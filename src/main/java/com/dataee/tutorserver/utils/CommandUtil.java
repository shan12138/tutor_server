package com.dataee.tutorserver.utils;

import com.dataee.tutorserver.commons.commonservice.impl.OSSServiceImpl;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.io.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@Component
public class CommandUtil {
    // TODO: Logger
    private final Logger logger = LoggerFactory.getLogger(CommandUtil.class);
    private String readContentFromInputStream(InputStream stream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(stream,"utf-8");
        BufferedReader br = new BufferedReader(inputStreamReader);
        StringBuilder builder = new StringBuilder();

        String line = "";
        while ((line = br.readLine()) != null) {
            builder.append(line +"\n");
            System.out.println(line);
        }

        return builder.toString();
    }

    public void execCommand(String cmd){
        try{
            System.out.println("------------------------------------");
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(cmd,null,null);

            int returnCode = proc.waitFor();
            // 0成功，没有错误
            // 非0，失败

            InputStream stderr = proc.getErrorStream();
            InputStream stdout = proc.getInputStream();

            String errorOutput = readContentFromInputStream(stderr);
            String standardOutput = readContentFromInputStream(stdout);

            logger.info(standardOutput);
            logger.info(errorOutput);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public   MultipartFile getMulFileByPath(String picPath) {
        FileItem fileItem = createFileItem(picPath);
        MultipartFile mfile = new CommonsMultipartFile(fileItem);
        return mfile;
    }

    private  FileItem createFileItem(String filePath)
    {
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        String textFieldName = "textField";
        int num = filePath.lastIndexOf(".");
        String extFile = filePath.substring(num);
        FileItem item = factory.createItem(textFieldName, "text/plain", true,
                "MyFileName" + extFile);
        File newfile = new File(filePath);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        try
        {
            FileInputStream fis = new FileInputStream(newfile);
            OutputStream os = item.getOutputStream();
            while ((bytesRead = fis.read(buffer, 0, 8192))
                    != -1)
            {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            fis.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return item;
    }




}
