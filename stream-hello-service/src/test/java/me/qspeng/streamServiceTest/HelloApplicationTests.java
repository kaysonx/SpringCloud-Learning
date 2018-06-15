package me.qspeng.streamServiceTest;

import me.qspeng.streamService.SinkSender;
import me.qspeng.streamService.StreamServiceApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StreamServiceApplication.class)
public class HelloApplicationTests {
    @Autowired
    private SinkSender sinkSender;

    @Test
    public void contextLoads() {
        sinkSender.input().send(MessageBuilder.withPayload("From SinkSender").build());
    }

}
