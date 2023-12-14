package com.example.customannotation.custom.slack;


import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;




@Aspect
@Order(1)
@Configuration
@RequiredArgsConstructor
@Slf4j
public class SlackAspect {


    private final SlackService slackService;

    /**
     * AOP 작업
     * SlackMessaging Annotation 이 적용된 Method 가 실행 될때,
     * SlackService의 sendSlackMessage() 가 실행 되도록
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("@annotation(SlackMessaging)")
    public Object processCustomAnnotation(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        // Run Method
        Object proceedReturnValue = proceedingJoinPoint.proceed();

        // Slack Message Dto Builder
        SlackMessageDto slackMessageDto = SlackMessageDto.toDto(proceedingJoinPoint);

        // Send Message;
        slackService.sendSlackMessage(slackMessageDto);
        return proceedReturnValue;
    }

}
