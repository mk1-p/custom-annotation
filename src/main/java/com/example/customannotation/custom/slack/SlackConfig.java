package com.example.customannotation.custom.slack;


import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.model.Attachment;
import com.slack.api.model.Field;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.awt.*;
import java.io.IOException;
import java.util.List;


@Configuration
// Configuration Property File 을 읽어들이기 위한 작업
@ConfigurationProperties(prefix = "slack")
@Getter
@Setter
// Slack 라이브러리가 Import 되었는지
// Slack 라이브러리가 없으면 코드 자체가 실행이 안되니 굳이 필요없지만...
@ConditionalOnClass(name = "com.slack.api.Slack")
@Slf4j
public class SlackConfig {
    private String token;

    /**
     * 토큰 값을 적용한 MethodsClient Bean 등록
     * @return
     */
    @Bean
    @ConditionalOnMissingClass
    public MethodsClient methodsClient() {
        log.info("Slack Init!");
        MethodsClient methodsClient = Slack.getInstance().methods(token);

        return methodsClient;
    }

}
