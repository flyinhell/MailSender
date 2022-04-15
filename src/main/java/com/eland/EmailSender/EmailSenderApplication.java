package com.eland.EmailSender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@ComponentScan(value = "com.eland")
public class EmailSenderApplication extends SpringBootServletInitializer{
    //自定義配置載入
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(EmailSenderApplication.class);
    }
    public static void main(String[] args) {
        SpringApplication.run(EmailSenderApplication.class, args);
    }

}

