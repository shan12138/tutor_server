package com.dataee.tutorserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * 对异步线程的线程池的配置
 *
 * @author JinYue
 * @CreateDate 2019/6/16 11:09
 */
@Configuration
@EnableAsync
public class AnsynConfig {
    private final Integer CORE_POOL_SIZE = 2;
    private final Integer MAX_POOL_SIZE = 2;
    private final Integer QUEUE_CAPACITY = 100;


    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(CORE_POOL_SIZE);
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        executor.setQueueCapacity(QUEUE_CAPACITY);
        executor.setThreadNamePrefix("tutor-");
        executor.initialize();
        return executor;
    }
}
