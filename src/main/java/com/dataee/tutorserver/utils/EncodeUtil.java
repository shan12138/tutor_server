package com.dataee.tutorserver.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * 明文密码加密
 *
 * @author JinYue
 */
public class EncodeUtil {
    private static final String algorithmName = "Md5";
    private static final int hashIterations = 2;

    /**
     * 通过MD5和加盐将明文加密
     *
     * @param plaintext 明文密码
     * @param saltVal   盐值
     * @return
     */
    public static String encodePassword(String plaintext, String saltVal) {
        ByteSource salt = getSalt(saltVal);
        SimpleHash simpleHash = new SimpleHash(algorithmName, plaintext, salt, hashIterations);
        return simpleHash.toString();
    }

    /**
     * 将String型的盐转换成ByteSource类型的盐
     *
     * @param saltVal
     * @return
     */
    public static ByteSource getSalt(String saltVal) {
        ByteSource salt = ByteSource.Util.bytes(saltVal);
        return salt;
    }
}
