package com.example.customannotation.custom.slack;

import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
@RequiredArgsConstructor
@Slf4j
public class SlackService {

    private final MethodsClient methodsClient;

    /**
     * Send Message to Slack by SlackMessageDto Obj
     *
     * @param dto
     * @throws SlackApiException
     * @throws IOException
     */
    @Async
    public void sendSlackMessage(SlackMessageDto dto) throws SlackApiException, IOException {
        // Generate ChatPostMessageRequest Object
        ChatPostMessageRequest request = dto.generatePostMessage();
        // send Message to Slack
        methodsClient.chatPostMessage(request);
    }

}
