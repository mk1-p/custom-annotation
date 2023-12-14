package com.example.customannotation;

import com.example.customannotation.custom.slack.SlackMessaging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableAspectJAutoProxy
@Slf4j
public class CustomAnnotationApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomAnnotationApplication.class, args);
    }

}
