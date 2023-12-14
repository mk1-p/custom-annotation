package com.example.customannotation.custom;


import com.example.customannotation.custom.slack.SlackMessaging;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {


    @GetMapping("/slack")
    @SlackMessaging(message = "슬랙 테스트를 하였다.", channel = "#back_sys_noti")
    public ResponseEntity sendSlackTest() {
        return ResponseEntity.ok().body("send Slack message!");
    }

}
