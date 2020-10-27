package com.dataee.tutorserver.utils;

import java.util.UUID;

/**
 * 生成资源文件名称保证文件名称的唯一性
 * 使用UUID
 *
 * @author JinYue
 * @CreateDate 2019/5/5 22:15
 */
public class FilenameProducerUtil {
    public static String createNewName() {
        String newName = UUID.randomUUID().toString();
        newName.replace("_", "");
        //POP协议要求
        newName = replaceSpecialChar(newName);
        return newName;
    }

    public static String replaceSpecialChar(String name) {
        name.replace("+", "%20");
        name.replace("*", "%2A");
        name.replace("%7E", "~");
        return name;
    }
}
