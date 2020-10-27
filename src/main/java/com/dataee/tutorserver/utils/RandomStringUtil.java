package com.dataee.tutorserver.utils;

import java.util.Random;

/**
 * 随机字符串工具类
 * @Author ChenShanShan
 * @CreateDate 2019/11/12 10:26
 */
public class RandomStringUtil {
    public static String getRandomString() {
        String val = "";
        Random random = new Random();
        // length为几位密码
        for (int i = 0; i < 6; i++) {
            // 判断生成数字还是字母(字母有大小写区别)
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            // 输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                // 输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (random.nextInt(26) + temp);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }
}
