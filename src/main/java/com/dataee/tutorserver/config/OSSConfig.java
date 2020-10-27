package com.dataee.tutorserver.config;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.common.comm.Protocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author JinYue
 * @CreateDate 2019/5/6 20:07
 */
@Configuration
public class OSSConfig {
    @Value("${com.aliyun.accessKeyId}")
    private String accessKeyId;
    @Value("${com.aliyun.accessKeySecret}")
    private String accessKeySecret;
    @Value("${com.aliyun.oss.userAgent}")
    private String userAgent;

    @Bean(name = "clientConfig")
    public ClientConfiguration clientConfig() {
        ClientConfiguration conf = new ClientConfiguration();
        // 设置OSSClient允许打开的最大HTTP连接数，默认为1024个。
        conf.setMaxConnections(10);
        // 设置Socket层传输数据的超时时间，默认为50000毫秒。
        conf.setSocketTimeout(10000);
        // 设置建立连接的超时时间，默认为50000毫秒。
        conf.setConnectionTimeout(10000);
        // 设置连接空闲超时时间。超时则关闭连接，默认为60000毫秒。
        conf.setIdleConnectionTime(10000);
        // 设置连接OSS所使用的协议（HTTP/HTTPS），默认为HTTP。
        conf.setProtocol(Protocol.HTTP);
        // 设置用户代理，指HTTP的User-Agent头，默认为aliyun-sdk-java。
        conf.setUserAgent(userAgent);
        return conf;
    }

    @Bean(name = "credentials")
    public CredentialsProvider credentials() {
        CredentialsProvider credentialsProvider = new DefaultCredentialProvider(accessKeyId, accessKeySecret);
        return credentialsProvider;
    }
}
