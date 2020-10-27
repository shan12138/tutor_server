package com.dataee.tutorserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * 使用springfox和swagger来生成json形式的文档
 *
 * @author JinYue
 * @CreateDate 2019/5/12 15:25
 */
@Configuration
@EnableSwagger2
@Profile("dev")
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("tutor-server-api")
                .select()
                //.apis(not(RequestHandlerSelectors.withMethodAnnotation()))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("tutor后台开发组", "www.baidu.com", "1140018327@qq.com");
        return new ApiInfoBuilder()
                .title("tutor家教平台内部api文档")
                .description("发现文档有误欢迎邮件来扰~:1140018327@qq.com")
                .contact(contact)
                .version("1.0")
                .build();
    }
}
