package com.dataee.tutorserver.config;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author JinYue
 * @CreateDate 2019/5/9 23:38
 */
@Configuration
public class STSConfig {
    @Value("${com.aliyun.accessKeyId}")
    private String accessKeyId;
    @Value("${com.aliyun.accessKeySecret}")
    private String accessKeySecret;
    @Value("${com.aliyun.sts.endpoint}")
    private String endpoint;
    @Value("${com.aliyun.sts.arn}")
    private String arn;

    @Bean("stsClient")
    public DefaultAcsClient stsClient() {
        DefaultProfile.addEndpoint("", "Sts", endpoint);
        IClientProfile profile = DefaultProfile.getProfile("", accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }

    @Bean("stsRequest")
    public AssumeRoleRequest stsRequest() {
        AssumeRoleRequest request = new AssumeRoleRequest();
        request.setDurationSeconds(3600L);
        request.setRoleArn(arn);
        request.setSysMethod(MethodType.POST);
        return request;
    }
}
