package com.dataee.tutorserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

/**
 * 跨域请求配置过滤器
 *
 * @Author JinYue
 * @CreateDate 2019/4/18
 */
@Configuration
public class CorsConfig {
    /**
     * 跨域信息配置
     *
     * @return
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "PUT", "DELETE", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("application/json", "text/plain", "content-type", "dnt"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    /**
     * 将跨域配置放入Request的过滤器中
     *
     * @param corsConfigurationSource
     * @return
     */
    @Bean
    CorsFilter corsFilter(CorsConfigurationSource corsConfigurationSource) {
        CorsFilter corsFilter = new CorsFilter(corsConfigurationSource);
        return corsFilter;
    }
}
