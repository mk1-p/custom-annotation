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
    private String channel;

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


    /**
     * 연결 테스트 메소드이다.
     * @throws SlackApiException
     * @throws IOException
     */
    public void connectSlackTest(MethodsClient methodsClient) throws SlackApiException, IOException {

        String greenHex = "#"+Integer.toHexString(Color.GREEN.getRGB()).substring(2);
        Attachment attachment = Attachment.builder().color(greenHex) // 메시지 색상
                .fields( // 메시지 본문 내용
                        List.of(Field.builder()
                                .title("Connect")
                                .value("success")
                                .build()
                        )
                ).build();

        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                .channel(channel)
                .iconUrl(":ghost:")
                .text("is connectable slack!")
                .attachments(List.of(attachment))
                .build();

        ChatPostMessageResponse response = methodsClient.chatPostMessage(request);

        if (!response.isOk()) {
            log.error("Slack is fail to connect : {}",response.getError());
        }

        log.info("Slack Chat 보냄");
        // C06AB3X8Q81
    }


}
