package com.example.customannotation.custom.slack;


import com.slack.api.Slack;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.model.Attachment;
import com.slack.api.model.Field;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.util.StringUtils;

import java.util.List;

@Getter
@Setter
public class SlackMessageDto {

    private String channel;
    private String methodName;
    private String title;
    private String message;
    private ColorType color;


    @Builder
    public SlackMessageDto(String channel, String methodName, String title, String message, ColorType color) {
        this.channel = channel;
        this.methodName = StringUtils.hasLength(methodName) ? methodName : "NO_NAME";
        this.title = title;
        this.message = message;
        this.color = color;
    }


    /**
     * ChatPostMessageRequest > Attachment > Field
     * 내부 요소를 포함하고 있다.
     * @return
     */
    public Field generateSlackFiled() {
        return Field.builder()
                .title(this.title)
                .value(this.message)
                .valueShortEnough(false)
                .build();
    }

    public ChatPostMessageRequest generatePostMessage() {

        Attachment attachment = Attachment.builder()
                .color(this.color.getHexCode())
                .fields(List.of(generateSlackFiled()))
                .build();

        return ChatPostMessageRequest.builder()
                .channel(this.channel)
                .text(this.methodName)
                .attachments(List.of(attachment))
                .build();
    }

    /**
     * SlackMessageDto by ProceedingJoinPoint
     * TODO 실행한 Method 명이 필요없다면 SlackMessaging Annotation 에서 바로 매핑하도록 함
     * @param proceedingJoinPoint
     * @return
     */
    public static SlackMessageDto toDto(ProceedingJoinPoint proceedingJoinPoint) {

        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        SlackMessaging slackAnno = methodSignature.getMethod().getAnnotation(SlackMessaging.class);
        String channel = slackAnno.channel();
        String title = slackAnno.title();
        String message = slackAnno.message();
        ColorType color = slackAnno.color();
        String methodName = ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod().getName();


        return SlackMessageDto.builder()
                .channel(channel)
                .methodName(methodName)
                .title(title)
                .message(message)
                .color(color)
                .build();

    }

}
