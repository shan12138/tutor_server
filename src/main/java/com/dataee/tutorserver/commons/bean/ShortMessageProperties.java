package com.dataee.tutorserver.commons.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 短信必要的配置信息常量
 *
 * @author JinYue
 * @CreateDate 2019/4/27 11:30
 */
@Component
@PropertySource(value = "classpath:application.properties", encoding = "UTF-8")
@Data
public class ShortMessageProperties {
    @Value("${com.aliyun.accessKeyId}")
    private String accessKeyId;
    @Value("${com.aliyun.accessKeySecret}")
    private String accessKeySecret;
    @Value("${com.aliyun.shortMessage.domain}")
    private String domain;
    @Value("${com.aliyun.shortMessage.version}")
    private String version;
    @Value("${com.aliyun.shortMessage.action}")
    private String action;
    @Value("${com.aliyun.regionId}")
    private String regionId;
    @Value("${com.aliyun.shortMessage.signName}")
    private String signName;
    @Value("${com.aliyun.shortMessage.templateCode}")
    private String templateCode;

}
