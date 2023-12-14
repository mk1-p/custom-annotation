package com.example.customannotation.custom.slack;


import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.model.Attachment;
import com.slack.api.model.Field;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

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
        this.methodName = methodName != null ? methodName : "Test";
        this.title = title;
        this.message = message;
        this.color = color;
    }



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
