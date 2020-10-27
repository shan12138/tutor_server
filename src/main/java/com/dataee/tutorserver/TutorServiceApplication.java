package com.dataee.tutorserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Date;

@EnableScheduling
@SpringBootApplication
public class TutorServiceApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(TutorServiceApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(TutorServiceApplication.class, args);
    }
}
