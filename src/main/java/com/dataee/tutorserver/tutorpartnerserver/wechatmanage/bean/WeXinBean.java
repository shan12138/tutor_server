package com.dataee.tutorserver.tutorpartnerserver.wechatmanage.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/13 9:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeXinBean {
    private String encryptedData;
    private String iv;
    private String code;
}
