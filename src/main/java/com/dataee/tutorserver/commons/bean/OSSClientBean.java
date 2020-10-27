package com.dataee.tutorserver.commons.bean;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.CredentialsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * OSS服务配置信息
 *
 * @author JinYue
 * @CreateDate 2019/5/2 22:14
 */
@Configuration
@SessionScope
public class OSSClientBean {
    @Value("${com.aliyun.oss.endpoint}")
    private String endpoint;
    @Autowired
    private ClientConfiguration clientConfig;
    @Autowired
    private CredentialsProvider credentials;
    private OSSClient client;

    private void createOSSClient() {
        client = new OSSClient(endpoint, credentials, clientConfig);
    }

    @Bean(name = "ossClient")
    @SessionScope
    public OSSClient ossClient() {
        return client;
    }

    @PostConstruct
    public void initClient() {
        this.createOSSClient();
    }

    @PreDestroy
    public void shutdownClient() {
        this.shutdown();
    }

    public void shutdown() {
        if (client != null) {
            client.shutdown();
        }
    }
}
